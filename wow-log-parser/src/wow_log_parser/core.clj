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
     "da6510ccec60f1cbcfbecd98a002cc22"
     
     "56eb77a354f3069fad494b5255a5a27c"
     "6acc08106d377c06d6b1308e3ec9854c"
     "d012c4c1a3cd90f792ed00a905253a53"
     "9732a49e0826ba118fe5a73add116caf"
     "24251d012d1522b8ce67b43becd58afe"
     "5fd297311051f190194dded8b198e090"
     "f31cb53d3456904c3fa3a34d43c4aa33"
     "37d8d7f1c83fe6c81dff1554023f8582"
     "b3c6ce5ba0eaf5a7f8757177fcd9faeb"
     "025ddf6eca297ca25259f2558997a046"
     "452f83f075eddb881d49f705b1300b66"
     "722e64559cc96d6d594726585da87429"
     "4fd041dcc4c05bd79509ab0657ade98f"
     "68fcd5c4bd723571b628515d7e33987d"
     "80bfe4634518842d2dc8b7a2d84dcb03"
     "f0c01e4c16bbe6ecef6bac22c82b3d9b"
     "0faef8fd1e6819a45966abdf376058dc"
     "b5714db6d612b9d877dbc73a8c8d5489"
     "9a86d6ce35753631ac052a47fa1155c0"
     "e948ece01c66b3f2aeff8897543e5bf8"])))


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

#_(defn get-table
  [id {:keys [start_time end_time] :as r}]
  (loop [period 0
         agg {:damage-done 0 :damage-taken 0 :healing 0}
         v []]
    (let [period-start (+ start_time (* period 15000))
          period-end (+ start_time (* (inc period) 15000))]
      (if (> (+ start_time (* 15000 period)) end_time)
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

(def some-data-0
  (request (format "report/events/%s?start=%d&end=%d"
                   "amZV6N1DBbLcAjfP"
                   7183272
                   7184275)))

(defn get-all-events
  [id period-start period-end]
  (let [req (format "report/events/%s?start=%d&end=%d"
                    id
                    period-start
                    period-end)
        response (request req)
        npt (:nextPageTimestamp response)]
    (println "Doing " id ":" period-start "->" period-end)
    (def junk response)
    (cond-> (:events response)
      npt (concat (get-all-events id npt period-end)))))

(defn p-get-all-events
  [id period-start period-end]
  (apply concat
         (pmap (fn [x] (get-all-events id x (+ x 1000)))
               (take-while (partial > period-end) (map #(+ period-start (* 1000 %)) (range))))))

(take-while (partial > 15000) (map #(+ 8000 (* 1000 %)) (range)))

(defn get-events
  [id {:keys [start_time end_time] :as r}]
  (loop [period 0
         agg {:damage-done 0 :damage-taken 0 :healing 0}
         v []]
    (let [period-start (+ start_time (* period 15000))
          period-end (+ start_time (* (inc period) 15000))]
      (if (> (+ start_time (* 15000 period)) end_time)
        v
        (let [period-events (reduce-kv
                             (fn [mm kk vv] (assoc mm kk (count vv)))
                             {}
                             (group-by #(str (:sourceIsFriendly %) "-"
                                             (:targetIsFriendly %) "-"
                                             (:name (:ability %)))
                                       (p-get-all-events id period-start period-end)))
              period-data (into {:period period}
                                (map (fn [report]
                                       (let [req (format "report/tables/%s/%s?start=%d&end=%d"
                                                         (name report)
                                                         id
                                                         period-start
                                                         period-end)]
                                         [report (transduce (map :total) + (:entries (request req)))]))
                                     [:damage-done :damage-taken :healing]))
              period-agg (merge-with + agg period-data period-events)]
          (recur (inc period)
                 period-agg
                 (conj v (merge period-data
                                period-events
                                (reduce-kv (fn [m k v] (assoc m (str k "-agg") v)) {} period-agg)
                                #_(set/rename-keys period-agg
                                                 {:damage-done :agg-damage-done
                                                  :damage-taken :agg-damage-taken
                                                  :healing :agg-healing
                                                  #_:events #_:agg-events})))))))))

(defn get-fights
  [boss-name id]
  (let [{:keys [enemies start end fights]} (request (format "report/fights/%s" id))
        boss-fights (-> (filter #(= (:name %) boss-name) enemies)
                        first
                        :fights)
        boss-fight-ids (into #{} (map :id boss-fights))
        fights (filter #(= (:name %) boss-name) fights) #_(filter #(boss-fight-ids (:id %)) fights)]
    (take 2 fights)))

(defn process-log
  [id]
  (let [fights #_(try) (get-fights "Grong" id) #_(catch Exception e (println "couldn't get-fights with " (.getMessage e)))]
    (reduce (fn [acc i]
              (assoc acc
                     (format "%s-%d" id (:id i))
                     (assoc i
                            :periods
                            #_(try) (get-events #_get-table id i)
                            #_(catch Exception e (println "couldn't get-table with " (.getMessage e))))))
            {}
            fights)))

(defn add-ts-period [jsons]
  (reduce-kv (fn [m k v]
               (assoc m k
                      (cond-> v
                        (:periods v) (update :periods 
                                             (fn [x]
                                               (map-indexed (fn [y z] (assoc z :ts-period (+ 15 (* 15 y))))
                                                            (sort-by :period x)))))))
             {} jsons))

(defn respond
  [id]
  (json/write-str (add-ts-period (process-log id))))

(defn process-encounters
  [file-name output-name]
  (doall
   (map (fn [x y] (let [blah (respond y)] (println x) #_(spit output-name (str blah "\n") :append true) blah))
        (range)
        (sequence (comp
                   (map #(string/split % #","))
                   (map first))
                  (take 1 (rest (rest (string/split (slurp file-name) #"\n"))))))))

(def foo (time (process-encounters "fightsandencounters2268.txt" "grong-sucks-3.txt")))
#_(process-encounters "fightsandencounters2268.txt" "grong-sucks-3.txt")

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
