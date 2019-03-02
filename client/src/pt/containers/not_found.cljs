(ns pt.containers.not-found
  (:require [pt.components.base.text :as text]))

(defn render
  []
  [:div
   [text/display-20-alt :h1
    {:style {:text-align :center}}
    "Page Not Found"]])
