(ns homing.core
  (:use ring.adapter.jetty)
  (:require [ring.middleware.params :refer [wrap-params]])
  (:require [ring.middleware.keyword-params :refer [wrap-keyword-params]])
  (:require [ring.middleware.json :refer [wrap-json-params wrap-json-response]])
  (:require [compojure.core :refer :all])
  (:require [homing.database :refer [query-place insert-place]])
  (:import com.google.maps.GeocodingApi)
  (:import com.google.maps.GeoApiContext))

; Google Geocoding

(def google-api-key "AIzaSyCWBbuS69GavPJdKd73g9OIk6tO4evBGdQ")
(defn get-geocoding-api-context [] (.setApiKey (GeoApiContext.) google-api-key))


; Api Implementation

(defn get-place [longitude latitude] (query-place longitude latitude))
(defn add-place [longitude latitude params] (insert-place (assoc params :longitude longitude :latitude latitude)))
(defn lookup-place [query] (let [request (GeocodingApi/geocode (get-geocoding-api-context) query)
                                 ; TODO check if there is at least one, if more than one, perhaps return all?
                                 result (first (.await request))
                                 longitude (.lng (.location (.geometry result)))
                                 latitude (.lat (.location (.geometry result)))]
                             {:body {:longitude longitude :latitude latitude}}))

; Api Routes

(defroutes api-routes

  ; Debug

  (GET "/" request (str request))

  ; Places

  (GET "/place/:longitude/:latitude" [longitude latitude] (get-place longitude latitude))
  (POST "/place/:longitude/:latitude" [longitude latitude & params] (add-place longitude latitude params))

  ; Lookup Service

  (GET "/place/lookup" {{query "query"} :query-params} (lookup-place query)))


(def application
  (-> api-routes
      wrap-json-response
      wrap-params
      wrap-keyword-params
      wrap-json-params))