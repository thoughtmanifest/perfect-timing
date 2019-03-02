(ns pt.subscriptions.core
  (:require [re-frame-notifier.core :as rf-notifier]
            [re-frame-request.core :as rf-request]
            [re-frame-routing.core :as rf-routing]
            [re-frame.core :as re-frame]))

(rf-notifier/register-subscriptions)
(rf-request/register-subscriptions)
(rf-routing/register-subscriptions)

(defn raid-metadata
  [db [_ {:keys [raid-id]}]]
  (get-in db [:raids raid-id :metadata]))

(re-frame/reg-sub :raid/raid-metadata raid-metadata)
