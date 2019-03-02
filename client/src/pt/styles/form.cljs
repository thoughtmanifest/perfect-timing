(ns pt.styles.form)

(defn- base-label
  [gs create-font-styles]
  (create-font-styles {:style :caption-30
                       :color :primary-400
                       :family :primary}))

(defn- base-text
  [gs create-font-styles]
  (create-font-styles {:style :body-20
                       :color :gray-scale-800
                       :family :primary}))

(defn- get-field-styles
  [gs create-font-styles]
  (merge {:width "100%"
          :height (gs [:spacing :p40])
          :padding (gs [:spacing :p4])
          :border (gs [:borders :border-50-1])
          :border-radius (gs [:radius :r4])
          :background-color (gs [:colors :true-white])
          "&::placeholder" (create-font-styles
                            {:style :body-20
                             :color :gray-scale-300
                             :family :primary})
          "&:focus" {:outline :none
                     :border (gs [:borders :border-200-1])
                     :box-shadow (str "0px 0px 5px "
                                      (gs [:colors :primary-100]))}}
         (base-text gs create-font-styles)))

(defn- get-submit-button-styles
  [gs create-font-styles]
  (merge
   (create-font-styles {:style :caption-30
                        :color :primary-500
                        :family :primary})
   {:padding [(gs [:spacing :p8]) (gs [:spacing :p20])]
    :cursor :pointer
    :outline :none
    :transition (str "background-color 0.2s ease, "
                     "color 0.2s ease, "
                     "border-color 0.2s ease")
    :border (str "2px solid " (gs [:colors :primary-500]))
    :background-color (gs [:colors :primary-500])
    :color (gs [:colors :true-white])

    "&:hover"
    {:background-color (gs [:colors :primary-600])
     :border-color (gs [:colors :primary-600])}

    "&:disabled"
    {:background-color (gs [:colors :gray-scale-50])
     :color (gs [:colors :gray-scale-300])
     :border (gs [:borders :border-50-1])}}))

(defn form
  [gs create-font-styles]
  (let [field-styles (get-field-styles gs create-font-styles)]
    {"input[type=text]" field-styles
     "input[type=date]" field-styles
     "input[type=datetime]" field-styles
     "input[type=email]" field-styles
     "input[type=number]" field-styles
     "input[type=search]" field-styles
     "input[type=time]" field-styles
     "input[type=url]" field-styles
     "input[type=password]" field-styles

     "select"
     (merge field-styles
            {:background-color (gs [:colors :true-white])
             :-webkit-appearance :none
             :text-transform :capitalize})

     "textarea"
     (merge field-styles
            {:resize :none
             :overflow :hidden
             :min-height "200px"})

     ".checkbox-form-field"
     {:display :flex
      :align-items :center
      "& label" {:order 2
                 :margin (gs [:spacing :p0])
                 :padding-top (gs [:spacing :p0])
                 :margin-left (gs [:spacing :p12])}}

     ".time-picker"
     {:display :flex
      :align-items :center}

     ".time-picker select"
     {:width "60px"}

     ".time-picker-colon"
     {:margin (str "auto " (gs [:spacing :p4]))}

     ".time-picker-period"
     {:margin-left (gs [:spacing :p4])}

     ".reagent-form-row"
     {:display :flex
      :flex-wrap :no-wrap
      :justify-content :space-between
      "& .reagent-form-field"
      {:margin-right (gs [:spacing :p16])
       "&:last-child"
       {:margin-right (gs [:spacing :p0])}}
      (gs [:queries :phone])
      {:flex-wrap :wrap
       "& .reagent-form-field"
       {:margin-right (gs [:spacing :p0])}}}

     ".reagent-form-field"
     {:margin-bottom (gs [:spacing :p4])
      :position :relative
      :width "100%"}

     ".reagent-form-field-label"
     (merge (base-label gs create-font-styles)
            {:display :block
             :margin-bottom (gs [:spacing :p4])
             :padding-top (gs [:spacing :p20])})

     ".reagent-form-field-error"
     (merge
      (create-font-styles {:style :caption-20
                           :color :error
                           :family :primary})
      {:margin-top (gs [:spacing :p0])
       :position :absolute
       :top (gs [:spacing :p0])})

     ".reagent-form-field-hint"
     (merge
      (create-font-styles {:style :caption-20
                           :color :warning
                           :family :primary})
      {:margin-top (gs [:spacing :p0])
       :position :absolute
       :top (gs [:spacing :p0])})

     ".reagent-form-submit-button-container"
     {:margin-bottom (gs [:spacing :p0])
      :position :relative
      :padding-top (gs [:spacing :p28])}

     ".reagent-form-errors"
     (merge
      (create-font-styles {:style :caption-20
                           :color :error
                           :family :primary})
      {:position :absolute
       :top (gs [:spacing :p0])
       :margin-top (gs [:spacing :p0])
       :white-space :nowrap})

     ".reagent-form-submit-button"
     (get-submit-button-styles gs create-font-styles)

     ".reagent-form-submitting-tag-container"
     {:position :relative
      :display :block}

     ".reagent-form-submitting-tag"
     {:position :absolute
      :top "-10px"
      :width (gs [:spacing :p20])
      :height (gs [:spacing :p20])
      :margin (str (gs [:spacing :p20] " auto"))
      :background-color (gs [:colors :primary-500])
      :border-radius "100%"
      :animation "scaleout 1.0s infinite ease-in-out"}

     ".crud-form"
     {:width "100%"
      :padding (gs [:spacing :p16])
      :border (str "1px solid " (gs [:colors :primary-200]))
      :border-radius (gs [:radius :r4])}}))
