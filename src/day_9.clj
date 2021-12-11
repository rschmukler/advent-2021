(ns day-9
  (:require [aocd.core :as aoc]
            [cuerdas.core :as str]
            [clojure.set :as set]))

(defn get-neighboring-coordinates
  "Return the neighboring coordinates for the provided input coordinate"
  [[x y]]
  (->> [[(inc x) y]
        [(dec x) y]
        [x (inc y)]
        [x (dec y)]]))


(defn in-bounds?
  "Return whether the provided coordinate is in bounds given the provided `input` grid"
  [input [x y]]
  (and (< -1 y (count input))
       (< -1 x (count (first input)))))

(defn get-height
  "Return the value at the given coordinate (or nil if its out of bounds)"
  [input [x y :as coord]]
  (when (in-bounds? input coord)
    (-> input
        (nth y)
        (nth x))))


(defn is-low-point?
  "Return whether the provided coordinate is a low point"
  [input coordinate]
  (let [height (get-height input coordinate)]
    (->> coordinate
         (get-neighboring-coordinates)
         (keep #(get-height input %))
         (every? #(< height %)))))

(defn find-low-points
  "Return all low points in the provided input"
  [input]
  (->> (for [y (range (count input))
             x (range (count (first input)))]
         [x y])
       (filter (partial is-low-point? input))))

(defn solve-part-one
  "Solve part one!"
  [input]
  (->> input
       (find-low-points)
       (map (comp inc (partial get-height input)))
       (apply +)))

(def input
  "Our advent of code puzzle input"
  (->> (aoc/input 2021 9)
       (str/lines)
       (mapv #(mapv read-string (re-seq #"\d" %)))))


(defn find-neighboring-basin-coordinates
  "Return a set of all neighboring basin coordinates to `coord` for the provided `input`. "
  [input coord]
  (->> (get-neighboring-coordinates coord)
       (filter #(in-bounds? input %))
       (filter #(< (get-height input %) 9))
       set))

(defn find-basin-at-low-point
  "Return a set of coordinates as part of the basin using the provided `low-point` coordinate."
  [input low-point]
  (loop [to-check        [low-point]
         already-checked #{}
         basin-coords    #{}]
    (if-some [coord (first to-check)]
      (let [basin (find-neighboring-basin-coordinates input coord)]
        (recur
          (concat (rest to-check) (remove already-checked basin))
          (conj already-checked coord)
          (set/union basin-coords basin #{coord})))
      basin-coords)))

(defn solve-part-two
  "Solve part two!"
  [input]
  (->> (find-low-points input)
       (map #(count (find-basin-at-low-point input %)))
       (sort)
       (reverse)
       (take 3)
       (apply *)))

(comment
  (solve-part-one input)
  (solve-part-two input))
