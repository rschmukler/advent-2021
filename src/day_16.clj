(ns day-16
  (:require [cuerdas.core :as str]
            [aocd.core :as aoc]))


(def input
  (->> (aoc/input 2021 16)
       (str/lines)
       first))

(defn hex->binary
  "Convert the provided hex string into a 8 bit aligned binary representation"
  [hex-str]
  (let [bits       8
        binary     (.toString (BigInteger. hex-str 16) 2)
        len        (quot (count binary) bits)
        needs-pad? (pos? (mod (count binary) bits))]
    (if-not needs-pad?
      binary
      (str/pad binary {:length (* bits (inc len)) :padding "0"}))))

(defn binary->int
  "Coerce the provided binary slice (at start and end) into an integer and return it"
  [binary start end]
  (-> binary (subs start end) (Long/parseLong 2)))

(defn parse-literal
  "Parse a literal and return it, as well as the remaining binary"
  [binary]
  (loop [acc    ""
         binary binary]
    (let [recur? (= \1 (first binary))
          acc    (str acc (subs binary 1 5))
          binary (subs binary 5)]
      (if recur?
        (recur acc binary)
        [(Long/parseLong acc 2) binary]))))

(defn binary-metadata
  "Compute the binary :length and :binary attributes of a packet by
  comparing the input binary and the remaining binary."
  [binary binary-rest]
  (let [length (- (count binary) (count binary-rest))]
    {:length length
     :binary (subs binary 0 length)}))

(defn parse-packets
  "Take the provided binary input and return a lazy sequence of packets."
  [binary]
  (if (re-find #"^0*$" binary)
    nil
    (let [type-id        (binary->int binary 3 6)
          is-literal?    (= 4 type-id)
          binary-rest    (subs binary 6)
          base           {:version (binary->int binary 0 3)
                          :type-id type-id}
          length-type-id (binary->int binary-rest 0 1)]
      (cond
        is-literal?
        (let [[value binary-rest] (parse-literal binary-rest)]
          (lazy-seq
            (cons
              (merge
                base
                (binary-metadata binary binary-rest)
                {:literal value})
              (parse-packets binary-rest))))
        (= 0 length-type-id)
        (let [subpacket-length (binary->int binary-rest 1 16)
              subpacket-stop   (+ 16 subpacket-length)
              subpacket-binary (subs binary-rest 16 subpacket-stop)
              binary-rest      (subs binary-rest subpacket-stop)]
          (lazy-seq
            (cons
              (merge base
                     (binary-metadata binary binary-rest)
                     {:length-type-id   length-type-id
                      :subpacket-length subpacket-length
                      :subpackets       (parse-packets subpacket-binary)})
              (parse-packets binary-rest))))
        (= 1 length-type-id)
        (let [subpacket-count (binary->int binary-rest 1 12)
              binary-rest     (subs binary-rest 12)
              subpackets      (take subpacket-count (parse-packets binary-rest))
              subpacket-bits  (->> subpackets
                                   (map :length)
                                   (apply +))
              binary-rest     (subs binary-rest subpacket-bits)]
          (lazy-seq
            (cons
              (merge base
                     (binary-metadata binary binary-rest)
                     {:length-type-id  length-type-id
                      :subpacket-count subpacket-count
                      :subpackets      subpackets})
              (parse-packets binary-rest))))))))

(defn solve-part-one
  "Solve the puzzle for part one."
  [hex-str]
  (let [sum-version (fn sum-version [{:keys [version subpackets]}]
                      (if (seq subpackets)
                        (+ version (apply + (map sum-version subpackets)))
                        version))]
    (->> hex-str
         (hex->binary)
         (parse-packets)
         (map sum-version)
         (apply +))))

(def type->operation
  "Cheeky type-id to function. FP you beautiful thing you."
  (let [bool->int #(if % 1 0)]
    {0 +
     1 *
     2 min
     3 max
     5 (comp bool->int >)
     6 (comp bool->int <)
     7 (comp bool->int =)}))

(defn solve-part-two
  "Sole the puzzle for part two."
  [hex-str]
  (let [evaluate-packet
        (fn evaluate-packet
          [{:keys [type-id subpackets literal]}]
          (if (= 4 type-id)
            literal
            (->> subpackets
                 (map evaluate-packet)
                 (apply (type->operation type-id)))))]
    (->> hex-str
         (hex->binary)
         (parse-packets)
         (first)
         (evaluate-packet))))


(comment
  (solve-part-one input)
  (solve-part-two input))
