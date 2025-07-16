(ns my-app.components.todo-item
  (:require [reagent.core :as r]
            [my-app.state :as state]))

(defn todo-item [id]
  (let [edit-mode?     (r/atom false)
        edit-text      (r/atom "")
        edit-deadline  (r/atom "")]
    (fn [id]
      (let [todo (some #(when (= (:id %) id) %) (:todos @state/app-db))
            {:keys [text deadline done?]} todo]
        (when todo
          [:div {:style {:display "flex" :gap "1rem"
                         :align-items "center"
                         :text-decoration (when done? "line-through")}}

           ;; Checkbox
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

           (if (and (not done?) @edit-mode?)
             [:<>
              [:input {:type "text"
                       :value @edit-text
                       :on-change #(reset! edit-text (-> % .-target .-value))}]
              [:input {:type "datetime-local"
                       :value @edit-deadline
                       :on-change #(reset! edit-deadline (-> % .-target .-value))}]
              [:button {:on-click #(do
                                     (state/update-todo! id {:text @edit-text
                                                             :deadline (js/Date. @edit-deadline)})
                                     (reset! edit-mode? false))}
               "💾 Сохранить"]]

             [:<>
              [:span (str text " (до: " (.toLocaleString deadline) ")")]])

           (when (and (not done?) (not @edit-mode?))
             [:button {:on-click #(do
                                    (reset! edit-text text)
                                    (reset! edit-deadline (subs (.toISOString deadline) 0 16))
                                    (reset! edit-mode? true))}
              "✏️ Редактировать"])

           [:button {:on-click #(state/delete-todo! id)} "🗑 Удалить"]])))))
