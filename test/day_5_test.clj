(ns day-5-test
  (:require [day-5 :as sut]
            [clojure.test :refer [deftest testing is]]
            [cuerdas.core :as str]))

(def example-input
  "0,9 -> 5,9
   8,0 -> 0,8
   9,4 -> 3,4
   2,2 -> 2,1
   7,0 -> 7,4
   6,4 -> 2,0
   0,9 -> 2,9
   3,4 -> 1,4
   0,0 -> 8,8
   5,5 -> 8,2")

(deftest input->line-test
  (is (= {:x1 0 :y1 9 :x2 5 :y2 9}
         (sut/input->line "0,9 -> 5,9"))))

(deftest line->coordiantes-test
  (is (= [[0 1]
          [0 2]
          [0 3]]
         (sut/line->coordinates {:x1 0 :x2 0
                                 :y1 1 :y2 3})))
  (is (= [[1 1]
          [2 2]
          [3 3]]
         (sut/line->coordinates {:x1 1 :x2 3
                                 :y1 1 :y2 3})))
  (is (= [[9 7]
          [8 8]
          [7 9]]
         (sut/line->coordinates {:x1 9 :x2 7
                                 :y1 7 :y2 9}))))

(deftest solve-part-one-test
  (is (= 5
         (->> example-input
              (str/lines)
              (map sut/input->line)
              (sut/solve-part-one)))))

(deftest solve-part-two-test
  (is (= 12
         (->> example-input
              (str/lines)
              (map sut/input->line)
              (sut/solve-part-two)))))
