(ns day-11
  (:require [aocd.core :as aoc]
            [cuerdas.core :as str]))

(def input
  (->> (aoc/input 2021 11)
       (str/lines)
       (mapv #(mapv read-string (re-seq #"\d" %)))))

(defn neighboring-coords
  "Return neighboring coordinate pairs that are in bounds"
  [input [y x]]
  (let [max-y (count input)
        max-x (count (first input))]
    (for [y-prime (range (max 0 (dec y)) (min (+ y 2) max-y))
          x-prime (range (max 0 (dec x)) (min (+ x 2) max-x))
          :when   (not= [y x] [y-prime x-prime])]
      [y-prime x-prime])))

(defn run-step
  "Run a step over the provided input grid and return the new value"
  [octos]
  (let [coords (for [y (range (count octos))
                     x (range (count (first octos)))]
                 [y x])]
    (loop [[coord & to-check] coords
           flashed            #{}
           octos              (reduce #(update-in %1 %2 inc) octos coords)]
      (cond
        (nil? coord)    (reduce #(assoc-in %1 %2 0) octos flashed)
        (flashed coord) (recur to-check flashed octos)
        :else
        (let [energy (get-in octos coord)
              flash? (> energy 9)]
          (if-not flash?
            (recur to-check flashed octos)
            (let [neighbors (neighboring-coords octos coord)]
              (recur
                (concat to-check neighbors)
                (conj flashed coord)
                (reduce #(update-in %1 %2 inc) octos neighbors)))))))))

(defn flash-count
  "Return the number of octopus that flashed in the provided grid"
  [octos]
  (->> octos
       (flatten)
       (filter zero?)
       count))

(defn flashes-after-n-steps
  [octos n]
  (->> octos
       (iterate run-step)
       (map flash-count)
       (take (inc n))
       (apply +)))

(defn find-step-when-all-flash
  [octos]
  (let [total-count (->> octos flatten count)]
    (loop [n     0
           octos octos]
      (if (= (flash-count octos) total-count)
        n
        (recur (inc n) (run-step octos))))))

(comment
  (flashes-after-n-steps input 100)
  (find-step-when-all-flash input))
