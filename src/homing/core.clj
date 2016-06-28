(ns homing.core
  (:use ring.adapter.jetty)
  (:require [ring.middleware.params :refer [wrap-params]])
  (:require [compojure.core :refer :all])
  (:require [homing.database :refer [query-place insert-place]])
  (:import com.google.maps.GeocodingApi)
  (:import com.google.maps.GeoApiContext))

(def google-api-key "AIzaSyCWBbuS69GavPJdKd73g9OIk6tO4evBGdQ")
(defn get-geocoding-api-context [] (.setApiKey (GeoApiContext.) google-api-key))

(defn get-place [longitude latitude] (query-place longitude latitude))
(defn add-place [longitude latitude params] (insert-place (assoc params :longitude longitude :latitude latitude)))
(defn lookup-place [query] (let [request (GeocodingApi/geocode (get-geocoding-api-context) query)
                                 result (first (.await request))
                                 longitude (.lng (.location (.geometry result)))
                                 latitude (.lat (.location (.geometry result)))]
                             (pr-str longitude latitude)))

(defroutes api-routes

  (GET "/" request (str request))

  ; Places

  (GET "/place/:longitude/:latitude" [longitude latitude] (get-place longitude latitude))
  (POST "/place/:longitude/:latitude" [longitude latitude & params] (add-place longitude latitude params))

  ; Lookup Service

  (GET "/place/lookup" {{query "query"} :query-params} (lookup-place query)))


(def application (wrap-params (routes api-routes)))