(ns my-app.components.auth-form
  (:require [reagent.core :as r]
            [my-app.router :refer [check-auth]]))

(defn auth-form []
  (let [input (r/atom "")]
    (fn []
      [:div
       [:h2 "Авторизация"]
       [:input {:type "password"
                :placeholder "Введите ключ"
                :value @input
                :on-change #(reset! input (-> % .-target .-value))}]
       [:button {:on-click #(check-auth @input)} "Войти"]])))
