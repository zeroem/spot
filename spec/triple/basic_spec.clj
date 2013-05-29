(ns triple.basic_spec
  (:use [speclj.core] [triple.basic])
  (:require [triple.core]))

(def example-triple (triple.core.Triple. "s" "p" "o" "t"))

(describe "Basic in memory Triple Store"
  (it "Can test all triple elements"
    (let [t example-triple
          f (triple.core.Triple. #(= "s" %) #(= "p" %) #(= "o" %) #(= "t" %))]
          (should (test-triple f t))))
          
  (it "Short circuits on false"
    (let [t example-triple
          f (triple.core.Triple. (fn [t] true) (fn [t] false) #(should-fail "this should never be reached") (fn [t] false))]
          (should-not (test-triple f t))))
          
          )
