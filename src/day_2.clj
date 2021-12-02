(ns day-2
  (:require [aocd.core :as aoc]
            [clojure.core.match :refer [match]]
            [cuerdas.core :as str]))

(def input
  (-> (aoc/input 2021 2)
      (str/split "\n")
      (->>
        (mapv #(let [[dir num] (str/split % " ")]
                 [(keyword dir) (read-string num)])))))

(defn get-position
  [input]
  (loop [[i & input] input
         pos         {:depth 0 :horizontal 0}]
    (if-not i
      pos
      (recur
        input
        (match i
          [:forward x] (update pos :horizontal + x)
          [:down x]    (update pos :depth + x)
          [:up x]      (update pos :depth - x))))))

(defn get-position-with-aim
  [input]
  (loop [[i & input] input
         pos         {:depth 0 :horizontal 0 :aim 0}]
    (if-not i
      (dissoc pos :aim)
      (recur
        input
        (match i
          [:down x]    (update pos :aim + x)
          [:up x]      (update pos :aim - x)
          [:forward x] (-> pos
                           (update :horizontal + x)
                           (update :depth + (* (:aim pos) x))))))))

(def solution-part-one
  (->> input
       (get-position)
       (vals)
       (apply *)))

(def solution-part-two
  (->> input
       (get-position-with-aim)
       (vals)
       (apply *)))
