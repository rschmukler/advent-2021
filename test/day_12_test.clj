(ns day-12-test
  (:require [day-12 :as sut]
            [clojure.test :refer [deftest testing is]]))

(def small-input
  "start-A\nstart-b\nA-c\nA-b\nb-d\nA-end\nb-end")

(def medium-input
  "dc-end\nHN-start\nstart-kj\ndc-start\ndc-HN\nLN-dc\nHN-end\nkj-sa\nkj-HN\nkj-dc")

(deftest input->cave-map-test
  (is (= {:bigs   #{"A"}
          :smalls #{"start" "end" "b" "c" "d"}
          :links  {"A"     #{"c" "b" "start" "end"}
                   "start" #{"b" "A"}
                   "end"   #{"b" "A"}
                   "c"     #{"A"}
                   "b"     #{"A" "d" "start" "end"}
                   "d"     #{"b"}}}
         (sut/input->cave-map small-input))))


(deftest find-valid-paths-test
  (let [small-map (sut/input->cave-map small-input)
        med-map   (sut/input->cave-map medium-input)]
    (is (= 10
           (count (sut/find-valid-paths small-map sut/part-one-logic))))
    (is (= 19
           (count (sut/find-valid-paths med-map sut/part-one-logic))))

    (is (= 36 (count (sut/find-valid-paths small-map sut/part-two-logic))))
    (is (= 103 (count (sut/find-valid-paths med-map sut/part-two-logic))))))
