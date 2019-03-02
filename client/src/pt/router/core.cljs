(ns pt.router.core
  (:require [re-frame.core :as re-frame]
            [re-frame-routing.core :as rfr]

            [pt.containers.home :as home]
            [pt.containers.raid :as raid]

            ;; Catch-all
            [pt.containers.not-found :as not-found]))

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Routing Utilities
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn create-route-middleware
  "Route middleware is an itterator that reduces over a sequence of
  middleware functions to ensure all required route logic has been
  met before mounting the containing view.

  Use cases: Protecting routes
             Redirecting routes
             Bootstraping data"
  []
  (fn [container middleware]
    (let [route-params (re-frame/subscribe [:router/route-params])
          route-query (re-frame/subscribe [:router/route-query])
          route-key (re-frame/subscribe [:router/route])
          ;; Middleware state can be used for anything, and is the recommended
          ;; method for sharing infromation between middleware functions
          ;; or track information on global state updates that will trigger
          ;; re-renders of the middleware chain.
          ;;
          ;; Realworld application of using middleware-state:
          ;; View relies on data from a server before rendering. Add request
          ;; information to middleware state to cause the view to display
          ;; a loading indicator before displaying the view. (Works well with
          ;; re-frame-request, TODO: example)
          middleware-state (atom {})]
      (fn []
        (let [{:keys [is-loading container] :as ctx}
              (reduce
               (fn [{:keys [is-loading] :as middleware-ctx}
                   middleware-fn]
                 (if is-loading
                   middleware-ctx
                   (middleware-fn middleware-ctx)))
               {:is-loading false
                :middleware-state @middleware-state
                :route-params @route-params
                :route-query @route-query
                :container container}
               middleware)]

          (reset! middleware-state (:middleware-state ctx))

          [container {:params @route-params
                      :query @route-query
                      :key @route-key
                      :loading is-loading}])))))


(def route-middleware (create-route-middleware))

(defn create-router
  [router-name]
  (let [route (re-frame/subscribe [:router/route])
        prevent-top-scroll #{:bot-details :bot-simulator}]
    (fn []
      (when-not (contains? prevent-top-scroll @route)
        (set! (-> js/document .-body .-scrollTop) 0)
        (set! (-> js/document .-documentElement .-scrollTop) 0))

      [router-name @route])))

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Route Middleware
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn ensure-resource
  [event-key dispatch-fn {:keys [route-params
                                 route-query
                                 middleware-state]
                          :as ctx}]
  (let [request (re-frame/subscribe [:request/track-request event-key])
        has-not-bootstrapped (-> middleware-state
                                 :bootstrapped-resources
                                 event-key
                                 nil?)
        ctx* (cond-> ctx
               (true? has-not-bootstrapped)
               (assoc-in [:middleware-state :bootstrapped-resources event-key]
                         true))]

    (when (true? has-not-bootstrapped)
      (dispatch-fn route-params))

    (if (true? (contains? #{:loading :never-requested} (:status @request)))
      (assoc ctx* :is-loading true)
      ctx*)))

(def ensure-raid-metadata
  (partial ensure-resource :raid/get-raid-metadata
           #(re-frame/dispatch [:raid/get-raid-metadata %])))

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Container Views
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmulti containers identity)

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Unauthenticated Views
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmethod containers
  :home [] [(route-middleware home/render
                              [])])

(defmethod containers
  :raid [] [(route-middleware raid/render
                              [ensure-raid-metadata])])

;; catch-all

(defmethod containers
  :default [] [(route-middleware not-found/render [])])

;; routers
(defn router [] (create-router containers))
