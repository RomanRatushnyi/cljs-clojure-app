(ns my-app.core
  (:require [reagent.core :as r]
            [reagent.dom.client :as rdom]))

(defonce root (rdom/create-root (js/document.getElementById "app")))

(defn hello-world []
  [:div
   [:h1 "Hello, React 18 + Reagent!"]
   [:p "С использованием createRoot()"]])

(defn init []
  (rdom/render root [hello-world]))
