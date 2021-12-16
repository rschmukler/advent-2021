(ns day-15
  (:require [cuerdas.core :as str]
            [aocd.core :as aoc]))

(defn text->cave-map
  [text]
  (into {}
        (for [[y line] (map-indexed vector (str/lines text))
              [x risk] (map-indexed vector (map read-string (re-seq #"\d" line)))]
          [[x y] risk])))

(defn get-neighbors
  "Return all neighboring coordinates"
  [[x y]]
  (->> [[(inc x) y]
        [(dec x) y]
        [x (inc y)]
        [x (dec y)]]))

(defn dijkstras-shortest-path
  "Return the shortest path using Dijkstra's algorithm"
  [cave-map start end]
  (loop [visited      {}
         distances    {start 0}
         current-node start]
    (if-some [answer (visited end)]
      answer
      (let [neighbors (sequence
                        (comp
                          (filter cave-map)
                          (remove visited))
                        (get-neighbors current-node))
            score     (get distances current-node)
            distances
            (reduce
              (fn [distances coord]
                (let [new-score (+ score (cave-map coord))]
                  (update distances coord (fnil min new-score) new-score)))
              distances
              neighbors)
            visited   (assoc visited current-node score)
            distances (dissoc distances current-node)]
        (recur
          visited
          distances
          (->> distances
               (remove (comp visited key))
               (sort-by val)
               (ffirst)))))))

(def input
  (aoc/input 2021 15))

(defn solve-part-one
  [text]
  (let [cave-map (text->cave-map text)]
    (dijkstras-shortest-path cave-map [0 0] (->> cave-map keys sort last))))

(defn enhance
  "Return an enhanced version of `cave-map`"
  [cave-map n]
  (let [[base-x base-y] (->> cave-map keys sort last (map inc))]
    (into {}
          (for [x    (range (* n base-x))
                y    (range (* n base-y))
                :let [src-x (rem x base-x)
                      src-y (rem y base-y)
                      x-off (quot x base-x)
                      y-off (quot y base-y)
                      src   (cave-map [src-x src-y])
                      risk  (mod (+ src x-off y-off) 9)]]
            [[x y] (cond
                     (and (zero? x-off) (zero? y-off)) src
                     (zero? risk)                      9
                     :else                             risk)]))))

(defn solve-part-two
  [text]
  (let [cave-map (-> text
                     (text->cave-map)
                     (enhance 5))
        end      (->> cave-map
                      (keys)
                      (sort)
                      last)]
    (dijkstras-shortest-path cave-map [0 0] end)))

(comment
  (time
    (solve-part-one input))
  (defonce solution
    (future (solve-part-two input))))
