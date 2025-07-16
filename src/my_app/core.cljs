(ns my-app.core
  (:require [reagent.dom.client :as rdom]
            [reagent.core :as r]
            [my-app.components.todo-form :refer [todo-form]]
            [my-app.components.todo-list :refer [todo-list]]
            [my-app.state :as state]))

(defonce root (rdom/create-root (js/document.getElementById "app")))

(defn app []
  [:div
   [:h1 "Todo List"]
   [todo-form]
   [todo-list]])

(defn init []
  (state/load-from-storage!)
  (rdom/render root [app]))
