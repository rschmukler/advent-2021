(ns day-3-test
  (:require  [clojure.test :refer [deftest testing is]]
             [day-3 :as sut]))


(def example-input
  [[0 0 1 0 0]
   [1 1 1 1 0]
   [1 0 1 1 0]
   [1 0 1 1 1]
   [1 0 1 0 1]
   [0 1 1 1 1]
   [0 0 1 1 1]
   [1 1 1 0 0]
   [1 0 0 0 0]
   [1 1 0 0 1]
   [0 0 0 1 0]
   [0 1 0 1 0]])

(deftest generate-gamma-test
  (is (= [1 0 1 1 0]
         (sut/generate-gamma example-input))))

(deftest get-co2-rating-test
  (is (= [0 1 0 1 0]
         (sut/get-co2-rating example-input))))

(deftest get-oxygen-rating-test
  (is (= [1 0 1 1 1]
         (sut/get-oxygen-rating example-input))))
