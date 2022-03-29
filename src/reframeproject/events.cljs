(ns reframeproject.events
  (:require
   [re-frame.core :as re-frame]
   [reframeproject.db :as db]
   [reframeproject.coeffects :as coeffects]
   [medley.core :as medley]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::on-change-texto
 (fn [db [_ nuevo-texto]]
   (assoc db :texto nuevo-texto)))

(re-frame/reg-event-db
 ::elimina-texto
 (fn [db [_ id]]
   (assoc db :notas (dissoc (db :notas) id))))

(re-frame/reg-event-db
 ::cambia-opcion-seleccionada
 (fn [db [_ opcion]]
   (assoc db :opcion-seleccionada opcion)))

(re-frame/reg-event-db
 ::selecciona-nota
 (fn [db [_ id-texto]]
   (update-in db [:notas id-texto :seleccionado] #(-> % not))))

(re-frame/reg-event-db
 ::cambia-texto
 (fn [db [_ id-texto nuevo-texto]]
   (update-in db [:notas id-texto :texto] nuevo-texto)))

(re-frame/reg-event-db
 ::cambia-seleccionados
 (fn [db [_]]
   (assoc 
    db 
    :notas 
    (medley/map-vals  #(assoc % :seleccionado (-> db :seleccionados not)) (db :notas))
    :seleccionados 
    (-> db :seleccionados not))))

(re-frame/reg-event-db
 ::elimina-seleccionados
 (fn [db [_]]
   (assoc db :notas (medley/remove-vals :seleccionado (db :notas)))))

(re-frame/reg-event-fx
 ::guarda-texto
 [(re-frame/inject-cofx :nuevo-id)] ;; <- esto es nuevo, aquí estamos inyectando el coefecto
 (fn [cofx [_ texto]]
   (let [{:keys [db nuevo-id]} cofx]
     (re-frame/dispatch [::on-change-texto ""])
     {:db (assoc-in db [:notas nuevo-id] {:seleccionado false :texto texto})})))

(re-frame/reg-event-db
 ::cambia-id-texto-editando
 (fn [db [_ id-texto]]
   (assoc db :id-texto-editando id-texto)))
