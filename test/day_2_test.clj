(ns day-2-test
  (:require [day-2 :as sut]
            [clojure.test :refer [deftest testing is]]))

(def example
  [[:forward 5]
   [:down 5]
   [:forward 8]
   [:up 3]
   [:down 8]
   [:forward 2]])

(deftest get-position-test
  (is (= {:depth 10 :horizontal 15}
         (sut/get-position example))))

(deftest get-position-with-aim-test
  (is (= {:depth 60 :horizontal 15}
         (sut/get-position-with-aim example))))
