(ns day-6
  (:require [aocd.core :as aoc]))

(defn initial-state
  [input]
  (->> (re-seq #"\d" input)
       (map read-string)
       (frequencies)))

(defn run-day
  [m]
  (let [reproducing-fish (get m 0 0)]
    (-> (into {} (for [[k v] m] [(dec k) v]))
        (dissoc -1)
        (update 6 (fnil + 0) reproducing-fish)
        (update 8 (fnil + 0) reproducing-fish))))

(defn run-simulation
  "Run the simulation for the provided input string and return the number of fish"
  [input n]
  (->> (initial-state input)
       (iterate run-day)
       (take (inc n))
       (last)
       (vals)
       (apply +)))

(def input
  (aoc/input 2021 6))

(comment
  (run-simulation input 256))
