(ns day-14
  (:require [aocd.core :as aoc]
            [cuerdas.core :as str]))

(def input
  "Our advent of code input"
  (aoc/input 2021 14))

(defn parse-text
  "Parse the provided input `text` into a starting template and a rules map."
  [text]
  (let [[template _ & rules] (mapv str/trim (str/lines text))]
    {:template template
     :rules
     (into {} (map #(vec (re-seq #"\w+" %)) rules))}))

(defn apply-rules
  "Apply the given rules to the accumulator (which includes existing pair counts)"
  [rules {:keys [pairs counts]}]
  (reduce
    (fn [result [pair count]]
      (let [out-e (get rules pair)
            out-a (str (first pair) out-e)
            out-b (str out-e (second pair))]
        (-> result
            (update-in [:pairs out-a] (fnil + 0) count)
            (update-in [:pairs out-b] (fnil + 0) count)
            (update-in [:counts (str out-e)] (fnil + 0) count))))
    {:counts counts}
    pairs))


(defn template->accumulator
  "Initialize the accumulator for the provided template string"
  [template]
  {:counts
   (->> template
        (map str)
        (frequencies)
        (map (juxt (comp str key) val))
        (into {}))
   :pairs
   (->> template
        (partition 2 1)
        (map #(apply str %))
        (frequencies))})

(defn solve-puzzle
  "Solve the provided puzzle input after `n` steps."
  [text n]
  (let [{:keys [rules template]} (parse-text text)
        sorted-counts
        (->> (template->accumulator template)
             (iterate (partial apply-rules rules))
             (take (inc n))
             (last)
             (:counts)
             (sort-by val))
        least-common             (->> sorted-counts first val)
        most-common              (->> sorted-counts last val)]
    (- most-common least-common)))

(comment
  (solve-puzzle input 10)
  (solve-puzzle input 40))
