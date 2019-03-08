(ns pt.containers.raid
  (:require
   [re-frame.core :as re-frame]

   [pt.components.base.link :as link]
   [pt.components.base.text :as text]
   [pt.components.composite.raid-list :as raid-list]
   [pt.components.composite.loading :as loading]
   [pt.styles.core :refer [styles->classes gs add-class]]))

(def classes
  (styles->classes
   {:container
    {:padding (gs [:spacing :p20])}

    :top-bar-container
    {:display :flex
     :justify-content :space-between
     :align-items :center}

    :raid-id-container
    {:display :flex
     :align-items :center}}))

(def grong-id 2263)

(defn render
  [{:keys [params] :as a}]
  (let [{:keys [raid-id]} params
        raid (re-frame/subscribe [:raid/raid {:raid-id raid-id}])]
    (fn [{:keys [params loading]}]
      [:div (add-class {} :container classes)
       [:div (add-class {} :top-bar-container classes)
        [:div (add-class {} :raid-id-container classes)
         [text/caption-30 :p {:style {:margin-right (gs [:spacing :p8])}}
          "Raid ID: "]
         [text/caption-20 :p raid-id]]
        [link/link {:href "/"} "Enter New Raid ID"]]

       (if (true? loading)
         [loading/render {:container-height "400px"}]
         (doall
          (for [boss-id (keys @raid)]
            ^{:key boss-id}
            [raid-list/render {:fights (get @raid boss-id)
                               :raid-id raid-id}])))])))
