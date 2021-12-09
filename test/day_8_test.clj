(ns day-8-test
  (:require [day-8 :as sut]
            [clojure.test :refer [deftest testing is]]
            [cuerdas.core :as str]))

(def example-input
  (->> "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
   edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
   fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
   fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
   aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
   fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
   dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
   bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
   egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
   gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"))

(deftest line->signals+digits-test
  (is (= {:signals #{#{:a :c :e :d :g :f :b}
                     #{:c :d :f :b :e}
                     #{:g :c :d :f :a}
                     #{:f :b :c :a :d}
                     #{:d :a :b}
                     #{:c :e :f :a :b :d}
                     #{:e :a :f :b}
                     #{:e :g :c :b :d :f}
                     #{:c :a :g :e :d :b}
                     #{:a :b}}
          :digits  [#{:c :d :f :e :b}
                    #{:f :c :a :d :b}
                    #{:c :d :f :e :b}
                    #{:c :d :b :a :f}]}
         (sut/line->signals+digits
           "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"))))

(deftest get-unique-digits-in-outputs-test
  (is (= 26
         (sut/get-unique-digits-in-outputs example-input))))


(deftest solve-signals+digits-test
  (is (= 5353
         (-> "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"
             (sut/line->signals+digits)
             (sut/solve-signals+digits)))))

(deftest solve-part-two-test
  (is (= 61229
         (sut/solve-part-two example-input))))
