(ns my-app.components.todo-item
  (:require [my-app.state :as state]))

(defn todo-item [todo]
  (let [{:keys [id text deadline done?]} todo]
    [:div {:style {:display "flex" :gap "1rem"
                   :text-decoration (when done? "line-through")}}
     [:input {:type "checkbox"
              :checked done?
              :on-change #(do
                            (swap! state/app-db update :todos
                                   (fn [todos]
                                     (mapv (fn [t]
                                             (if (= (:id t) id)
                                               (update t :done? not)
                                               t))
                                           todos)))
                            (state/save-to-storage!))}]
     [:span text " (до: " (.toLocaleString deadline) ")"]
     [:button {:on-click #(state/delete-todo! id)} "🗑 Удалить"]]))
