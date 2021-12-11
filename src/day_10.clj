(ns day-10
  (:require [aocd.core :as aoc]
            [cuerdas.core :as str]))


(def open->close
  "Map from opening character to the corresponding character"
  {\( \)
   \[ \]
   \{ \}
   \< \>})

(def opening-chars
  "Set of all opening characters"
  (set (keys open->close)))

(def closing-chars
  "Set of all opening characters"
  (set (vals open->close)))

(defn validate-string
  "Return the first illegal character in the provided `input`.

  Return `nil` if there are no illegal characters"
  [input]
  (loop [stack       []
         [s & input] input]
    (cond
      (nil? s)          {:autocomplete (when (seq stack)
                                         (apply str (map open->close (reverse stack))))}
      (opening-chars s) (recur (conj stack s) input)
      (closing-chars s) (let [expected-char (-> (peek stack)
                                                (open->close))]
                          (if (= expected-char s)
                            (recur (pop stack) input)
                            {:invalid s})))))

(defn solve-part-one
  [lines]
  (let [close->score
        {\) 3
         \] 57
         \} 1197
         \> 25137}]
    (->> lines
         (keep (comp close->score :invalid validate-string))
         (apply +))))


(defn autocomplete->score
  "Return the score for the provided autocomplete"
  [input]
  (let [close->score
        {\) 1
         \] 2
         \} 3
         \> 4}]
    (reduce
      (fn [score char]
        (+ (* 5 score) (close->score char)))
      0
      input)))

(defn solve-part-two
  [lines]
  (let [autocompletes (keep (comp :autocomplete validate-string) lines)
        mid-point     (inc (int (/ (count autocompletes) 2)))]
    (->> autocompletes
         (keep autocomplete->score)
         (sort)
         (take mid-point)
         last)))

(def input
  (str/lines (aoc/input 2021 10)))

(comment
  (solve-part-one input)
  (solve-part-two input))
