(ns my-app.state
  (:require [reagent.core :as r]))

(defonce app-db (r/atom {:todos []}))

(defn save-to-storage! []
  (let [todos (:todos @app-db)]
    (.setItem js/localStorage "todos"
              (js/JSON.stringify (clj->js todos)))))

(defn load-from-storage! []
  (let [raw (.getItem js/localStorage "todos")]
    (when raw
      (let [todos-json (js/JSON.parse raw)
            todos-clj (js->clj todos-json :keywordize-keys true)
            todos (mapv (fn [todo]
                          (update todo :deadline (fn [d] (js/Date. d))))
                        todos-clj)]
        (swap! app-db assoc :todos todos)))))

(defn delete-todo! [id]
  (swap! app-db update :todos
         (fn [todos]
           (remove #(= (:id %) id) todos)))
  (save-to-storage!))

(defn update-todo! [id updates]
  (swap! app-db update :todos
         (fn [todos]
           (mapv (fn [t]
                   (if (= (:id t) id)
                     (merge t updates)
                     t))
                 todos)))
  (save-to-storage!))