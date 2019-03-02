(ns pt.styles.layouts)

(def layouts
  {:row-left {:display :flex
              :flex-direction :row
              :align-items :center
              :justify-content :flex-start}
   :row-right {:display :flex
               :flex-direction :row
               :align-items :center
               :justify-content :flex-end}
   :row-center {:display :flex
                :flex-direction :row
                :align-items :center
                :justify-content :center}
   :col-left {:display :flex
              :flex-direction :column
              :align-items :center
              :justify-content :flex-start}
   :col-right {:display :flex
               :flex-direction :column
               :align-items :center
               :justify-content :flex-end}
   :col-center {:display :flex
                :flex-direction :column
                :align-items :center
                :justify-content :center}})
