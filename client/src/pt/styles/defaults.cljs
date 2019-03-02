(ns pt.styles.defaults)

(defn defaults
  [gs create-font-styles]
  {:html {:box-sizing :border-box
          :background-color (gs [:colors :true-black])}
   "*" {:box-sizing :inherit
        "&:before" {:box-sizing :inherit}
        "&:after" {:box-sizing :inherit}}

   "ol, ul" {:list-style :none
             :padding-left 0}

   ".no-scroll" {:overflow :hidden}

   ".no-print" {"@media print" {:display "none !important"}}

   ".print-only" {:display :none
                  "@media print" {:display :initial}}

   "@media print" {".page-break" {:display :block
                                  :position :relative
                                  :page-break-after :always}}

   "html, body, #main"
   {:height "100%"}

   "#main"
   {:display :flex
    :flex-direction :column
    :justify-content :space-between}

   "@keyframes spinner"
   {"0%" {:transform "rotate(0deg)"
          :strokeDashoffset (* 0.66 (gs [:constants :spinner-size]))}
    "50%" {:transform "rotate(720deg)"
           :strokeDashoffset (* 3.14 (gs [:constants :spinner-size]))}
    "100%" {:transform "rotate(1080deg)"
            :strokeDashoffset (* 0.66 (gs [:constants :spinner-size]))}}})
