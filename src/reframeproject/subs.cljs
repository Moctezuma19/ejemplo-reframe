(ns reframeproject.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::texto
 (fn [db]
   (:texto db)))

(re-frame/reg-sub
 ::notas
 (fn [db]
   (:notas db)))

(re-frame/reg-sub
 ::seleccionados
 (fn [db]
   (:seleccionados db)))

(re-frame/reg-sub
 ::total
 :<- [::notas]
 (fn [notas _]
   (count (filter #(-> % :seleccionado not) notas))))

(re-frame/reg-sub
 ::hay-marcadas
 :<- [::notas]
 (fn [notas]
   (->> notas
        vals
        (some :seleccionado))))

(re-frame/reg-sub
 ::opcion-seleccionada
 (fn [db]
   (:opcion-seleccionada db)))

(re-frame/reg-sub
 ::opcion-seleccionada?
 (fn [db [_ opcion]]
   (= (:opcion-seleccionada db) opcion)))

(re-frame/reg-sub
 ::id-texto-editando
 (fn [db]
   (:id-texto-editando db)))

(re-frame/reg-sub
 ::notas-seleccionadas
 :<- [::opcion-seleccionada]
 :<- [::notas]
 (fn [[opcion-seleccionada notas]]
   (case opcion-seleccionada
     :todas     notas
     :no-leidas (filter (complement :seleccionado) notas)
     :leidas    (filter :seleccionado notas))))
  
