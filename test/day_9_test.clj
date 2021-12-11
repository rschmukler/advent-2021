(ns day-9-test
  (:require [day-10 :as sut]
            [clojure.test :refer [deftest testing is]]))

(def example-input
  [[2 1 9 9 9 4 3 2 1 0]
   [3 9 8 7 8 9 4 9 2 1]
   [9 8 5 6 7 8 9 8 9 2]
   [8 7 6 7 8 9 6 7 8 9]
   [9 8 9 9 9 6 5 6 7 8]])


(deftest get-height-test
  (is (= 5
         (sut/get-height [[0 1]
                          [5 6]] [0 1]))))

(deftest is-low-point-test
  (is (sut/is-low-point? example-input [1 0])))

(deftest find-low-points-test
  (is (= [[1 0]
          [9 0]
          [2 2]
          [6 4]]
         (sut/find-low-points example-input))))


(deftest solve-part-one-test
  (is (= 15
         (sut/solve-part-one example-input))))

(deftest find-basin-at-low-point
  (is (= #{[0 0]
           [0 1]
           [1 0]}
         (sut/find-basin-at-low-point example-input [0 0]))))

(deftest solve-part-two-test
  (is (= 1134
         (sut/solve-part-two example-input))))
