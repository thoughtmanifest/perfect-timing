(defproject wow-log-parser "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-http "3.9.1"]
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot wow-log-parser.core
  :target-path "target/%s"
  :jvm-opts ["-Xmx4g"] 
  :profiles {:uberjar {:aot :all}})
