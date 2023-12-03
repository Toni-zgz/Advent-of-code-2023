(ns day-1.core-test
  (:require [clojure.test :refer :all]
            [day-1.core :refer :all]))

(deftest day-1-test
  (testing "probando procesar-tarea-1"
    (is (= (procesar-tarea-1 "1abc2") 12))
    (is (= (procesar-tarea-1 "pqr3stu8vwx") 38))
    (is (= (procesar-tarea-1 "a1b2c3d4e5f") 15))
    (is (= (procesar-tarea-1 "treb7uchet") 77)))
  (testing "probando procesar-tarea-2"
    (is (= (procesar-tarea-2 "two1nine") 29))
    (is (= (procesar-tarea-2 "eightwothree") 83))
    (is (= (procesar-tarea-2 "abcone2threexyz") 13))
    (is (= (procesar-tarea-2 "xtwone3four") 24))
    (is (= (procesar-tarea-2 "4nineeightseven2") 42))
    (is (= (procesar-tarea-2 "zoneight234") 14))
    (is (= (procesar-tarea-2 "7pqrstsixteen") 76))))







