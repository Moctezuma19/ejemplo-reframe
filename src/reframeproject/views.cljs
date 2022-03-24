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
                     :display (if @(re-frame/subscribe [::subs/visible]) "block" "none")}}
   [:span.icon {:style    {:float  "right"
                           :cursor "pointer"}
                :on-click #(re-frame/dispatch [::events/elimina-texto id])}
    [:i.fas.fa-close]]
   [:p nota]])

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
                              :color        (when @(re-frame/subscribe [::subs/visible]) "lightgray")
                              }
                   :on-click #(re-frame/dispatch [::events/cambia-visibilidad])}
       [:i.fas.fa-chevron-down]]
      [:input.input {:placeholder "Ingresa tu texto"
                     :value       @(re-frame/subscribe [::subs/texto])
                     :on-change   #(re-frame/dispatch [::events/on-change-texto (-> % .-target .-value)])
                     :required    true}]]]]
   (map notas @(re-frame/subscribe [::subs/notas]))
   [:div.box {:style {:border-radius "0 0 6px 6px"}} 
    (str "Total: " @(re-frame/subscribe [::subs/total]))]
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
