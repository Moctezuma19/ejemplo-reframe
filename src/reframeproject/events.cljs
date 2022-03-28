(ns reframeproject.events
  (:require
   [re-frame.core :as re-frame]
   [reframeproject.db :as db]
   [reframeproject.coeffects :as coeffects]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::on-change-texto
 (fn [db [_ newTexto]]
   (assoc db :texto newTexto)))

(re-frame/reg-event-db
 ::elimina-texto
 (fn [db [_ id]]
   (assoc db :notas (dissoc (db :notas) id))))

(re-frame/reg-event-db
 ::cambia-opcion-seleccionada
 (fn [db [_ opcion]]
   (assoc db :opcion-seleccionada opcion)))

(re-frame/reg-event-db
 ::selecciona-texto
 (fn [db [_ id-texto]]
   (assoc db :notas (assoc-in (db :notas) [id-texto :seleccionado]  (not (((db :notas) id-texto) :seleccionado))))))

(re-frame/reg-event-db
 ::cambia-texto
 (fn [db [_ id-texto nuevoTexto]]
   (assoc db :notas (assoc-in (db :notas) [id-texto :texto]  nuevoTexto))))

(defn actualiza-nota
  [nota seleccionado]
  (let [[id-nota contenido] nota]
    [id-nota (assoc contenido :seleccionado seleccionado)]))

(re-frame/reg-event-db
 ::cambia-seleccionados
 (fn [db [_]]
   (assoc db :notas (into {} (map #(actualiza-nota % (not (db :seleccionados))) (db :notas))) :seleccionados (not (db :seleccionados)))))

(re-frame/reg-event-db
 ::elimina-seleccionados
 (fn [db [_]]
   (assoc db :notas (into {} (filter (fn [[_ v]] (false? (v :seleccionado))) (db :notas))))))

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
