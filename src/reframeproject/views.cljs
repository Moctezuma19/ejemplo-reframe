(ns reframeproject.views
  (:require
   [re-frame.core :as re-frame]
   [reframeproject.subs :as subs]
   [reframeproject.events :as events]))


(defn notas
  [[id nota]]
  ^{:key id}
  [:div.box {:style {:border-radius 0
                     :margin-bottom 0
                     :display       @(re-frame/subscribe [::subs/muestra? (nota :seleccionado)])}}
   [:div {:style {:display "inline-flex"
                  :width   "95%"}}
    [:span.icon {:style    {:border        "1px solid lightgray"
                            :border-radius "13px"
                            :color         "lightgreen"
                            :cursor        "pointer"
                            :margin-top    "2%"
                            :visibility    (when (= id @(re-frame/subscribe [::subs/id-texto-editando])) "hidden")}
                 :on-click #(re-frame/dispatch [::events/selecciona-texto id])}
     [:i.fas.fa-check {:style {:display (if (nota :seleccionado)
                                          "block"
                                          "none")}}]]
    [:input.input {:style           {:text-decoration (when (nota :seleccionado)
                                                        "line-through")
                                     :color           (when (nota :seleccionado)
                                                        "lightgray")

                                     :border          (when-not (= id @(re-frame/subscribe [::subs/id-texto-editando])) "none")
                                     :margin-left     "10px"}
                   :read-only       (if (= id @(re-frame/subscribe [::subs/id-texto-editando])) false true)
                   :on-double-click #(re-frame/dispatch [::events/cambia-id-texto-editando id])
                   :on-blur         #(re-frame/dispatch [::events/cambia-id-texto-editando -1])
                   :on-change       #(re-frame/dispatch [::events/cambia-texto id (-> % .-target .-value)])
                   :value           (nota :texto)}]]
   [:div {:style {:display "inline-flex"
                  :width   "5%"}}
    [:span.icon {:style    {:visibility (when (= id @(re-frame/subscribe [::subs/id-texto-editando])) "hidden")
                            :cursor     "pointer"}
                 :on-click #(re-frame/dispatch [::events/elimina-texto id])}
     [:i.fas.fa-close]]]])

(defn formulario []
  [:div
   [:form.box {:on-submit #(.preventDefault % (re-frame/dispatch [::events/guarda-texto @(re-frame/subscribe [::subs/texto])]))
               :style     {:margin-bottom 0
                           :border-radius "6px 6px 0 0"}}
    [:div.field
     [:div.control {:style {:display "flex"}}
      [:span.icon {:style    {:margin-right 5
                              :margin-top   "2%"
                              :cursor       "pointer"
                              :color        (when @(re-frame/subscribe [::subs/seleccionados]) 
                                              "lightgray")}
                   :on-click #(re-frame/dispatch [::events/cambia-seleccionados])}
       [:i.fas.fa-chevron-down]]
      [:input.input {:placeholder "Ingresa tu texto"
                     :value       @(re-frame/subscribe [::subs/texto])
                     :on-change   #(re-frame/dispatch [::events/on-change-texto (-> % .-target .-value)])
                     :required    true}]]]]
   (doall (map notas @(re-frame/subscribe [::subs/notas])))
   [:div.box {:style {:border-radius "0 0 6px 6px"}}
    [:span {:style {:margin-right "10px"}}
     (str "Por leer: " @(re-frame/subscribe [::subs/total]))]
    [:span {:style    {:margin-right    "10px"
                       :cursor          "pointer"
                       :text-decoration (when @(re-frame/subscribe [::subs/opcion-seleccionada? 1]) 
                                          "underline")}
            :on-click #(re-frame/dispatch [::events/cambia-opcion-seleccionada 1])} 
     "Todas"]
    [:span {:style    {:margin-right    "10px"
                       :cursor          "pointer"
                       :text-decoration (when @(re-frame/subscribe [::subs/opcion-seleccionada? 2]) 
                                          "underline")}
            :on-click #(re-frame/dispatch [::events/cambia-opcion-seleccionada 2])}
     "No leídas"]
    [:span {:style    {:margin-right    "10px"
                       :cursor          "pointer"
                       :text-decoration (when @(re-frame/subscribe [::subs/opcion-seleccionada? 3]) 
                                          "underline")}
            :on-click #(re-frame/dispatch [::events/cambia-opcion-seleccionada 3])}
     "Leídas"]
    [:span {:style    {:display (when-not @(re-frame/subscribe [::subs/hay-marcadas]) 
                                  "none")
                       :cursor  "pointer"}
            :on-click #(re-frame/dispatch [::events/elimina-seleccionados])}
     "Limpiar leídas"]]
   [:div.box {:style {:z-index     -1
                      :position    "absolute"
                      :margin-top  "-60px"
                      :margin-left "3px"
                      :width       "32%"}}]
   [:div.box {:style {:z-index     -2
                      :position    "absolute"
                      :margin-top  "-56px"
                      :margin-left "10px"
                      :width       "31%"}}]])

(defn main-panel 
  []
  [:div.content {:style {:margin-top "10px"}}
   [:div.columns
    [:div.column]
    [:div.column
     [:div [formulario]]]
    [:div.column]]])
