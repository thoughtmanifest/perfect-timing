(ns pt.components.base.button
  (:require [devcards.core :as devcards]

            [pt.styles.core :refer [gs
                                    styles->classes
                                    add-class
                                    create-font-styles]])
  (:require-macros [devcards.core :refer [defcard-rg]]))

(def base-button-styles
  (merge
   (create-font-styles {:style :caption-30
                        :color :primary-500
                        :family :primary})
   {:padding [(gs [:spacing :p8])
              (gs [:spacing :p20])]
    :text-decoration :none
    :cursor :pointer
    :outline :none
    :transition (str "background-color 0.2s ease, "
                     "color 0.2s ease, "
                     "border-color 0.2s ease")
    :border (str "2px solid " (gs [:colors :primary-500]))}))

(def primary-button-styles
  (merge
   base-button-styles
   {:background-color (gs [:colors :primary-500])
    :color (gs [:colors :true-white])

    "&:hover"
    {:background-color (gs [:colors :primary-600])
     :border-color (gs [:colors :primary-600])}}))

(def primary-link-button-styles
  (merge
   primary-button-styles
   ;; TODO Devcards overrides this value
   {:color (str (gs [:colors :true-white]) " !important")
    :padding [(gs [:spacing :p12])
              (gs [:spacing :p20])]}))

(def secondary-button-styles
  (merge
   base-button-styles
   {:background-color (gs [:colors :true-white])

    "&:hover"
    {:background-color (gs [:colors :primary-500])
     :color (gs [:colors :true-white])}}))

(def secondary-button-danger-styles
  (merge
   base-button-styles
   {:background-color (gs [:colors :true-white])
    :border-color (gs [:colors :error])
    :color (gs [:colors :error])

    "&:hover"
    {:background-color (gs [:colors :error])
     :color (gs [:colors :true-white])}}))

(def secondary-link-button-styles
  (merge
   secondary-button-styles
   {:padding [(gs [:spacing :p12])
              (gs [:spacing :p20])]}))

(def classes
  (styles->classes
   {:primary-button primary-button-styles
    :primary-button-alt (assoc primary-button-styles
                               :font-family (gs [:font-family :secondary]))
    :primary-link-button primary-link-button-styles
    :primary-link-button-alt (assoc primary-link-button-styles
                                    :font-family (gs [:font-family :secondary]))
    :secondary-button secondary-button-styles
    :secondary-button-danger secondary-button-danger-styles
    :secondary-button-alt (assoc secondary-button-styles
                                 :font-family (gs [:font-family :secondary]))
    :secondary-link-button secondary-link-button-styles
    :secondary-link-button-alt (assoc secondary-link-button-styles
                                      :font-family
                                      (gs [:font-family :secondary]))}))

(defn primary-button
  [options button-content]
  [:button (add-class options :primary-button classes) button-content])

(defn primary-button-alt
  [options button-content]
  [:button (add-class options :primary-button-alt classes) button-content])

(defn secondary-button
  [options button-content]
  [:button (add-class options :secondary-button classes) button-content])

(defn secondary-button-alt
  [options button-content]
  [:button (add-class options :secondary-button-alt classes) button-content])

(defn secondary-button-danger
  [options button-content]
  [:button (add-class options :secondary-button-danger classes) button-content])

(defn primary-link-button
  [options display]
  [:a (add-class options :primary-link-button classes) display])

(defn secondary-link-button
  [options display]
  [:a (add-class options :secondary-link-button classes) display])

(defn primary-link-button-alt
  [options display]
  [:a (add-class options :primary-link-button-alt classes) display])

(defn secondary-link-button-alt
  [options display]
  [:a (add-class options :secondary-link-button-alt classes) display])

(defcard-rg primary-button
  "Primary button"
  [primary-button {} "Primary Button"])

(defcard-rg primary-button-atl
  "Primary button (alt)"
  [primary-button-alt {} "Primary Button (alt)"])

(defcard-rg secondary-button
  "Secondary button"
  [secondary-button {} "Secondary Button"])

(defcard-rg secondary-button-danger
  "Secondary button danger"
  [secondary-button-danger {} "Secondary Button Danger"])

(defcard-rg secondary-button-alt
  "Secondary button (alt)"
  [secondary-button-alt {} "Secondary Button (alt)"])

(defcard-rg primary-link-button
  "Primary link button"
  [primary-link-button {} "Primary Link Button"])

(defcard-rg primary-link-button-alt
  "Primary link button (alt)"
  [primary-link-button-alt {} "Primary Link Button (alt)"])

(defcard-rg secondary-link-button
  "Secondary link button"
  [secondary-link-button {} "Secondary Link Button"])

(defcard-rg secondary-link-button-alt
  "Secondary link button (alt)"
  [secondary-link-button-alt {} "Secondary Link Button (alt)"])
