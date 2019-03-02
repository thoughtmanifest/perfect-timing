(ns pt.components.composite.raid-list
  (:require
   [pt.components.base.text :as text]
   [pt.components.base.link :as link]
   [pt.styles.core :refer [gs styles->classes add-class]]))

(def classes
  (styles->classes
   {:card-container
    {:border (gs [:borders :border-100-1])
     :margin-bottom (gs [:spacing :p40])}

    :top-banner
    {:display :flex
     :justify-content :space-between
     :align-items :center
     :padding (gs [:spacing :p20])
     :border-bottom (gs [:borders :border-50-1])}

    :body
    {:padding (gs [:spacing :p20])}}))

(defn- ->difficulty-str
  [diff]
  (or (get {1 "LFR"
            2 "Flex"
            3 "Normal"
            4 "Heroic"
            5 "Mythic"} diff) "Unknown"))

(defn- render-fight-card
  [{fight-id :id
    attempt :attempt
    success :kill
    start-time :start_time
    end-time :end_time
    difficulty :difficulty} raid-id]
  (let [fight-length (.round js/Math (/ (- end-time start-time) 1000))
        fight-length-mins (.floor js/Math (/ fight-length 60))
        fight-length-seconds (mod fight-length 60)]
    [:div (add-class {} :card-container classes)
     [:div (add-class {} :top-banner classes)
      [text/title-10 :p (str " Fight #" attempt)]
      [text/caption-20 :p (str fight-length-mins
                               " mins, "
                               fight-length-seconds
                               " seconds.")]]
     [:div (add-class {} :body classes)
      [text/caption-20 :p (str "Difficulty: " (->difficulty-str difficulty))]
      [text/caption-20 :p (str "Kill?" (if success " Yes" " No"))]

      [link/link
       {:href (str "https://www.warcraftlogs.com/reports/"
                   raid-id
                   "#fight="
                   fight-id)
        :target "_blank"}
       "View in Warcraft Logs"]]]))

(defn render
  [{:keys [fights raid-id]}]
  (let [boss-name (->> fights first :name)]
    [:div
     [text/title-40 :p (str "Boss: " boss-name)]
     [:ul
      (for [{:keys [id] :as fight} fights]
        ^{:key id} [render-fight-card fight raid-id])]]))
