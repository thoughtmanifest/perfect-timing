(ns pt.events.raid
  (:require [pt.events.utils :refer [reg-event-fx api-route]]))

(defn get-raid-report-success
  [{:keys [db]} [_ report]]
  {:db db})

(reg-event-fx :raid/get-raid-report-success get-raid-report-success)

(defn get-raid-report-failure
  [{:keys [db]} [_ err]]
  {:db db})

(reg-event-fx :raid/get-raid-report-failure get-raid-report-failure)

(defn get-raid-report
  [{:keys [db]} [event-name {:keys [raid-id]}]]
  {:db db
   :request-mock {:name event-name
                  :method :get
                  :uri (api-route "/raid/" raid-id)
                  :response-format :json
                  :on-success [:raid/get-raid-report-success]
                  :on-failure [:raid/get-raid-report-failure]
                  :mock {:data {}
                         :time 5000}}})

(reg-event-fx :raid/get-raid-report get-raid-report)

(def grong-id 2263)

(defn- format-meta-data
  [metadata]
  (->> metadata
       :fights
       (filter #(contains? #{(:boss %)} grong-id))
       (map-indexed #(assoc %2 :attempt (inc %1)))
       (vec)))

(defn get-raid-metadata-success
  [{:keys [db]} [event-name raid-id metadata]]
  {:db (assoc-in db [:raids raid-id :metadata] (format-meta-data metadata))})

(reg-event-fx :raid/get-raid-metadata-success get-raid-metadata-success)

(defn get-raid-metadata-failure
  [{:keys [db]} [event-name err]]
  {:db db})

(reg-event-fx :raid/get-raid-metadata-failure get-raid-metadata-failure)

(defn get-raid-metadata
  [{:keys [db]} [event-name {:keys [raid-id]}]]
  {:db db
   :request-mock {:name event-name
                  :method :get
                  :uri (str "https://www.warcraftlogs.com:443/v1/report/fights"
                            "/" raid-id
                            "?api_key=aa9b838a6195a39d0b9d278688e39973")
                  :response-format :json
                  :on-success [:raid/get-raid-metadata-success raid-id]
                  :on-failure [:raid/get-raid-metadata-failure]
                  :mock {:data {:fights [{:difficulty 4,
                                          :boss 2265,
                                          :name "Champion of the Light",
                                          :kill false,
                                          :start_time 0,
                                          :size 18,
                                          :end_time 251861,
                                          :fightPercentage 401,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 1,
                                          :id 1,
                                          :bossPercentage 401}
                                         {:difficulty 4,
                                          :boss 2265,
                                          :name "Champion of the Light",
                                          :kill true,
                                          :start_time 755994,
                                          :size 19,
                                          :end_time 940584,
                                          :fightPercentage 0,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 1,
                                          :id 2,
                                          :bossPercentage 0}
                                         {:id 3,
                                          :start_time 1244322,
                                          :end_time 1251947,
                                          :boss 0,
                                          :originalBoss 2263,
                                          :name "Grong"}
                                         {:difficulty 4,
                                          :boss 2263,
                                          :name "Grong",
                                          :kill false,
                                          :start_time 1467118,
                                          :size 19,
                                          :end_time 1654017,
                                          :fightPercentage 3501,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 0,
                                          :id 4,
                                          :bossPercentage 3501}
                                         {:difficulty 4,
                                          :boss 2263,
                                          :name "Grong",
                                          :kill false,
                                          :start_time 1808716,
                                          :size 20,
                                          :end_time 2076311,
                                          :fightPercentage 290,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 0,
                                          :id 5,
                                          :bossPercentage 290}
                                         {:difficulty 4,
                                          :boss 2263,
                                          :name "Grong",
                                          :kill false,
                                          :start_time 2183025,
                                          :size 20,
                                          :end_time 2443259,
                                          :fightPercentage 938,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 0,
                                          :id 6,
                                          :bossPercentage 938}
                                         {:difficulty 4,
                                          :boss 2263,
                                          :name "Grong",
                                          :kill false,
                                          :start_time 2691165,
                                          :size 19,
                                          :end_time 2886114,
                                          :fightPercentage 3586,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 0,
                                          :id 7,
                                          :bossPercentage 3586}
                                         {:difficulty 4,
                                          :boss 2263,
                                          :name "Grong",
                                          :kill true,
                                          :start_time 2983235,
                                          :size 19,
                                          :end_time 3241032,
                                          :fightPercentage 0,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 0,
                                          :id 8,
                                          :bossPercentage 0}
                                         {:difficulty 4,
                                          :boss 2266,
                                          :name "Jadefire Masters",
                                          :kill true,
                                          :start_time 3792462,
                                          :size 19,
                                          :end_time 4119630,
                                          :fightPercentage 0,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 0,
                                          :id 9,
                                          :bossPercentage 0}
                                         {:difficulty 4,
                                          :boss 2271,
                                          :name "Opulence",
                                          :kill false,
                                          :start_time 4850861,
                                          :size 19,
                                          :end_time 5104800,
                                          :fightPercentage 6687,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 1,
                                          :id 10,
                                          :bossPercentage 3374}
                                         {:id 11,
                                          :start_time 5136976,
                                          :end_time 5139601,
                                          :boss 0,
                                          :originalBoss 2271,
                                          :name "Opulence"}
                                         {:difficulty 4,
                                          :boss 2271,
                                          :name "Opulence",
                                          :kill false,
                                          :start_time 5254928,
                                          :size 19,
                                          :end_time 5520869,
                                          :fightPercentage 6656,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 1,
                                          :id 12,
                                          :bossPercentage 3313}
                                         {:difficulty 4,
                                          :boss 2271,
                                          :name "Opulence",
                                          :kill true,
                                          :start_time 5613609,
                                          :size 19,
                                          :end_time 6090833,
                                          :fightPercentage 0,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 2,
                                          :id 13,
                                          :bossPercentage 0}
                                         {:difficulty 4,
                                          :boss 2268,
                                          :name "Conclave of the Chosen",
                                          :kill false,
                                          :start_time 7385140,
                                          :size 21,
                                          :end_time 7656316,
                                          :fightPercentage 4249,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 3,
                                          :id 14,
                                          :bossPercentage 6996}
                                         {:difficulty 4,
                                          :boss 2268,
                                          :name "Conclave of the Chosen",
                                          :kill false,
                                          :start_time 7918658,
                                          :size 21,
                                          :end_time 8165657,
                                          :fightPercentage 4679,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 3,
                                          :id 15,
                                          :bossPercentage 8716}
                                         {:difficulty 4,
                                          :boss 2268,
                                          :name "Conclave of the Chosen",
                                          :kill false,
                                          :start_time 8361510,
                                          :size 21,
                                          :end_time 8819051,
                                          :fightPercentage 1227,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 4,
                                          :id 16,
                                          :bossPercentage 4911}
                                         {:difficulty 4,
                                          :boss 2268,
                                          :name "Conclave of the Chosen",
                                          :kill false,
                                          :start_time 8992835,
                                          :size 21,
                                          :end_time 9270176,
                                          :fightPercentage 4749,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 3,
                                          :id 17,
                                          :bossPercentage 8997}
                                         {:difficulty 4,
                                          :boss 2268,
                                          :name "Conclave of the Chosen",
                                          :kill false,
                                          :start_time 9523356,
                                          :size 21,
                                          :end_time 9738423,
                                          :fightPercentage 4849,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 3,
                                          :id 18,
                                          :bossPercentage 9398}
                                         {:difficulty 4,
                                          :boss 2268,
                                          :name "Conclave of the Chosen",
                                          :kill false,
                                          :start_time 9892854,
                                          :size 21,
                                          :end_time 10118821,
                                          :fightPercentage 4854,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 3,
                                          :id 19,
                                          :bossPercentage 9416}
                                         {:difficulty 4,
                                          :boss 2268,
                                          :name "Conclave of the Chosen",
                                          :kill false,
                                          :start_time 10360297,
                                          :size 20,
                                          :end_time 10743150,
                                          :fightPercentage 2175,
                                          :partial 3,
                                          :lastPhaseForPercentageDisplay 4,
                                          :id 20,
                                          :bossPercentage 8701}]}}}})

(reg-event-fx :raid/get-raid-metadata get-raid-metadata)
