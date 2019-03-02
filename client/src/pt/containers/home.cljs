(ns pt.containers.home
  (:require [re-frame.core :as re-frame]

            [pt.components.forms.raid :as raid]
            [pt.styles.core :refer [styles->classes gs add-class]]))

(def classes
  (styles->classes
   {:container
    {:padding (gs [:spacing :p20])}

    :form-container
    {:max-width "300px"}}))

(defn render
  []
  [:div (add-class {} :container classes)
   [:div (add-class {} :form-container classes)
    [raid/render {:dispatch-key :raid/get-raid-report}]]])
