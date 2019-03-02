(ns pt.styles.notifier)

(defn base-button-styles
  [gs]
  {:padding [(gs [:spacing :p8])
             (gs [:spacing :p20])]
   :cursor :pointer
   :outline :none
   :transition (str "background-color 0.2s ease, "
                    "color 0.2s ease, "
                    "border-color 0.2s ease")
   :border (str "2px solid " (gs [:colors :primary-500]))
   :width "calc(50% - 10px)"})

(defn primary-button-styles
  [gs create-font-styles]
  (merge (base-button-styles gs)
         (create-font-styles {:style :caption-30
                              :family :primary
                              :color :true-white})
         {:border :none
          :background-color (gs [:colors :primary-500])
          :color (gs [:colors :true-white])
          "&:focus" {:outline (gs [:spacing :p0])
                     :border-image :none}
          "&:hover" {:background-color (gs [:colors :primary-600])}
          "&:active" {:background-color (gs [:colors :primary-400])}
          "&:disabled" {:background-color (gs [:colors :gray-scale-200])}}))

(defn secondary-button-styles
  [gs create-font-styles]
  (merge (base-button-styles gs)
         (create-font-styles {:style :caption-30
                              :family :primary
                              :color :primary-500})
         {:border-color (gs [:colors :primary-500])
          :border-width (gs [:spacing :p1])
          :background-color (gs [:colors :true-white])
          :color (gs [:colors :primary-500])
          "&:focus" {:outline (gs [:spacing :p0])
                     :border-image :none}
          "&:hover" {:background-color (gs [:colors :primary-600])
                     :border-color (gs [:colors :primary-600])
                     :color (gs [:colors :true-white])}
          "&:active" {:background-color (gs [:colors :primary-400])
                      :border-color (gs [:colors :primary-400])
                      :color (gs [:colors :true-white])}}))

(defn notifier
  [gs create-font-styles]
  {".notifier-underlay"
   {:position :fixed
    :top 0
    :bottom 0
    :width "100%"
    :height "100%"
    :background-color "rgba(0, 0, 0, 0.5)"
    :display :flex
    :justify-content :center
    :align-items :center
    :overflow :hidden}

   ".notifier-alert"
   {:max-width "600px"
    :background-color (gs [:colors :true-white])
    :padding [[(gs [:spacing :p20])
               (gs [:spacing :p12])]]
    :border-radius (gs [:radius :r4])
    :display :flex
    :flex-wrap :wrap
    :justify-content :space-between}

   ".notifier-alert-title"
   {:font-family (gs [:font-family :primary])
    :font-weight (gs [:font-weight :body-30])
    :margin [[(gs [:spacing :p40])
              :auto
              (gs [:spacing :p20])
              :auto]]
    :color (gs [:colors :gray-scale-700])
    :font-size "24px"
    :text-align :center
    :width "100%"}

   ".notifier-alert-message"
   {:font-family (gs [:font-family :primary])
    :font-weight (gs [:font-weight :body-20])
    :text-align :center
    :width "100%"}

   ".notifier-alert-confirm-button"
   (merge (primary-button-styles gs create-font-styles)
          {:margin-right (gs [:spacing :p12])})

   ".notifier-alert-deny-button"
   (secondary-button-styles gs create-font-styles)

   ".notifier-toast"
   {:position :fixed
    :display :block
    :bottom (gs [:spacing :p20])
    :left (gs [:spacing :p20])
    :padding (gs [:spacing :p20])
    :border-radius (gs [:radius :r4])
    :font-family (gs [:font-family :primary])
    :font-weight (gs [:font-weight :caption-20])
    :font-size (gs [:font-size :caption-20])
    :line-height "1.5em"
    :box-shadow (gs [:shadows :shadow-10])
    :max-width "600px"

    (gs [:queries :tablet])
    {:max-width "75%"}

    (gs [:queries :phone])
    {:max-width (str "calc(100% - 40px)")}}

   ".info-toast"
   {:background-color (gs [:colors :primary-500])
    :color (gs [:colors :true-white])}

   ".warning-toast"
   {:background-color (gs [:colors :warning])
    :color (gs [:colors :true-white])}

   ".error-toast"
   {:background-color (gs [:colors :error])
    :color (gs [:colors :true-white])}})
