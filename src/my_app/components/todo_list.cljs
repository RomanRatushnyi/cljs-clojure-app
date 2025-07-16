(ns my-app.components.todo-list
  (:require [my-app.state :as state]
            [my-app.components.todo-item :refer [todo-item]]
            [my-app.utils :refer [sorted-todos]]))

(defn todo-list []
  (let [todos (:todos @state/app-db)]
    [:div
     (for [todo (sorted-todos todos)]
       ^{:key (:id todo)} [todo-item todo])]))
