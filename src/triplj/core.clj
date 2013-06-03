(ns triplj.core
  (require [clojure.set]))

(def triple-keys [:s :p :o :t])
(defn a-triple-index [] {:s (atom {}) :p (atom {}) :o (atom {}) :t (atom {})})

(defrecord Triple [s p o t])

(defprotocol TripleStoreProtocol
  (query [this ^Triple f])
  (add [this ^Triple t])
  (all [this]))

(defn index-query-futures [i f]
  (loop [K triple-keys futures []]
    (if (empty? K)
      futures)
    (let [k (first K)]
      (recur (rest K) (future (filter (k f) @(k @i)))))))

(defn index-triple-key! [i ^Triple t k]
  (let [target-index (k i)
        index-key (k t)]
    (if (not (contains? @target-index index-key))
      (swap! target-index #(assoc % index-key (atom []))))
    (swap! (get @target-index index-key) #(conj % t))))


(defn index-triple! [i ^Triple t]
  (loop [K triple-keys]
    (if-let [k (first K)]
      (do
        (index-triple-key! i t k)
        (recur (rest K))))))

(defrecord TripleStore [s i]
  TripleStoreProtocol
  (add [this t]
    (swap! (.s this) (fn [s] (conj s t)))
    (index-triple! (.i this) t))

  (all [this]
    @(.s this))

  (query [this f] 
    (let [futures (index-query-futures @(.i this) f)]
      (loop [remaining-futures futures]
        (if-let [k (first remaining-futures)]
          (recur (rest remaining-futures))))
      (apply clojure.set/intersection futures)
    )))

(defn a-triple-store []
  "Create a new triple store"
  (TripleStore. (atom []) (a-triple-index)))

(def match-all (Triple. (fn [t] true)(fn [t] true) (fn [t] true) (fn [t] true)))
