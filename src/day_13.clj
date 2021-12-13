(ns day-13
  (:require [aocd.core :as aoc]
            [cuerdas.core :as str]))

(def input
  (aoc/input 2021 13))

(defn text->fold+coordinates
  "Coerce the provided input into fold and coordinates"
  [text]
  (let [lines                       (mapv str/trim (str/lines text))
        ->ints                      #(mapv read-string (re-seq #"\d+" %))
        [coor-text [_ & fold-text]] (split-with (complement #{""}) lines)]
    {:coordinates
     (set (map ->ints coor-text))
     :folds
     (vec (for [fold fold-text
                :let [x? (re-seq #"x" fold)
                      amt (-> fold ->ints first)]]
            [(if x? :x :y) amt]))}))

(defn fold
  "Perform a fold on the provided input coordinates and return
  the new coordinate set"
  [coordinates [dir amt]]
  (let [up? (= :y dir)]
    (->> (for [[x y] coordinates]
           (cond
             (and up? (> y amt))       [x (- amt (- y amt))]
             (and (not up?) (> x amt)) [(- amt (- x amt)) y]
             :else                     [x y]))
         (into #{}))))


(defn solve-part-one
  []
  (let [{:keys [coordinates folds]} (text->fold+coordinates input)]
    (->> (take 1 folds)
         (reduce fold coordinates)
         (count))))

(defn render-coordinates
  "Print the coordinates to the screen"
  [coordinates]
  (let [max-x (->> coordinates (map first) (apply max))
        max-y (->> coordinates (map second) (apply max))]
    (doseq [y (range 0 (inc max-y))]
      (println (apply str (for [x (range 0 (inc max-x))]
                            (if (contains? coordinates [x y])
                              "X"
                              " ")))))))

(defn solve-part-two
  []
  (let [{:keys [coordinates folds]} (text->fold+coordinates input)]
    (->> folds
         (reduce fold coordinates)
         render-coordinates)))
(comment
  (solve-part-one)
  (solve-part-two))
