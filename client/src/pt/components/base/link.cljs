(ns pt.components.base.link
  (:require [devcards.core :as devcards]

            [pt.styles.core :refer [styles->classes
                                         gs
                                         add-class
                                         create-font-styles]]
            [pt.styles.typography :refer [margin]])
  (:require-macros [devcards.core :refer [defcard-rg]]))

(def link-styles
  {:cursor :pointer
   :text-decoration :none
   :color (gs [:colors :link-500])

   "&:hover"
   {:color (gs [:colors :link-600])}})

(def button-link-styles
  {:cursor :pointer
   :text-decoration :none
   :border :none
   :outline :none
   :border-radius (gs [:radius :r4])
   :transition (str "background-color 0.2s ease-in-out, "
                    "color 0.2s ease-in-out")
   :color (gs [:colors :link-500])

   "&:hover"
   {:color (gs [:colors :link-600])
    :background-color (gs [:colors :link-100])}})

(def classes
  (styles->classes
   {:link
    (merge
     link-styles
     (create-font-styles
      {:style :body-20 :color :link-500 :family :primary}))

    :link-alt
    (merge
     link-styles
     (create-font-styles
      {:style :body-20 :color :link-500 :family :secondary}))

    :button-link
    (merge
     button-link-styles
     (create-font-styles
      {:style :body-20 :color :link-500 :family :primary}))

    :button-link-alt
    (merge
     button-link-styles
     (create-font-styles
      {:style :body-20 :color :link-500 :family :secondary}))}))

(defn link
  [options display]
  [:a (add-class options :link classes)
   display])

(defn link-alt
  [options display]
  [:a (add-class options :link-alt classes)
   display])

(defn button-link
  [options content]
  [:button (add-class options :button-link classes)
   content])

(defn button-link-alt
  [options content]
  [:button (add-class options :button-link-alt classes)
   content])

(defcard-rg link
  "Link"
  [link {} "Sample link"])

(defcard-rg link-alt
  "Link (alt)"
  [link-alt {} "Sample link (alt)"])

(defcard-rg button-link
  "Button Link"
  [button-link {} "Sample button link"])

(defcard-rg button-link-alt
  "Button Link (alt)"
  [button-link-alt {} "Sample button link"])
