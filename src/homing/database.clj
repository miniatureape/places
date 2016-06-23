(ns homing.database
  (:require [clojure.java.jdbc :as jdbc]))

(def db {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname "db/database.db"})

(defn create-places-table []
  (jdbc/create-table-ddl :places
                         [[:longtitude :text]
                          [:latitude :text]
                          [:title :text]
                          [:description :text]]))

(defn create-db []
  (try (jdbc/db-do-commands db (create-places-table))
  (catch Exception e (println e))))