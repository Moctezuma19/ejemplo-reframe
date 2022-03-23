(ns reframeproject.views
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [reframeproject.subs :as subs]
   [reframeproject.events :as events]))

(def i (reagent/atom 0))

(defn handleClick [valor]
  (swap! i inc)
  (re-frame/dispatch [::events/agregatexto {:id @i :texto @(re-frame/subscribe [::subs/texto])}]))

(defn formulario []
  [:form.box {:style {:margin-left "10px"} :on-submit #(.preventDefault %)}
   [:div.field
    [:label.label "Texto"]
    [:div.control
     [:input.input {:placeholder "Ingresa tu texto"
                    :value       @(re-frame/subscribe [::subs/texto])
                    :on-change   #(re-frame/dispatch [::events/onchangetexto (-> % .-target .-value)])
                    :required    true}]
     ]
    ]
   [:button.button.is-primary {:type "button" :on-click #(handleClick %)} "Agregar"]
  ]
  )

(defn notas [nota]
  ^{:key (:id nota)}
  [:div.box {:style {:margin-right "10px"}}
   [:span.icon {:style {:float "right" :cursor "pointer"} :on-click #(re-frame/dispatch [::events/eliminatexto (:id nota)])} [:i.fas.fa-close]]
   [:p (:texto nota)]]
  )

(defn main-panel []
  [:div.content {:style {:margin-top "10px"}}
   [:div.columns
    [:div.column
     [:div (formulario)]]
    [:div.column
     (map notas @(re-frame/subscribe [::subs/notas]))
     ]
    ]
   ])
