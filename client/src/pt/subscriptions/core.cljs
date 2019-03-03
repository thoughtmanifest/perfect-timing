(ns pt.subscriptions.core
  (:require [re-frame-notifier.core :as rf-notifier]
            [re-frame-request.core :as rf-request]
            [re-frame-routing.core :as rf-routing]
            [re-frame.core :as re-frame]))

(rf-notifier/register-subscriptions)
(rf-request/register-subscriptions)
(rf-routing/register-subscriptions)

(defn spy [x] (js/console.log x) x)

(defn raid
  [db [_ {:keys [raid-id]}]]
  (let [raid-data (get-in db [:raids raid-id])]
    (as-> raid-data x
      (vals x)
      (group-by :boss x)
      ;; 0 Seems to correspond to non boss fights
      (dissoc x 0))))

(re-frame/reg-sub :raid/raid raid)
