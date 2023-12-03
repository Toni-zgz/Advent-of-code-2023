(ns day-1.core-test
  (:require [clojure.test :refer :all]
            [day-1.core :refer :all]))

(deftest day-1-test
  (testing "probando procesar-fila"
    (is (= (procesar-fila "1abc2") 12))
    (is (= (procesar-fila "pqr3stu8vwx") 38))
    (is (= (procesar-fila "a1b2c3d4e5f") 15))
    (is (= (procesar-fila "treb7uchet") 77))))
