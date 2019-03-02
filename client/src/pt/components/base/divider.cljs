(ns pt.components.base.divider
  (:require [devcards.core :as devcards]

            [pt.styles.core :refer [gs
                                         styles->classes
                                         add-class]])
  (:require-macros [devcards.core :refer [defcard-rg]]))

(def divider-styles
  {:width "100%"
   :margin [(gs [:spacing :p20])
            (gs [:spacing :p0])]
   :border-top :none
   :border-left :none
   :border-right :none})

(def classes
  (styles->classes
   {:divider-25
    (assoc divider-styles :border-color (gs [:colors :gray-scale-25]))

    :divider-50
    (assoc divider-styles :border-color (gs [:colors :gray-scale-50]))

    :divider-100
    (assoc divider-styles :border-color (gs [:colors :gray-scale-100]))

    :divider-200
    (assoc divider-styles :border-color (gs [:colors :gray-scale-200]))

    :divider-300
    (assoc divider-styles :border-color (gs [:colors :gray-scale-300]))

    :divider-400
    (assoc divider-styles :border-color (gs [:colors :gray-scale-400]))

    :divider-500
    (assoc divider-styles :border-color (gs [:colors :gray-scale-500]))

    :divider-600
    (assoc divider-styles :border-color (gs [:colors :gray-scale-600]))

    :divider-700
    (assoc divider-styles :border-color (gs [:colors :gray-scale-700]))}))

(defn- create-divider
  [class]
  (fn []
    [:hr (add-class {} class classes)]))

(def divider-25 (create-divider :divider-25))
(def divider-50 (create-divider :divider-50))
(def divider-100 (create-divider :divider-100))
(def divider-200 (create-divider :divider-200))
(def divider-300 (create-divider :divider-300))
(def divider-400 (create-divider :divider-400))
(def divider-500 (create-divider :divider-500))
(def divider-600 (create-divider :divider-600))
(def divider-700 (create-divider :divider-700))

(defcard-rg divider-25
  "Divider 25"
  [divider-25])

(defcard-rg divider-50
  "Divider 50"
  [divider-50])

(defcard-rg divider-100
  "Divider 100"
  [divider-100])

(defcard-rg divider-200
  "Divider 200"
  [divider-200])

(defcard-rg divider-300
  "Divider 300"
  [divider-300])

(defcard-rg divider-400
  "Divider 400"
  [divider-400])

(defcard-rg divider-500
  "Divider 500"
  [divider-500])

(defcard-rg divider-600
  "Divider 600"
  [divider-600])

(defcard-rg divider-700
  "Divider 700"
  [divider-700])
