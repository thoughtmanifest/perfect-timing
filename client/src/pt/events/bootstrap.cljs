(ns pt.events.bootstrap
  (:require [re-frame.core :as re-frame]
            [goog.object :as gobj]

            [pt.db.core :as db]
            [pt.events.utils :refer [reg-event-fx event-router]]))

(defn start
  [_ _]
  {:db db/default-db
   :extend-cljs true
   :pushy-init true
   :dev-tools-init true})

(reg-event-fx :bootstrap/start start)

(defn extend-cljs
  [enabled]
  (when enabled
    (extend-type js/NodeList
      ISeqable
      (-seq [array] (array-seq array 0)))
    (extend-type js/HTMLCollection
      ISeqable
      (-seq [array] (array-seq array 0)))))

(re-frame/reg-fx :extend-cljs extend-cljs)

(defn dev-tools-init
  [enabled]
  (when (and js/goog.DEBUG enabled)
    (enable-console-print!)))

(re-frame/reg-fx :dev-tools-init dev-tools-init)
