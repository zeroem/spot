(ns triplj.core_spec
  (require [triplj.core :as triplj]
           [speclj.core :refer :all]))

(def example-triple (triplj.core.Triple. "s" "p" "o" "t"))

(describe "Basic Indexing functionality"
    (it "Can index an element of the triple"
        (let [index (triplj/a-triple-index)]
          (triplj/index-triple-key! index  example-triple :o)
          (should (contains? @(:o index) (:o example-triple)))
          (should= example-triple (first @(get @(:o index) "o")))))
          )
