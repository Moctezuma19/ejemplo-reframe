(ns reframeproject.coeffects
  (:require
   [re-frame.core :as re-frame]))

(def siguiente-id
  (let [siguiente-id (atom 0)]
    (fn [cofx _]
      (assoc cofx :nuevo-id (swap! siguiente-id inc)))))


(re-frame/reg-cofx
 :nuevo-id
 siguiente-id)