(ns pt.events.core
  (:require [re-frame.core :as re-frame]
            [re-frame-notifier.core :as rf-notifier]
            [re-frame-request.core :as rf-request]
            [re-frame-routing.core :as rf-routing]

            [pt.db.core :as db]

            [pt.events.bootstrap]
            [pt.events.raid]

            [pt.events.utils :refer [app-interceptors]]
            [pt.router.routes :as routes]))

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Third Party Re-Frame Events
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(rf-notifier/register-events
 {:notifier-interceptors [app-interceptors]})

(rf-request/register-events
 {:request-interceptors [app-interceptors]})

(rf-routing/register-events
 {:set-route-interceptors [app-interceptors]
  :routes routes/routes})

(defn db-reset
  [db _] db/default-db)

(re-frame/reg-event-db :db/reset db-reset)
