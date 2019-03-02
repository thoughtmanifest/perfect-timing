(ns lint
  (:require [bikeshed.core]
            [kibit.driver :as kibit]
            [clojure.term.colors :as colors]))

(defn run-bikeshed
  [path]
  (let [report (bikeshed.core/bikeshed
                {:source-paths [path]}
                {:check? #{:long-lines
                           :trailing-whitespace
                           :trailing-blank-lines
                           :var-redefs}})]
    (if (empty? report)
      (println (str (colors/green "✓ ")
                    "Bikeshed reported no issues for " path))
      1)))

(defn run-kibit
  [path]
  (let [errors (kibit/run [path] nil)]
    (if (empty? errors)
      (println (str (colors/green "✓ ")
                    "Kibit reported no issues for " path))
      1)))

(println "Running bikeshed...")
(let [results (doall (pmap #(run-bikeshed %) *command-line-args*))]
  (when (not (every? nil? results))
    (println (colors/red "Please fix the errors addressed above"))
    (System/exit 1)))

(println "Running kibit...")
(let [results (doall (pmap #(run-kibit %) *command-line-args*))]
  (when (not (every? nil? results))
    (println (colors/red "Please fix the errors addressed above"))
    (System/exit 1)))

(System/exit 0)
