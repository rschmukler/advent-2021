(ns day-3
  (:require [cuerdas.core :as str]
            [aocd.core :as aoc]))

(def input
  (-> (aoc/input 2021 3)
      (str/split "\n")
      (->> (mapv #(mapv (fn [bit]
                          (case bit
                            \0 0
                            \1 1)) %)))))


(defn most-common-bit
  ([input n]
   (most-common-bit input n nil))
  ([input n default]
   (let [majority-count (/ (count input) 2)
         bits           (map #(nth % n) input)
         ones           (filter #(= 1 %) bits)]
     (cond
       (= majority-count (count ones)) default
       (> majority-count (count ones)) 0
       :else                           1))))

(defn generate-gamma
  [input]
  (->> input
       (first)
       (count)
       (range)
       (mapv (partial most-common-bit input))))

(defn binary-not
  [x]
  (case x
    0   1
    1   0
    nil nil))

(defn generate-epsilon
  [input]
  (mapv binary-not (generate-gamma input)))


(defn binary->dec
  [binary]
  (apply
    (comp int +)
    (for [[pow bit] (map-indexed vector (reverse binary))]
      (case bit
        1 (Math/pow 2 pow)
        0 0))))

(defn get-life-support-rating
  [input comparator]
  (loop [input input
         ix    0]
    (if (= 1 (count input))
      (first input)
      (let [tgt       (comparator input ix)
            new-input (filter #(= tgt (nth % ix)) input)]
        (recur new-input (inc ix))))))

(defn get-co2-rating
  [input]
  (get-life-support-rating
    input
    #(binary-not (most-common-bit %1 %2 1))))

(defn get-oxygen-rating
  [input]
  (get-life-support-rating
    input
    #(most-common-bit %1 %2 1)))

(def part-one-solution
  (let [g (generate-gamma input)
        e (generate-epsilon input)]
    (* (binary->dec g)
       (binary->dec e))))

(def part-two-solution
  (let [g (get-co2-rating input)
        e (get-oxygen-rating input)]
    (* (binary->dec g)
       (binary->dec e))))
