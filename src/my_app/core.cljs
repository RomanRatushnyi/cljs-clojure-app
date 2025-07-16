(ns my-app.core
  (:require [reagent.dom.client :as rdom]
            [reagent.core :as r]
            [my-app.components.todo-form :refer [todo-form]]
            [my-app.components.todo-list :refer [todo-list]]
            [my-app.components.auth-form :refer [auth-form]]
            [my-app.router :as router]
            [my-app.state :as state]))

(defonce root (rdom/create-root (js/document.getElementById "app")))

(defn app []
  (case @router/route
    "/"      [auth-form]
    "/todos" (if @router/auth-key
               [:div
                [:h1 "Todo List"]
                [todo-form]
                [todo-list]]
               [:div "🚫 Доступ запрещён."])
    [:div "404: Страница не найдена"]))

(defn init []
  (state/load-from-storage!)
  (router/init-router!)
  (rdom/render root [app]))
  