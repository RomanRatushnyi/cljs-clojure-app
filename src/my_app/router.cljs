(ns my-app.router
  (:require [reagent.core :as r]
            [my-app.keys :refer [valid-keys]]))

(defonce route (r/atom :auth))

(defonce auth-key (r/atom nil))

(defn check-auth [k]
  (if (valid-keys k)
    (do
      (reset! auth-key k)
      (reset! route :todos))
    (js/alert "Неверный ключ!")))
