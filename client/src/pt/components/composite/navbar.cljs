(ns pt.components.composite.navbar
  (:require
   [pt.components.base.text :as text]
   [pt.styles.core :refer [gs styles->classes add-class]]))

(def classes
  (styles->classes
   {:navbar {:height "80px"
             :width "100%"
             :box-shadow (gs [:shadows :shadow-20])
             :padding (gs [:spacing :p20])}}))

(defn render
  []
  [:div (add-class {} :navbar classes)
   [text/title-40 :p
    {:style {:margin (gs [:spacing :p0])}}
    "Perfect Timing"]])
