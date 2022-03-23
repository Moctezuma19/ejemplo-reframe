(ns reframeproject.events
  (:require
   [re-frame.core :as re-frame]
   [reframeproject.db :as db]
   [reframeproject.coeffects :as coeffects]
   ))

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

(re-frame/reg-event-fx
 ::guarda-texto
 [(re-frame/inject-cofx :nuevo-id)] ;; <- esto es nuevo, aquí estamos inyectando el coefecto
 (fn [cofx [_ texto]]
   (let [{:keys [db nuevo-id]} cofx]
     {:db (assoc-in db [:notas nuevo-id] texto)})))
