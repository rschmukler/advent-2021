(ns day-12
  (:require [cuerdas.core :as str]
            [aocd.core :as aoc]))


(defn input->cave-map
  [input]
  (reduce
    (fn [acc line]
      (let [is-big? #(some? (re-find #"[A-Z]" %))
            set-add (fnil conj #{})
            [a b]   (str/split line "-")]
        (-> acc
            (update (if (is-big? a) :bigs :smalls)
                    set-add a)
            (update (if (is-big? b) :bigs :smalls)
                    set-add b)
            (update-in [:links a] set-add b)
            (update-in [:links b] set-add a))))
    {}
    (str/lines input)))


(defn part-one-logic
  "Abstract function for the traversal logic in part one"
  [cave-map {:keys [path] :as acc}]
  (let [loc             (peek path)
        visited-smalls  (set (keep (:smalls cave-map) path))
        neighbors       (get-in cave-map [:links loc])
        legal-neighbors (remove visited-smalls neighbors)
        traverse        #(update acc :path conj %)]
    [legal-neighbors traverse]))


(defn part-two-logic
  "Abstract function for the traversal logic in part two"
  [cave-map {:keys [path used-double] :as acc}]
  (let [loc             (peek path)
        visited-smalls  (set (keep (:smalls cave-map) path))
        neighbors       (get-in cave-map [:links loc])
        legal-neighbors (if used-double
                          (remove visited-smalls neighbors)
                          (remove #{"start"} neighbors))
        traverse        #(-> acc
                             (update :path conj %)
                             (assoc :used-double (or used-double (contains? visited-smalls %))))]
    [legal-neighbors traverse]))


(defn find-valid-paths
  "Return the valid paths for the cave map using the provided logic function `f`.

  The logic function will be called with the `cave-map` and an `accumulator` and is expected to return a tuple
  of the valid neighbors, as well as a `traverse` function which will be called with a given neighbor and should return a new state
  of the `accumulator` assuming that the selected `neighbor` is chosen."
  ([cave-map f]
   (find-valid-paths cave-map f {:path ["start"]}))
  ([cave-map f acc]
   (let [path                       (:path acc)
         loc                        (peek path)
         [legal-neighbors traverse] (f cave-map acc)]
     (cond
       (= "end" loc)            [path]
       (empty? legal-neighbors) nil
       :else
       (->> (for [n legal-neighbors]
              (find-valid-paths cave-map f (traverse n)))
            (apply concat)
            (remove nil?))))))

(def input
  (aoc/input 2021 12))

(comment
  (time
    (-> input
        (input->cave-map)
        (find-valid-paths part-two-logic)
        count)))
