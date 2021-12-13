(ns day-13-test
  (:require [day-13 :as sut]
            [clojure.test :refer [deftest testing is]]))

(def example-text
  "6,10
   0,14
   9,10
   0,3
   10,4
   4,11
   6,0
   6,12
   4,1
   0,13
   10,12
   3,4
   3,0
   8,4
   1,10
   2,14
   8,10
   9,0

   fold along y=7
   fold along x=5")

(def example-fold
  {:coordinates
   #{[6 10]
     [0 14]
     [9 10]
     [0 3]
     [10 4]
     [4 11]
     [6 0]
     [6 12]
     [4 1]
     [0 13]
     [10 12]
     [3 4]
     [3 0]
     [8 4]
     [1 10]
     [2 14]
     [8 10]
     [9 0]}
   :folds
   [[:y 7]
    [:x 5]]})

(deftest text->fold+coordinates-test
  (is (= example-fold
         (sut/text->fold+coordinates example-text))))

(deftest fold-test
  (is (= 17
         (->> example-fold
              :folds
              (take 1)
              (reduce sut/fold (:coordinates example-fold))
              (count)))))
