(ns day-15-test
  (:require [day-15 :as sut]
            [clojure.test :refer [deftest testing is]]))


(def example-text
  "1163751742
   1381373672
   2136511328
   3694931569
   7463417111
   1319128137
   1359912421
   3125421639
   1293138521
   2311944581")

(def example-input
  (sut/text->cave-map example-text))

(deftest dijkstras-shortest-path-test
  (is (= 40
         (sut/dijkstras-shortest-path example-input [0 0] [9 9]))))

(deftest solve-part-two-test
  (is (= 315 (sut/solve-part-two example-text))))
