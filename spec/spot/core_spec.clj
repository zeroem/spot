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
          (loop [K spot/triple-keys]
            (if-let [k (first K)]
              (do
                (should (contains? @(k index) (k example-triple)))
                (should= example-triple (first @(get @(k index) (k example-triple))))
                (recur (rest K)))))))
          )
