(ns triplj.core)

(defrecord Triple [s p o t])

(defprotocol TripleStore
  (query [this ^Triple f])
  (add [this ^Triple t]))
