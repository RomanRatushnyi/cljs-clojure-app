(ns my-app.utils
  (:require [clojure.string :as str]))

(defn validate-todo [todo]
  (cond
    (str/blank? (:text todo)) "Текст задачи обязателен"
    (not (:deadline todo)) "Дедлайн обязателен"
    :else nil))

(defn todo-sort-key [todo]
  [(if (:done? todo) 1 0)
   (some-> (:deadline todo) .getTime)])

(defn sorted-todos [todos]
  (sort-by todo-sort-key todos))
