(ns triplj.core)

(defrecord Triple [s p o t])

(defprotocol TripleStore
  (filter [this ^Triple f])
  (conj [this ^Triple t])
  (all [this]))

(def match-all (Triple. (fn [t] true)(fn [t] true) (fn [t] true) (fn [t] true)))
