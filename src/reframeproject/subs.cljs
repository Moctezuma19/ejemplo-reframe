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
   (count (filter (fn [[_ v]] (not (v :seleccionado))) notas))))

(re-frame/reg-sub
 ::hay-marcadas
 :<- [::notas]
 :<- [::total]
 (fn [[notas total] _]
   (>  (- (count notas) total) 0)))


(re-frame/reg-sub
 ::opcion-seleccionada
 (fn [db]
   (:opcion-seleccionada db)))

(re-frame/reg-sub
 ::opcion-seleccionada?
 (fn [db [_ opcion]]
   (= (:opcion-seleccionada db) opcion)))

(re-frame/reg-sub
 ::muestra?
 :<- [::opcion-seleccionada]
 (fn [opcion-seleccionada [_ seleccionado]]
   (case opcion-seleccionada
     1 "block"
     2 (if (not seleccionado) "block" "none")
     3 (if seleccionado "block" "none")
     _ "none")))

(re-frame/reg-sub
 ::id-texto-editando
 (fn [db]
   (:id-texto-editando db)))
  
