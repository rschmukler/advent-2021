(ns day-7
  (:require [aocd.core :as aoc]))

(def input
  (aoc/input 2021 7))

(defn input->crab-positions
  [input]
  (->> (re-seq #"\d+" input)
       (map read-string)
       (frequencies)))


(defn compute-fuel-for-meeting
  [crab-positions fuel-cost-f position]
  (reduce
    (fn [fuel [crab-pos crab-count]]
      (let [fuel-per-crab (fuel-cost-f position crab-pos)]
        (+ fuel (* crab-count fuel-per-crab))))
    0
    crab-positions))


(defn find-optimal-position
  [fuel-cost-f crab-positions]
  (let [positions (keys crab-positions)
        min-pos   (apply min positions)
        max-pos   (apply max positions)]
    (->> (range min-pos (inc max-pos))
         (map (juxt identity (partial compute-fuel-for-meeting crab-positions fuel-cost-f)))
         (partition 2 1)
         (take-while (fn [[[_ fuel-a] [_ fuel-b]]]
                       (> fuel-a fuel-b)))
         (last)
         (last))))

(defn fuel-cost-f-part-one
  [pos-a pos-b]
  (int (Math/abs (- pos-a pos-b))))

(def fuel-cost-f-part-two-for-distance
  (memoize (fn [dist]
             (apply + (range (inc dist))))))

(defn fuel-cost-f-part-two
  [pos-a pos-b]
  (fuel-cost-f-part-two-for-distance (Math/abs (- pos-a pos-b))))

(comment
  (->> input
       (input->crab-positions)
       (find-optimal-position fuel-cost-f-part-two)))
