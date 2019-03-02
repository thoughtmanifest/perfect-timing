(ns pt.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [re-frame-notifier.core :as re-frame-notifier]

   [pt.components.composite.navbar :as navbar]
   [pt.constants]
   [pt.db.core]
   [pt.events.core]
   [pt.subscriptions.core]
   [pt.router.core :as router]))

(defn core-container
  []
  (fn []
    [:div
     [navbar/render]
     [router/router]
     [re-frame-notifier/render-notifications]]))

(defn mount-root
  "Application entry point"
  []
  (re-frame/dispatch [:bootstrap/start])

  (reagent/render [core-container]
                  (js/document.getElementById "main")))

(mount-root)
