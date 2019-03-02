(ns wow-log-parser.core
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]
            [clojure.set :as set]
            [clojure.string :as string])
  (:gen-class))

(def api-key "82172888b3b5e12d1e2dc2871fa4bd37")
(def host "https://www.warcraftlogs.com:443/v1/")

(defn request
  ([path] (request path {}))
  ([path params]
   (-> path
       (->> (str host))
       (http/get (update params
                         :query-params
                         #(assoc % "api_key" api-key)))
       :body
       (json/read-str :key-fn keyword))))

(defn get-table
  [id {:keys [start_time end_time] :as r}]
  (loop [period 0
         agg {:damage-done 0 :damage-taken 0 :healing 0}
         v []]
    (let [period-start (+ start_time (* period 30000))
          period-end (+ start_time (* (inc period) 30000))]
      (if (> (+ start_time (* 30000 period)) end_time)
        v
        (let [period-data (into {:period period}
                                (map (fn [report]
                                       (let [req (format "report/tables/%s/%s?start=%d&end=%d"
                                                         (name report)
                                                         id
                                                         period-start
                                                         period-end)]
                                         [report (transduce (map :total) + (:entries (request req)))]))
                                     [:damage-done :damage-taken :healing]))
              period-agg (merge-with + agg period-data)]
          (recur (inc period)
                 period-agg
                 (conj v (merge period-data
                                (set/rename-keys period-agg
                                                 {:damage-done :agg-damage-done
                                                  :damage-taken :agg-damage-taken
                                                  :healing :agg-healing})))))))))

(defn get-fights
  [boss-name id]
  (let [{:keys [enemies start end fights]} (request (format "report/fights/%s" id))
        boss-fights (-> (filter #(= (:name %) boss-name) enemies)
                        first
                        :fights)
        boss-fight-ids (into #{} (map :id boss-fights))
        fights (filter #(= (:name %) boss-name) fights) #_(filter #(boss-fight-ids (:id %)) fights)]
    fights))

(defn process-log
  [id]
  (let [fights (get-fights "Grong" id)]
    (reduce (fn [acc i]
              (assoc acc
                     (format "%s-%d" id (:id i))
                     (assoc i
                            :periods
                            (try (get-table id i)
                                 (catch Exception e (println "Something went wrong"))))))
            {}
            fights)))

(defn respond
  [id]
  (json/write-str (process-log id)))

(defn process-encounters
  [file-name]
  (doall
   (map-indexed (fn [x y] (println x) (spit "stuff.txt" (respond y) :append true))
                (sequence (comp
                           (map #(string/split % #","))
                           (map first))
                          (drop 1
                                (string/split (slurp file-name) #"\n"))))))


(map-indexed (fn [x y] (println x)
               (spit "filtered-encounters-return-2.txt" y :append true))
             (process-encounters "filteredencounters.txt"))

(process-encounters "filteredencounters.txt")
