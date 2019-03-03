(ns wow-log-parser.core
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]
            [clojure.set :as set]
            [clojure.string :as string])
  (:gen-class))

(def api-key "82172888b3b5e12d1e2dc2871fa4bd37")
(def host "https://www.warcraftlogs.com:443/v1/")

(defn get-api-key []
  (first
   (shuffle
    ["8331ff7f83ae0274f59fc296ae35e972"
     "82172888b3b5e12d1e2dc2871fa4bd37"
     "5c7a90bd67eec88b65c6d445d993094f"
     "dda6ed978c28c93075ab3d094e3be36d"
     "eea7daa787a4f0ec0b941a517261cd2c"
     "e49a595080229d7213f97d6092b9caa6"
     "d3792d11fefa51a82c6a7b18ee493a55"
     "17c1f4c7b3ca88119fab1beb43fe5760"
     "695858ab951259685d53a08407d18c21"
     "da6510ccec60f1cbcfbecd98a002cc22"])))


(defn request
  ([path] (request path {}))
  ([path params]
   (-> path
       (->> (str host))
       (http/get (update params
                         :query-params
                         #(assoc % "api_key" (get-api-key))))
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

(defn add-ts-period [jsons]
  (reduce-kv (fn [m k v]
               (assoc m k
                      (cond-> v
                        (:periods v) (update :periods 
                                             (fn [x]
                                               (map-indexed (fn [y z] (assoc z :ts-period (+ 30 (* 30 y))))
                                                            (sort-by :period x)))))))
             {} jsons))

(defn respond
  [id]
  (json/write-str (add-ts-period (process-log id))))

(defn process-encounters
  [file-name]
  (doall
   (pmap (fn [x y] (println (+ 557 x)) (spit "sssttttuuuuffffffffffffffff.txt" (str (respond y) "\n") :append true))
         (range)
         (sequence (comp
                    (map #(string/split % #","))
                    (map first))
                   (drop (+ 1 557)
                         (string/split (slurp file-name) #"\n"))))))

#_(process-encounters "filteredencounters.txt")

#_(dorun
 (map #(spit "grong-runs.almost-json"
             (str
              (json/write-str
               (let [jsons (json/read-str % :key-fn keyword)
                     to-write
                     (reduce-kv (fn [m k v]
                                  (assoc m k
                                         (cond-> v
                                           (:periods v) (update :periods 
                                                                (fn [x]
                                                                  (map-indexed (fn [y z] (assoc z :ts-period (+ 30 (* 30 y))))
                                                                               (sort-by :period x)))))))
                                {} jsons)]
                 to-write)) "\n")
            :append true)
      (line-seq (clojure.java.io/reader "sssttttuuuuffffffffffffffff.txt"))))
