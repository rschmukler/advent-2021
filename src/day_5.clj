(ns day-5
  (:require [cuerdas.core :as str]
            [aocd.core :as aoc]))

(defn input->line
  "Coerce the provided input line into a line coordinate map"
  [input]
  (->> (re-seq #"\d+" input)
       (map read-string)
       (zipmap [:x1 :y1 :x2 :y2])))

(defn horizontal?
  "Return whether the provided line-map is horizontal"
  [{:keys [x1 x2]}]
  (= x1 x2))

(defn vertical?
  "Return whether the provided line-map is vertical"
  [{:keys [y1 y2]}]
  (= y1 y2))

(defn line->coordinates
  "Return a sequence of all [x, y] coordinates covered by the provided line"
  [line]
  (let [{:keys [x1 x2 y1 y2]} line
        xs                    (cond
                                (horizontal? line) (repeat x1)
                                (< x1 x2)          (range x1 (inc x2))
                                :else              (range x1 (dec x2) -1))
        ys                    (cond
                                (vertical? line) (repeat y1)
                                (< y1 y2)        (range y1 (inc y2))
                                :else            (range y1 (dec y2) -1))]
    (map vector xs ys)))

(def input
  (->> (aoc/input 2021 5)
       (str/lines)
       (mapv input->line)))

(defn solve-part-one
  "Return the count of vertical and horizontal line points that overlap"
  [lines]
  (->> lines
       (filter (some-fn horizontal? vertical?))
       (mapcat line->coordinates)
       (frequencies)
       (remove #(> 2 (val %)))
       (count)))

(defn solve-part-two
  "Return the count of points that overlap"
  [lines]
  (->> lines
       (mapcat line->coordinates)
       (frequencies)
       (remove #(> 2 (val %)))
       (count)))

(comment
  (solve-part-one input)
  (solve-part-two input))
