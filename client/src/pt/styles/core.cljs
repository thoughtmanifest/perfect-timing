(ns pt.styles.core
  (:require [jss]
            [jss-preset-default]
            [goog.object :as gobj]

            [pt.styles.borders :refer [borders]]
            [pt.styles.colors :refer [colors]]
            [pt.styles.constants :refer [constants]]
            [pt.styles.defaults :refer [defaults]]
            [pt.styles.form :refer [form]]
            [pt.styles.media-queries :refer [queries]]
            [pt.styles.notifier :refer [notifier]]
            [pt.styles.radius :refer [radius]]
            [pt.styles.shadows :refer [shadows]]
            [pt.styles.spacing :refer [spacing]]
            [pt.styles.layouts :refer [layouts]]
            [pt.styles.typography :refer [font-families
                                            line-height
                                            size
                                            weight]]))

;; Note: Calling `jss/setup` will not invoke properly
(js-invoke jss "setup" (jss-preset-default))

(defn classes
  [styles]
  (-> jss
      (js-invoke "createStyleSheet" (clj->js styles))
      (js-invoke "attach")
      (gobj/get "classes")
      (js->clj :keywordize-keys true)))

(def application-styles
  {:borders (borders colors)
   :colors colors
   :constants constants
   :font-family font-families
   :font-height line-height
   :font-size size
   :font-weight weight
   :shadows shadows
   :spacing spacing
   :queries queries
   :radius radius
   :layouts layouts})

(defn gs
  "Pulls a styles from application-styles. If no style exists in the style
  map, then log error."
  [lens]
  (let [style (get-in application-styles
                      lens
                      (js/Error (str "No style found for " lens)))]

    (if (= (type style) js/Error)
      (js/console.error style)
      style)))

(defn create-font-styles
  [{:keys [style color family]}]
  {:-webkit-text-size-adjust :none
   :font-size (gs [:font-size style])
   :font-weight (gs [:font-weight style])
   :line-height (gs [:font-height style])
   :color (gs [:colors color])
   :font-family (gs [:font-family family])

   (gs [:queries :tablet])
   {:font-size
    (gs [:font-size (-> style name (keyword "tablet"))])}

   (gs [:queries :phone])
   {:font-size
    (gs [:font-size (-> style name (keyword "mobile"))])}})

(classes
 {"@global"
  (merge
   (defaults gs create-font-styles)
   (form gs create-font-styles)
   (notifier gs create-font-styles))})

(def styles->classes classes)

(defn add-class
  "Adds a class to an elements options hash"
  [options class-key class-map]
  (let [class-name (get class-map class-key)]
    (when-not (some? class-name)
      (js/console.error (str "Class not found: '" class-key "'")))

    (update options
            :class
            (fn [c]
              (cond
                (nil? c)
                [class-name]

                (vector? c)
                (conj c class-name)

                (string? c)
                (str c " " class-name)

                :else
                (js/console.error
                 (str "Invalid class definition: '" class-key "'")))))))

(def shared-classes
  (styles->classes
   {:paragraph
    {:max-width (gs [:constants :max-paragraph-width])}}))

(defn add-shared-class
  [options class-key]
  (add-class options class-key shared-classes))
