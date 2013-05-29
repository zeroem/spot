(ns triplj.basic
  (:require [triplj.core]))

(defn test-triple [f, ^triplj.core.Triple t]
  (loop [remaining-keys (keys f)]
    (cond 
      (empty? remaining-keys) true
      :else
      (let [k (first remaining-keys)
            check ((k f) (k t))]
        (cond (not check) false
              :else
              (recur (rest remaining-keys)))))))
      




(comment
(defrecord TripleStore [t]
  core/TripleStore
  (query [this f]
    (filter 
      (.t this))))
)

