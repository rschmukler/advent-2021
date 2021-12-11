(ns day-11-test
  (:require [day-11 :as sut]
            [clojure.test :refer [deftest testing is]]
            [cuerdas.core :as str]))


(def example-input
  (->> "5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526"
       (str/lines)
       (map str/trim)
       (mapv #(mapv read-string (re-seq #"\d" %)))))

(deftest neighboring-coords-test
  (is (= [[0 1]
          [1 0]
          [1 1]]
         (sut/neighboring-coords [[0 0 0]
                                  [1 1 1]
                                  [2 2 2]] [0 0]))))


(deftest run-step-test
  (is (= [[0 3]
          [2 2]]
         (sut/run-step [[9 1]
                        [0 0]])))
  (is (= [[3 4 5 4 3]
          [4 0 0 0 4]
          [5 0 0 0 5]
          [4 0 0 0 4]
          [3 4 5 4 3]]
         (sut/run-step
           [[1 1 1 1 1]
            [1 9 9 9 1]
            [1 9 1 9 1]
            [1 9 9 9 1]
            [1 1 1 1 1]]))))

(deftest flashes-after-n-steps-test
  (is (= 1656
         (sut/flashes-after-n-steps example-input 100))))

(deftest find-step-when-all-flash-test
  (is (= 195
         (sut/find-step-when-all-flash example-input))))
