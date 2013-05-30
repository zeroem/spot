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
      

(defrecord TripleStore [a]
  triplj.core.TripleStore
  (filter [this f]
    (filter 
      #(test-triple f %)
      @(.a this)))
  (conj [this t]
    (swap! (.a this) (fn [a] (conj a t))))
  (all [this]
    @(.a this))
  )

