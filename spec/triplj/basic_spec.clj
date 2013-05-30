(ns triplj.basic_spec
  (:use [speclj.core] [triplj.basic])
  (:require [triplj.core]))

(def example-triple (triplj.core.Triple. "s" "p" "o" "t"))

(describe "Basic in memory Triple Store"
  (it "Can test all triple elements"
    (should 
      (let [f (triplj.core.Triple. #(= "s" %) #(= "p" %) #(= "o" %) #(= "t" %))]
          (test-triple f example-triple))))

  (it "Short circuits on false"
    (should
      (let [f (triplj.core.Triple. (fn [t] true) (fn [t] false) #(should-fail "this should never be reached") (fn [t] false))]
        test-triple f example-triple)))
          
  (it "Can add new elements"
    (let [s (triplj.basic.TripleStore. (atom []))]
      (.conj s example-triple)
      (should= 1 (count (.all s)))
      (should= example-triple (first (.all s)))))
)
