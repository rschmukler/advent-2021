(ns day-8 (:require [cuerdas.core :as str]
            [aocd.core :as aoc]
            [clojure.set :as set]))

(def digit->segments
  {0 #{:a :b :c :e :f :g}
   1 #{:c :f}
   2 #{:a :c :d :e :g}
   3 #{:a :c :d :f :g}
   4 #{:b :c :d :f}
   5 #{:a :b :d :f :g}
   6 #{:a :b :d :e :f :g}
   7 #{:a :c :f}
   8 #{:a :b :c :d :e :f :g}
   9 #{:a :b :c :d :f :g}})

(defn line->signals+digits
  [line]
  (let [[signals digits] (->> (str/split line "|")
                              (map str/trim))
        signals          (->> (str/split signals " ")
                              (map #(set (map (comp keyword str) %)))
                              set)
        digits           (->> (str/split digits " ")
                              (mapv #(set (mapv (comp keyword str) %))))]
    {:signals signals
     :digits  digits}))

(defn get-unique-digits-in-outputs
  [input]
  (let [has-unique-digit-count? (->> [1 4 7 8]
                                     (map (comp count digit->segments))
                                     (set))]
    (->> input
         (mapcat :digits)
         (filter (comp has-unique-digit-count? count))
         (count))))


(def input
  (aoc/input 2021 8))

(defn signals+digits->signals->digits
  [{:keys [signals]}]
  (let [initial-digits (into {} (for [digit [1 4 7 8]
                                      :let  [digit-count (count (digit->segments digit))
                                             match       (->> signals
                                                              (filter #(= digit-count (count %)))
                                                              first)]]
                                  [digit match]))
        one            (initial-digits 1)
        four           (initial-digits 4)
        seven          (initial-digits 7)
        eight          (initial-digits 8)
        nine           (->> signals
                            (filter #(and (set/subset? four %)
                                          (= 6 (count %))))
                            first)
        seg-e          (set/difference eight nine)
        six            (->> signals
                            (filter #(and (= 6 (count %))
                                          (not (set/subset? one %))))
                            first)
        zero           (->> signals
                            (filter #(= 6 (count %)))
                            (remove #{six nine})
                            first)
        five           (set/difference six seg-e)
        three          (->> signals
                            (filter #(and (= 5 (count %))
                                          (set/subset? one %)))
                            first)
        two            (->> signals
                            (remove #{zero one three four five six seven eight nine})
                            (first))]
    {zero  0
     one   1
     two   2
     three 3
     four  4
     five  5
     six   6
     seven 7
     eight 8
     nine  9}))

(defn solve-signals+digits
  [{:keys [digits] :as signals+digits}]
  (let [signals->digits (signals+digits->signals->digits signals+digits)]
    (->> digits
         (map (comp str signals->digits))
         (drop-while #(= "0" %))
         (apply str)
         (read-string))))

(defn solve-part-one
  [input]
  (->> input
       (str/lines)
       (map (comp line->signals+digits str/trim))
       (get-unique-digits-in-outputs)))

(defn solve-part-two
  [input]
  (->> input
       (str/lines)
       (map (comp solve-signals+digits line->signals+digits str/trim))
       (apply +)))

(comment
  (solve-part-one input)
  (solve-part-two input))
