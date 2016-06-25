(ns homing.core
  (:use ring.adapter.jetty)
  (:require [compojure.core :refer :all])
  (:require [homing.database :refer [query-place add-place]]))


(defn get-place [longitude latitude] (query-place longitude latitude))
(defn add-place [longitude latitude params] (add-place (assoc params :longitude longitude :latitude latitude)))

(defroutes api-routes
  (GET "/place/:longitude/:latitude" [longitude latitude] (get-place longitude latitude))
  (POST "/place/:longitude/:latitude" [longitude latitude & params] (add-place longitude latitude params)))

(def application (routes api-routes))