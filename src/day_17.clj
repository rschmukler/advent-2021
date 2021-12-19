(ns day-17
  (:require [aocd.core :as aoc]
            [cuerdas.core :as str]))

(def input
  (aoc/input 2021 17))

(defn input->bounds
  "Parse the provided input into bounds"
  [text]
  (let [[min-x max-x min-y max-y] (map read-string (re-seq #"-?\d+" text))]
    {:min-x min-x
     :max-x max-x
     :min-y min-y
     :max-y max-y}))


(defn step-x-velocity
  [vel-x]
  (cond
    (zero? vel-x) 0
    (pos? vel-x)  (dec vel-x)
    :else         (inc vel-x)))

(defn run-step
  "Run a step of the probe's trajectory"
  [{:keys [x y vel-x vel-y]}]
  {:x     (+ x vel-x)
   :y     (+ y vel-y)
   :vel-x (step-x-velocity vel-x)
   :vel-y (dec vel-y)})


(defn theoretically-possible?
  "Return whether the provided probe could theoretically hit the provided
  bounds"
  ([bounds probe]
   (theoretically-possible? bounds probe nil))
  ([bounds probe only]
   (let [{:keys [min-x min-y max-x]} bounds
         {:keys [x y vel-x]}         probe
         x-possible?
         (or (and (< x max-x) (pos? vel-x))
             (and (< min-x x) (neg? vel-x))
             (and (<= min-x x max-x) (zero? vel-x)))
         y-possible?                 (>= y min-y)]
     (case only
       :x  x-possible?
       :y  y-possible?
       nil (and x-possible? y-possible?)))))

(defn in-bounds?
  "Return whether the provided probe is in the specified bounds"
  [bounds probe]
  (let [{:keys [min-x min-y max-x max-y]} bounds
        {:keys [x y]}                     probe]
    (and (<= min-x x max-x)
         (<= min-y y max-y))))


(defn run-trajectory
  "Run a trajectory for the given probe and bounds and return a sequence of all
  steps"
  [probe bounds]
  (->> (iterate run-step probe)
       (take-while (some-fn (partial theoretically-possible? bounds)
                            (partial in-bounds? bounds)))
       seq))


(defn trajectory->max-y
  "Return the maximum trajectory"
  [trajectory]
  (->> trajectory
       (map :y)
       (apply max)))


(defn run-x-trajectory
  [x-vel]
  (loop [x      0
         x-vel  x-vel
         result []]
    (if (zero? x-vel)
      result
      (recur (+ x x-vel) (step-x-velocity x-vel) (conj result x)))))

(defn find-potential-xs
  [bounds]
  (let [{:keys [min-x max-x]} bounds]
    (->> (range (inc max-x))
         (remove (fn [vel-x]
                   (not-any? #(<= min-x % max-x) (run-x-trajectory vel-x)))))))

(defn run-y-trajectory
  [{:keys [min-y]} y-vel]
  (loop [y      0
         y-vel  y-vel
         result []]
    (if (< y min-y)
      result
      (recur (+ y y-vel) (dec y-vel) (conj result y)))))

(defn find-potential-ys
  [bounds]
  (let [{:keys [min-y max-y]} bounds]
    (->> (range -1000 1000)
         (filter (fn [vel-y] (seq (filter #(<= min-y % max-y) (run-y-trajectory bounds vel-y))))))))


(defn solve-part-one
  [text]
  (let [bounds (input->bounds text)]
    (->> (for [vel-x (find-potential-xs bounds)
               vel-y (find-potential-ys bounds)
               :let  [trajectory (run-trajectory
                                   {:x     0     :y     0
                                    :vel-x vel-x :vel-y vel-y} bounds)]
               :when (and trajectory
                          (in-bounds? bounds (last trajectory)))]
           [[vel-x vel-y] (trajectory->max-y trajectory)])
         (sort-by second)
         (last)
         (second))))

(defn solve-part-two
  [text]
  (let [bounds (input->bounds text)]
    (->> (for [vel-x (find-potential-xs bounds)
               vel-y (find-potential-ys bounds)
               :let  [trajectory (run-trajectory
                                   {:x     0     :y     0
                                    :vel-x vel-x :vel-y vel-y} bounds)]
               :when (and trajectory
                          (in-bounds? bounds (last trajectory)))]
           [vel-x vel-y])
         count)))
(comment
  (solve-part-one (str/trim input))
  (solve-part-two (str/trim input)))
