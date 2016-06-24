(ns homing.database
  (:require [clojure.java.jdbc :as jdbc]))

(def db {:classname "org.sqlite.JDBC"
              :subprotocol "sqlite"
              :subname "db/database.db"})

(defn create-places-table []
  (jdbc/create-table-ddl :places
                         [[:longitude :text]
                          [:latitude :text]
                          [:title :text]
                          [:description :text]]))

(defn create-db []
  (try (jdbc/db-do-commands db (create-places-table))
  (catch Exception e (println e))))

(defn query-place
  "Return place data from longitude and latitude"
  [longitude latitude]
  (jdbc/query db ["SELECT * FROM places WHERE longitude = ? AND latitude = ?" longitude latitude]))

(defn insert-place
  "Add a place"
  [place-data]
  (jdbc/insert! db :places place-data))

(defn update-place
   "Update a place defined by longitude and latitude with place-data"
   [place-data longitude latitude]
   (jdbc/update! db :places
                place-data
                ["longitude = ? and latitude = ?" longitude latitude]))

(defn delete-place
  "Update a place defined by longitude and latitude with place-data"
  [longitude latitude]
  (jdbc/delete! db :places
                ["longitude = ? and latitude = ?" longitude latitude]))