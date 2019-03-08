(ns pt.components.composite.raid-list
  (:require
   [reagent.core :as reagent]
   [goog.object :as gobj]

   [pt.components.base.text :as text]
   [pt.components.base.link :as link]
   [pt.styles.core :refer [gs styles->classes add-class]]))

(def d3 (gobj/get js/window "d3"))
(def c3 (gobj/get js/window "c3"))
(def generate (gobj/get c3 "generate"))

(def classes
  (styles->classes
   {:fight-list-container
    {:display :flex
     :justify-content :space-between
     :flex-wrap :wrap
     :margin-bottom (gs [:spacing :p60])
     :padding-bottom (gs [:spacing :p60])
     :border-bottom (gs [:borders :border-50-1])

     "& > *"
     {:width (str "calc(50% - 20px)")}}

    :boss-title
    {:border-bottom (gs [:borders :border-100-2])
     :display :inline-block}

    :card-container
    {:border (gs [:borders :border-100-1])
     :margin-bottom (gs [:spacing :p40])}

    :chart-container
    {:width "400px"
     :padding (gs [:spacing :p20])

     "& text"
     {:fill (gs [:colors :primary-400])}}

    :top-banner
    {:display :flex
     :justify-content :space-between
     :align-items :center
     :padding (gs [:spacing :p20])
     :border-bottom (gs [:borders :border-50-1])}

    :body
    {:padding (gs [:spacing :p20])
     :display :flex
     :justify-content :space-between}

    :left
    {}}))

(defn- ->difficulty-str
  [diff]
  (or (get {1 "LFR"
            2 "Flex"
            3 "Normal"
            4 "Heroic"
            5 "Mythic"} diff) "Unknown"))

(defn- to-time-str
  [d]
  (let [minutes (.floor js/Math (/ d 60))
        seconds (mod d 60)]
    (str minutes ":" seconds)))

(defn- render-fight-card
  [{fight-id :id
    attempt :attempt
    success :kill
    start-time :start_time
    end-time :end_time
    difficulty :difficulty
    predictions :predictions}

   raid-id]
  (let [fight-length (.round js/Math (/ (- end-time start-time) 1000))
        fight-length-mins (.floor js/Math (/ fight-length 60))
        fight-length-seconds (mod fight-length 60)
        !ref (atom nil)]
    (reagent/create-class
     {:component-did-mount
      (fn []
        (when (some? predictions)
          (generate
           (clj->js
            {:bindto @!ref
             :padding {:right 20}
             :data {:x "x"
                    :columns [(into ["x"] (map first predictions))
                              (into ["success"] (map second predictions))]}
             :axis {:x {:tick {:format to-time-str}}
                    :y {:tick {:format (js-invoke d3 "format" "%")
                               :outer false}
                        :min 0.01
                        :max 1.0}}}))))

      :reagent-render
      (fn []
        [:li (add-class {} :card-container classes)
         [:div (add-class {} :top-banner classes)
          [text/title-10 :p (str " Fight #" attempt)]
          [text/caption-20 :p (str fight-length-mins
                                   " mins, "
                                   fight-length-seconds
                                   " seconds.")]]
         [:div (add-class {} :body classes)
          [:div (add-class {} :left classes)
           [text/caption-20 :p (str "Difficulty: " (->difficulty-str difficulty))]
           [text/caption-20 :p (str "Kill?" (if (true? success) " Yes" " No"))]

           [link/link
            {:href (str "https://www.warcraftlogs.com/reports/"
                        raid-id
                        "#fight="
                        fight-id)
             :target "_blank"}
            "View in Warcraft Logs"]]
          [:div {:ref #(reset! !ref %)
                 :class (:chart-container classes)}
           (when-not (some? predictions)
             [text/caption-30 :p "No model applied"])]]])})))

(defn- format-fights
  [fights]
  (->> fights
       (sort-by :end_time)
       (map-indexed #(assoc %2 :attempt (inc %1)))
       (vec)))

(defn render
  [{:keys [fights raid-id]}]
  (let [boss-name (->> fights first :name)]
    [:div
     [text/title-40 :p
      (add-class {} :boss-title classes)
      (str "Boss: " boss-name)]
     [:ul (add-class {} :fight-list-container classes)
      (for [{:keys [id] :as fight} (format-fights fights)]
        ^{:key id} [render-fight-card fight raid-id])]]))
