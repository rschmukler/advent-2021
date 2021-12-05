(ns day-4
  (:require [cuerdas.core :as str]
            [aocd.core :as aoc]))

(def input
  (aoc/input 2021 4))

(defn input->game
  [input]
  (let [[nums _ & lines] (str/split input "\n")
        numbers          (->> (str/split nums ",")
                              (map read-string))]
    {:numbers numbers
     :boards  (->> lines
                   (partition-by #{""})
                   (remove #{'("")})
                   (mapv (fn [card-input]
                           (mapv #(->> (str/split % " ")
                                       (remove #{""})
                                       (mapv read-string))
                                 card-input))))}))


(defn get-col
  "Return a sequence of numbers from the corresponding column index from the provided board"
  [board n]
  (map #(nth % n) board))

(defn get-row
  "Return a sequence of numbers from the corresponding row index from the provided board"
  [board n]
  (nth board n))

(defn win?
  "Return whether the provided `board` has a win given the provided `seen-nums` set"
  [board seen-nums]
  (->> (for [n    (range (count board))
             :let [row (get-row board n)
                   col (get-col board n)]]
         (or (= row (filter seen-nums row))
             (= col (filter seen-nums col))))
       (filter true?)
       (seq)
       (some?)))

(defn play-game
  "Play the provided game and return the win summary. If there is no winner, return `nil`"
  [game]
  (let [{:keys [boards numbers]} game]
    (loop [[n & numbers] numbers
           boards        boards
           result        []
           seen-nums     #{}]
      (if-not n
        result
        (let [seen-nums (conj seen-nums n)
              [winners boards]
              (->> boards
                   (reduce (fn [acc board]
                             (update
                               acc
                               (if (win? board seen-nums)
                                 0
                                 1)
                               conj
                               board))
                           [[] []]))]
          (recur
            numbers
            boards
            (concat
              result
              (for [winner winners]
                {:winner    winner
                 :seen-nums seen-nums
                 :last-num  n}))
            seen-nums))))))


(defn compute-score
  "Compute the score for the provided `win-state`"
  {:arglists '([win-state])}
  [{:keys [winner seen-nums last-num]}]
  (let [unmarked-score (->> winner
                            (flatten)
                            (remove seen-nums)
                            (apply +))]
    (* unmarked-score last-num)))

(def part-one-answer
  (-> input
      (input->game)
      (play-game)
      (first)
      (compute-score)))

(def part-two-answer
  (-> input
      input->game
      (play-game)
      (last)
      (compute-score)))
