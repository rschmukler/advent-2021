(ns day-16-test
  (:require [day-16 :as sut]
            [clojure.test :refer [deftest testing is are]]))


(deftest parse-literal-test
  (is (= [2021 "000"]
         (sut/parse-literal "101111111000101000"))))


(deftest parse-packets-test
  (testing "returns nil for all zeros / empty"
    (is (= nil (sut/parse-packets "")))
    (is (= nil (sut/parse-packets "000"))))

  (testing "literal packet"
    (is (= [{:binary  "110100101111111000101"
             :length  21
             :version 6
             :type-id 4
             :literal 2021}]
           (sut/parse-packets (sut/hex->binary "D2FE28")))))

  (testing "operator packets"
    (testing "length type 0"
      (is (= '({:version          1
                :type-id          6
                :length           49
                :binary           "0011100000000000011011110100010100101001000100100"
                :length-type-id   0
                :subpacket-length 27
                :subpackets       ({:version 6
                                    :type-id 4
                                    :length  11
                                    :binary  "11010001010"
                                    :literal 10}
                                   {:version 2
                                    :type-id 4
                                    :length  16
                                    :binary  "0101001000100100"
                                    :literal 20})})
             (sut/parse-packets (sut/hex->binary "38006F45291200")))))
    (testing "length type 1"
      (is (= '({:version         7
                :type-id         3
                :length          51
                :binary          "111011100000000011010100000011001000001000110000011"
                :length-type-id  1
                :subpacket-count 3
                :subpackets      ({:version 2
                                   :type-id 4
                                   :length  11
                                   :binary  "01010000001"
                                   :literal 1}
                                  {:version 4
                                   :type-id 4
                                   :length  11
                                   :binary  "10010000010"
                                   :literal 2}
                                  {:version 1
                                   :type-id 4
                                   :length  11
                                   :binary  "00110000011"
                                   :literal 3})})
             (sut/parse-packets (sut/hex->binary "EE00D40C823060")))))))

(deftest solve-part-one-test
  (are [x y] (= x (sut/solve-part-one y))
    16 "8A004A801A8002F478"
    12 "620080001611562C8802118E34"
    23 "C0015000016115A2E0802F182340"
    31 "A0016C880162017C3686B18A3D4780"))

(deftest solve-part-two-test
  (are [x y] (= x (sut/solve-part-two y))
    3  "C200B40A82"
    54 "04005AC33890"
    7  "880086C3E88112"
    9  "CE00C43D881120"
    1  "D8005AC2A8F0"
    0  "F600BC2D8F"
    0  "9C005AC2F8F0"
    1  "9C0141080250320F1802104A08"))
