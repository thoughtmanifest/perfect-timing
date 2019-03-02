(ns pt.components.forms.raid
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [reagent-form.core :as rf]
            [reagent-form.validators :as v]
            [reagent-form.transformers :as t]

            [pt.components.base.button :as button]
            [pt.styles.core :refer [styles->classes
                                         gs
                                         add-class
                                         add-shared-class]]))

(def classes
  (styles->classes
   {:name-container
    {:display :flex
     :align-items :center
     :justify-content :space-between}

    :cta-container
    {:display :flex
     :align-items :flex-end
     :justify-content :space-between}

    :cta-container-left
    {:display :flex
     :align-items :flex-end}

    :cancel-button
    {:margin-left (gs [:spacing :p20])}}))

(defn render
  [{:keys [dispatch-key]}]
  [rf/form {:id dispatch-key
            :is-submitting (reagent/atom false)}
   [:form
    {:rf/form {:on-submit
               #(do
                  (re-frame/dispatch [:router/nav-to (str "/raid/"
                                                          (:raid-id %))])
                  (re-frame/dispatch [dispatch-key %]))}}

    (rf/input
     (cond-> {:field-key :raid-id
              :id :raid-id
              :type :text
              :label "Raid ID"
              :validators [{:validator v/required
                            :message "Required"}]
              :transformers [t/trim]}))

    (rf/submit-button
     {:default-text "Generate Raid Report"})]])
