(ns day-6.core-test
  (:require [clojure.test :as test]
            [day-6.core :as core]))

(test/deftest day-6-test
  (test/testing "probando obtener-distancia"
    (test/is (= (core/obtener-distancia 7 0) 0))
    (test/is (= (core/obtener-distancia 7 1) 6))
    (test/is (= (core/obtener-distancia 7 2) 10))
    (test/is (= (core/obtener-distancia 7 3) 12))
    (test/is (= (core/obtener-distancia 7 4) 12))
    (test/is (= (core/obtener-distancia 7 5) 10))
    (test/is (= (core/obtener-distancia 7 6) 6))
    (test/is (= (core/obtener-distancia 7 7) 0)))
  (test/testing "probando obtener-numero-jugadas-optimas"
    (test/is (= (core/obtener-numero-jugadas-optimas 7 9) 4))
    (test/is (= (core/obtener-numero-jugadas-optimas 15 40) 8))
    (test/is (= (core/obtener-numero-jugadas-optimas 30 200) 9)))
  (test/testing "probandoo obtener-lista"
    (test/is (= (core/obtener-lista "Time:      7  15   30") [7 15 30]))
    (test/is (= (core/obtener-lista "Distance:  9  40  200") [9 40 200]))))
