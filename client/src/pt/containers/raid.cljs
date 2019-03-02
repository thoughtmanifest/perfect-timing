(ns pt.containers.raid
  (:require
   [pt.components.base.link :as link]
   [pt.components.base.text :as text]
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

(defn render
  [{:keys [params]}]
  (let [{:keys [raid-id]} params]
    (fn []
      [:div (add-class {} :container classes)

       [:div (add-class {} :top-bar-container classes)
        [:div (add-class {} :raid-id-container classes)
         [text/caption-30 :p {:style {:margin-right (gs [:spacing :p8])}}
          "Raid ID: "]
         [text/caption-20 :p raid-id]]
        [link/link {:href "/"} "New Raid"]]])))
