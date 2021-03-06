(defproject homing "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [
                 [org.clojure/clojure "1.8.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.5.1"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [org.xerial/sqlite-jdbc "3.8.11.2"]
                 [com.google.maps/google-maps-services "0.1.15"]
                 [ring/ring-json "0.4.0"]
                 ]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler homing.core/application :auto-reload? true})

