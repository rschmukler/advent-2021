(ns day-1-test
  (:require [day-1 :as sut]
            [clojure.test :refer [deftest testing is]]))

(def sample
  [199
   200
   208
   210
   200
   207
   240
   269
   260
   263])

(deftest depth-increase-count-test
  (is (= 7 (sut/depth-increase-count sample))))

(deftest sliding-window-increase-count-test
  (is (= 5 (sut/sliding-window-increase-count sample))))
