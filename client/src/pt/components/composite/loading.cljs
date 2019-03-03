(ns pt.components.composite.loading
  (:require [devcards.core :as devcards]

            [pt.styles.core :refer [gs styles->classes]])
  (:require-macros [devcards.core :refer [defcard-rg]]))

(def classes
  (styles->classes
   {:loading-container
    {:width "100%"
     :height "100%"
     :display :flex
     :justify-content :center
     :align-items :center}

    :spinner
    {:width (str (gs [:constants :spinner-size]) "px")
     :height (str (gs [:constants :spinner-size]) "px")
     :x "0px"
     :y "0px"
     :viewBox (str "0 0 "
                   (gs [:constants :spinner-size]) " "
                   (gs [:constants :spinner-size]))

     "& circle"
     {:fill :transparent
      :stroke (gs [:colors :gray-scale-300])
      :strokeWidth 4
      :strokeLinecap :round
      :strokeDasharray  (* 3.14 (gs [:constants :spinner-size]))
      :transform-origin (str (* 0.5 (gs [:constants :spinner-size])) "px "
                             (* 0.5 (gs [:constants :spinner-size])) "px " 0)
      :animation "spinner 2s linear infinite"}}}))

(defn
  ^{:attribution "https://medium.com/@clg/animated-svg-spinner-8dff32d310fc"}
  render
  ([] [render {:container-height (gs [:constants :spinner-size])}])
  ([{:keys [container-height]}]
   [:div {:class [(:loading-container classes)]
          :style {:height container-height}}
    [:svg {:class (:spinner classes)}
     [:circle {:cx "20", :cy "20", :r "18"}]]]))

(defcard-rg render
  "Loading Indicator"
  [render])
