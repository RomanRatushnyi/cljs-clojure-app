(ns my-app.router
  (:require [reagent.core :as r]
            [my-app.keys :refer [valid-keys]]))

(defonce route (r/atom "/"))

(defonce auth-key (r/atom nil))

(defn set-route! [path]
  (.pushState js/history nil "" path)
  (reset! route path))

(defn check-auth [k]
  (if (valid-keys k)
    (do
      (reset! auth-key k)
      (set-route! "/todos"))
    (js/alert "Неверный ключ!")))

(defn handle-popstate []
  (reset! route (.-pathname js/location)))

(defn init-router! []
  (reset! route (.-pathname js/location))
  (.addEventListener js/window "popstate" handle-popstate))
