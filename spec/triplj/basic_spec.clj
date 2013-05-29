(ns triplj.basic_spec
  (:use [speclj.core] [triplj.basic])
  (:require [triplj.core]))

(def example-triple (triplj.core.Triple. "s" "p" "o" "t"))

(describe "Basic in memory Triple Store"
  (it "Can test all triple elements"
    (let [t example-triple
          f (triplj.core.Triple. #(= "s" %) #(= "p" %) #(= "o" %) #(= "t" %))]
          (should (test-triple f t))))
          
  (it "Short circuits on false"
    (let [t example-triple
          f (triplj.core.Triple. (fn [t] true) (fn [t] false) #(should-fail "this should never be reached") (fn [t] false))]
          (should-not (test-triple f t))))
          
          )
