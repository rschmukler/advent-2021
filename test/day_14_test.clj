(ns day-14-test
  (:require [day-14 :as sut]
            [clojure.test :refer [deftest testing is]]))

(def example-input-text
  "NNCB

   CH -> B
   HH -> N
   CB -> H
   NH -> C
   HB -> C
   HC -> B
   HN -> C
   NN -> C
   BH -> H
   NC -> B
   NB -> B
   BN -> B
   BB -> N
   BC -> B
   CC -> N
   CN -> C")

(def example-input
  {:template "NNCB"
   :rules
   {"CH" "B"
    "HH" "N"
    "CB" "H"
    "NH" "C"
    "HB" "C"
    "HC" "B"
    "HN" "C"
    "NN" "C"
    "BH" "H"
    "NC" "B"
    "NB" "B"
    "BN" "B"
    "BB" "N"
    "BC" "B"
    "CC" "N"
    "CN" "C"}})

(deftest parse-text-test
  (is (= example-input (sut/parse-text example-input-text))))

(sut/depair-pair-freqs
  {:offsets {"N" -1
             "C" -2
             "H" -1
             "B" -1},
   :pairs   {"NC" 1, "CN" 1, "NB" 1, "BC" 1, "CH" 1, "HB" 1}})

(deftest apply-rules-test
  (is (= {:pairs
          {"NC" 1
           "CN" 1
           "NB" 1
           "BC" 1
           "CH" 1
           "HB" 1}
          :counts
          {"N" 2
           "C" 2
           "H" 1
           "B" 2}}
         (sut/apply-rules (:rules example-input) (sut/template->accumulator "NNCB")))))

(deftest solve-puzzle-test
  (is (= 1588 (sut/solve-puzzle example-input-text 10)))
  (is (= 2188189693529 (sut/solve-puzzle example-input-text 40))))
