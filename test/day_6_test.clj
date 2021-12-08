(ns day-6-test
  (:require [day-6 :as sut]
            [clojure.test :refer [deftest testing is]]))


(deftest initial-state-test
  (is (= {3 2
          4 1
          1 1
          2 1}
         (sut/initial-state "3,4,3,1,2"))))

(deftest run-day-test
  (is (= {2 2
          3 1
          0 1
          1 1
          6 0
          8 0}
         (sut/run-day {3 2 4 1 1 1 2 1})))
  (is (= {0 1
          1 2
          2 1
          5 0
          6 1
          7 0
          8 1}
         (sut/run-day
           {2 2
            3 1
            0 1
            1 1
            6 0
            8 0}))))

(deftest run-simulation-test
  (is (= 26 (sut/run-simulation "3,4,3,1,2" 18)))
  (is (= 5934 (sut/run-simulation "3,4,3,1,2" 80))))
