(ns day-1
  (:require [clojure.java.io :as io]
            [cuerdas.core :as str]))


(def input
  (->> (str/split (slurp "resources/day_1.txt") "\n")
       (mapv read-string)))

(defn depth-increase-count
  [input]
  (->> input
       (partition 2 1)
       (map #(apply < %))
       (remove false?)
       (count)))

(defn sliding-window-increase-count
  [input]
  (->> input
       (partition 3 1)
       (map (partial apply +))
       (depth-increase-count)))


(comment
  (depth-increase-count input)
  (sliding-window-increase-count input))
