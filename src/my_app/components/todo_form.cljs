(ns my-app.components.todo-form
  (:require [my-app.state :as state]
            [my-app.utils :as utils]
            [reagent.core :as r]))

(defn default-deadline []
  (let [now (js/Date.)
        _ (.setHours now (+ (.getHours now) 1))]
    (.toISOString now)
    (subs (.toISOString now) 0 16)))

(defn todo-form []
  (let [text (r/atom "")
        deadline (r/atom (default-deadline))
        error (r/atom nil)]
    (fn []
      [:div
       [:input {:type "text"
                :placeholder "Задача"
                :value @text
                :on-change #(reset! text (-> % .-target .-value))}]
       [:input {:type "datetime-local"
                :value @deadline
                :on-change #(reset! deadline (-> % .-target .-value))}]
       (when @error [:div.error @error])
       [:button
        {:on-click
         #(let [new-todo {:id (random-uuid)
                          :text @text
                          :deadline (js/Date. @deadline)
                          :done? false}
                err (utils/validate-todo new-todo)]
            (if err
              (reset! error err)
              (do
                (swap! state/app-db update :todos conj new-todo)
                (state/save-to-storage!)
                (reset! text "")
                (reset! deadline (default-deadline))
                (reset! error nil))))}
        "Добавить"]])))
