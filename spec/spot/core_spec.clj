(ns spot.core_spec
  (require [spot.core :as spot]
           [speclj.core :refer :all]))

(def example-triple (spot.core.Triple. "s" "p" "o" "t"))

(describe "Basic Indexing functionality"
    (it "Can index an element of the triple"
        (let [index (spot/a-triple-index)]
          (spot/index-triple-key! index  example-triple :o)
          (should (contains? @(:o index) (:o example-triple)))
          (should= example-triple (first @(get @(:o index) "o")))))

    (it "Can index an entire triple"
        (let [index (spot/a-triple-index)]
          (spot/index-triple! index  example-triple)
          (spot/with-each-triple-key
            (fn [k _]
              (should (contains? @(k index) (k example-triple)))
              (should= example-triple (first @(get @(k index) (k example-triple))))))))
    (it "Can generate futures against an index"
        (let [index (spot/a-triple-index)
              f (spot/with-each-triple-key (fn [k r] (assoc r k (fn [t] true))) {})
              F (spot/index-query-futures index f)]
          (should= 4 (count F))
          )))
