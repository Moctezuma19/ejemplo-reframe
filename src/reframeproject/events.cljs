(ns reframeproject.events
  (:require
   [re-frame.core :as re-frame]
   [reframeproject.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::onchangetexto
 (fn [db [_ newTexto]]
      (assoc db :texto newTexto)))

(re-frame/reg-event-db
 ::agregatexto
 (fn [db [_ texto]]
   (assoc db :notas (conj (db :notas) texto))))

(re-frame/reg-event-db
 ::eliminatexto
 (fn [db [_ id]]
   (assoc db :notas (remove #(= id (:id %)) (:notas db)))))
