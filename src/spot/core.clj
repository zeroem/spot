(ns spot.core
  (require [clojure.set]))

(defrecord Triple [s p o t])

(defprotocol TripleStoreProtocol
  (query [this ^Triple f])
  (add! [this ^Triple t])
  (all [this]))

(def triple-keys [:s :p :o :t])

(defn a-triple-index [] {:s (atom {}) :p (atom {}) :o (atom {}) :t (atom {})})

(defn with-each-triple-key [f & [initial-value]]
  (loop [K triple-keys accumulator initial-value]
    (if-let [k (first K)]
      (recur (rest K) (f k accumulator))
      accumulator)))

(defn set-of-matching-triples [I_k f]
  (reduce (fn [a b] (clojure.set/union a @b))
          #{}
          (vals (filter (fn [[v t]] (f v)) I_k))))

(defn index-query-futures [I f]
  (with-each-triple-key
    (fn [k r]
      (if-let [f_k (k f)]
        (do
          (conj r (future (set-of-matching-triples @(k I) f_k))))
        r))
    []))

(defn index-triple-key! [I ^Triple t k]
  (let [target-index (k I)
        index-key (k t)]
    (if (not (contains? @target-index index-key))
      (swap! target-index #(assoc % index-key (atom #{}))))
    (swap! (get @target-index index-key) #(conj % t))))


(defn index-triple! [I ^Triple t]
  (with-each-triple-key
    (fn [k r]
      (index-triple-key! I t k))))

(defrecord TripleStore [S I]
  TripleStoreProtocol
  (add! [this t]
    (swap! (.S this) (fn [S] (conj S t)))
    (index-triple! (.I this) t))

  (all [this]
    @(.S this))

  (query [this f] 
    (let [futures (index-query-futures @(.I this) f)]
      (loop [F futures]
        (if-let [k (first F)]
          (recur (rest F))))
      (apply clojure.set/intersection futures)
    )))

(defn a-triple-store []
  "Create a new triple store"
  (TripleStore. (atom []) (a-triple-index)))

(def match-all (Triple. (fn [t] true)(fn [t] true) (fn [t] true) (fn [t] true)))
