(ns pt.events.raid
  (:require [pt.events.utils :refer [reg-event-fx api-route]]))

(defn get-raid-report-success
  [{:keys [db]} [_ report]]
  (js/console.log "REPORT" report)
  {:db db})

(reg-event-fx :raid/get-raid-report-success get-raid-report-success)

(defn get-raid-report-failure
  [{:keys [db]} [_ err]]
  {:db db})

(reg-event-fx :raid/get-raid-report-failure get-raid-report-failure)

(defn get-raid-report
  [{:keys [db]} [event-name {:keys [raid-id]}]]
  {:db db
   :request-mock {:name event-name
                  :method :get
                  :uri (api-route "/raid/" raid-id)
                  :response-format :json
                  :on-success [:raid/get-raid-report-success]
                  :on-failure [:raid/get-raid-report-failure]
                  :mock {:data {}
                         :time 5000}}})

(reg-event-fx :raid/get-raid-report get-raid-report)
