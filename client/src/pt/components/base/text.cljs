(ns pt.components.base.text
  (:require [devcards.core :as devcards]

            [pt.styles.core :refer [styles->classes
                                         gs
                                         add-class
                                         create-font-styles]])
  (:require-macros [devcards.core :refer [defcard-rg]]))

(def text-styles
  (reduce
   (fn [acc font]
     (merge acc {(or (:alias font)
                     (:style font))
                 (create-font-styles font)}))
   {}
   [{:style :display-20 :color :gray-scale-800 :family :primary}
    {:style :display-10 :color :gray-scale-800 :family :primary}
    {:style :title-40   :color :gray-scale-800 :family :primary}
    {:style :title-30   :color :gray-scale-800 :family :primary}
    {:style :title-20   :color :gray-scale-800 :family :primary}
    {:style :title-10   :color :gray-scale-800 :family :primary}
    {:style :body-30    :color :gray-scale-800 :family :primary}
    {:style :body-20    :color :gray-scale-700 :family :primary}
    {:style :body-10    :color :gray-scale-600 :family :primary}
    {:style :caption-30 :color :gray-scale-600 :family :primary}
    {:style :caption-20 :color :gray-scale-600 :family :primary}
    {:style :caption-10 :color :gray-scale-600 :family :primary}

    {:alias :display-20-alt
     :style :display-20 :color :gray-scale-800 :family :secondary}
    {:alias :display-10-alt
     :style :display-10 :color :gray-scale-800 :family :secondary}
    {:alias :title-40-alt
     :style :title-40   :color :gray-scale-800 :family :secondary}
    {:alias :title-30-alt
     :style :title-30   :color :gray-scale-800 :family :secondary}
    {:alias :title-20-alt
     :style :title-20   :color :gray-scale-800 :family :secondary}
    {:alias :title-10-alt
     :style :title-10   :color :gray-scale-800 :family :secondary}
    {:alias :body-30-alt
     :style :body-30    :color :gray-scale-800 :family :secondary}
    {:alias :body-20-alt
     :style :body-20    :color :gray-scale-700 :family :secondary}
    {:alias :body-10-alt
     :style :body-10    :color :gray-scale-600 :family :secondary}
    {:alias :caption-30-alt
     :style :caption-30 :color :gray-scale-600 :family :secondary}
    {:alias :caption-20-alt
     :style :caption-20 :color :gray-scale-600 :family :secondary}
    {:alias :caption-10-alt
     :style :caption-10 :color :gray-scale-600 :family :secondary}]))

(def classes (styles->classes text-styles))

(defn- render-text
  [tag text-key options content]
  [tag (add-class options text-key classes) content])

(defn- create-font
  [font-name]
  (fn font*
    ([tag content] [font* tag {} content])
    ([tag options content]
     [render-text tag font-name options content])))

(def display-20 (create-font :display-20))
(def display-10 (create-font :display-10))
(def title-40 (create-font :title-40))
(def title-30 (create-font :title-30))
(def title-20 (create-font :title-20))
(def title-10 (create-font :title-10))
(def body-30 (create-font :body-30))
(def body-20 (create-font :body-20))
(def body-10 (create-font :body-10))
(def caption-30 (create-font :caption-30))
(def caption-20 (create-font :caption-20))
(def caption-10 (create-font :caption-10))

(def display-20-alt (create-font :display-20-alt))
(def display-10-alt (create-font :display-10-alt))
(def title-40-alt (create-font :title-40-alt))
(def title-30-alt (create-font :title-30-alt))
(def title-20-alt (create-font :title-20-alt))
(def title-10-alt (create-font :title-10-alt))
(def body-30-alt (create-font :body-30-alt))
(def body-20-alt (create-font :body-20-alt))
(def body-10-alt (create-font :body-10-alt))
(def caption-30-alt (create-font :caption-30-alt))
(def caption-20-alt (create-font :caption-20-alt))
(def caption-10-alt (create-font :caption-10-alt))

(defonce test-sentance
  (str "Typography is the process of using type to print onto a page, "
       "or the general look of letters and words on a page."))

(defcard-rg display-20
  "Display 20"
  [display-20 :p test-sentance])

(defcard-rg display-10
  "Display 10"
  [display-10 :p test-sentance])

(defcard-rg title-40
  "Title 40"
  [title-40 :p test-sentance])

(defcard-rg title-30
  "Title 30"
  [title-30 :p test-sentance])

(defcard-rg title-20
  "Title 20"
  [title-20 :p test-sentance])

(defcard-rg title-10
  "Title 10"
  [title-10 :p test-sentance])

(defcard-rg body-30
  "Body 30"
  [body-30 :p test-sentance])

(defcard-rg body-20
  "Body 20"
  [body-20 :p test-sentance])

(defcard-rg body-10
  "Body 10"
  [body-10 :p test-sentance])

(defcard-rg caption-30
  "Caption 30"
  [caption-30 :p test-sentance])

(defcard-rg caption-20
  "Caption 20"
  [caption-20 :p test-sentance])

(defcard-rg caption-10
  "Caption 10"
  [caption-10 :p test-sentance])

(defcard-rg display-20-alt
  "Display 20 (alt)"
  [display-20-alt :p test-sentance])

(defcard-rg display-10-alt
  "Display 10 (alt)"
  [display-10-alt :p test-sentance])

(defcard-rg title-40-atl
  "Title 40 (alt)"
  [title-40-alt :p test-sentance])

(defcard-rg title-30-alt
  "Title 30 (alt)"
  [title-30-alt :p test-sentance])

(defcard-rg title-20-alt
  "Title 20 (alt)"
  [title-20-alt :p test-sentance])

(defcard-rg title-10-alt
  "Title 10 (alt)"
  [title-10-alt :p test-sentance])

(defcard-rg body-30-alt
  "Body 30 (alt)"
  [body-30-alt :p test-sentance])

(defcard-rg body-20-alt
  "Body 20 (alt)"
  [body-20-alt :p test-sentance])

(defcard-rg body-10-alt
  "Body 10 (alt)"
  [body-10-alt :p test-sentance])

(defcard-rg caption-30-alt
  "Caption 30 (alt)"
  [caption-30-alt :p test-sentance])

(defcard-rg caption-20-alt
  "Caption 20 (alt)"
  [caption-20-alt :p test-sentance])

(defcard-rg caption-10-alt
  "Caption 10 (alt)"
  [caption-10-alt :p test-sentance])
