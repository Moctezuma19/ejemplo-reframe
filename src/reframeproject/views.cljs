(ns reframeproject.views
  (:require
   [re-frame.core :as re-frame]
   [reframeproject.subs :as subs]
   [reframeproject.events :as events]))

(defn formulario []
  [:form.box {:style     {:margin-left "10px"}
              :on-submit #(.preventDefault %)}
   [:div.field
    [:label.label "Texto"]
    [:div.control
     [:input.input {:placeholder "Ingresa tu texto"
                    :value       @(re-frame/subscribe [::subs/texto])
                    :on-change   #(re-frame/dispatch [::events/on-change-texto 
                                                      (-> % .-target .-value)])
                    :required    true}]
     ]
    ]
   [:button.button.is-primary {:type     "button"
                               :on-click #(re-frame/dispatch [::events/guarda-texto @(re-frame/subscribe [::subs/texto])])}
    "Agregar"]])

(defn notas 
  [[id nota]]
  ^{:key id}
  [:div.box {:style {:margin-right "10px"}}
   [:span.icon {:style    {:float  "right"
                           :cursor "pointer"}
                :on-click #(re-frame/dispatch [::events/elimina-texto id])} 
    [:i.fas.fa-close]]
   [:p nota]])

(defn main-panel 
  []
  [:div.content {:style {:margin-top "10px"}}
   [:div.columns
    [:div.column
     [:div [formulario]]]
    [:div.column
     (map notas @(re-frame/subscribe [::subs/notas]))]]])
