(ns day-10-test
  (:require [day-10 :as sut]
            [clojure.test :refer [deftest testing is]]
            [cuerdas.core :as str]))

(def example-input
  (->> "[({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]"
       (str/lines)
       (map str/trim)))


(deftest validate-string-test
  (is (= {:invalid \}}
         (sut/validate-string "{([(<{}[<>[]}>{[]{[(<()>")))
  (is (= {:autocomplete "}}]])})]"}
         (sut/validate-string "[({(<(())[]>[[{[]{<()<>>"))))

(deftest solve-part-one-test
  (is (= 26397
         (sut/solve-part-one example-input))))

(deftest autocomplete->score-test
  (is (= 288957
        (sut/autocomplete->score "}}]])})]"))))

(deftest solve-part-two-test
  (is (= 288957
         (sut/solve-part-two example-input))))
