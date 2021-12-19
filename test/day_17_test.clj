(ns day-17-test
  (:require [day-17 :as sut]
            [clojure.test :refer [deftest testing is]]))


(deftest input->bounds-test
  (is (= {:min-x 20 :max-x 30 :min-y -10 :max-y -5}
         (sut/input->bounds "target area: x=20..30, y=-10..-5"))))

(deftest solve-part-one-test
  (is (= 45 (sut/solve-part-one "target area: x=20..30, y=-10..-5"))))

(deftest solve-part-two-test
  (is (= 112 (sut/solve-part-two  "target area: x=20..30, y=-10..-5"))))
