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
 (fn [_]
   (count (filter (fn [[_ v]] (not (v :seleccionado))) @(re-frame/subscribe [::notas])))))

(re-frame/reg-sub
 ::hay-marcadas
 (fn [_]
   (>  (- (count @(re-frame/subscribe [::notas])) @(re-frame/subscribe [::total])) 0)))


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
 (fn [_ [_ seleccionado]]
   (let [opcion @(re-frame/subscribe [::opcion-seleccionada])]
     (if (= opcion 1) "block" (if (and (= opcion 2) (not seleccionado)) "block" (if (and (= opcion 3) seleccionado) "block" "none"))))))
  
