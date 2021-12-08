(ns day-7-test
  (:require [day-7 :as sut]
            [clojure.test :refer [deftest testing is]]))


(deftest find-optimal-position-test
  (let [positions (sut/input->crab-positions "16,1,2,0,4,2,7,1,2,14")]
    (is (= [2 37]
           (sut/find-optimal-position sut/fuel-cost-f-part-one positions)))
    (is (= [5 168]
           (sut/find-optimal-position sut/fuel-cost-f-part-two positions)))))
