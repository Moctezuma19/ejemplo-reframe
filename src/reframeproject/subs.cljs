(ns reframeproject.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::timer
 (fn [db]
   (:timer db)))

(re-frame/reg-sub
 ::texto
 (fn [db]
   (:texto db)))

(re-frame/reg-sub
 ::notas
 (fn [db]
   (:notas db)))

(re-frame/reg-sub
 ::visible
 (fn [db]
   (:visible db)))

(re-frame/reg-sub
 ::total
 (fn [_] 
   (count @(re-frame/subscribe [::notas]))))
