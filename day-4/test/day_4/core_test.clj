(ns day-4.core-test
  (:require [clojure.test :as test]
            [day-4.core :as core]))

(test/deftest day-4-test
  (test/testing "Probando obtener-tarjetas"
    (test/is (= (core/obtener-tarjetas "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53") ["41 48 83 86 17" "83 86  6 31 17  9 48 53"]))
    (test/is (= (core/obtener-tarjetas "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19") ["13 32 20 16 61" "61 30 68 82 17 32 24 19"]))
    (test/is (= (core/obtener-tarjetas "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1") ["1 21 53 59 44" "69 82 63 72 16 21 14  1"]))
    (test/is (= (core/obtener-tarjetas "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83") ["41 92 73 84 69" "59 84 76 51 58  5 54 83"]))
    (test/is (= (core/obtener-tarjetas "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36") ["87 83 26 28 32" "88 30 70 12 93 22 82 36"]))
    (test/is (= (core/obtener-tarjetas "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11") ["31 18 13 56 72" "74 77 10 23 35 67 36 11"])))
  (test/testing "Probando incluido-en-lista?"
    (test/is (= (core/incluido-en-lista? 41 [83 86 6 31 17 9 48 53]) false))
    (test/is (= (core/incluido-en-lista? 83 [83 86 6 31 17 9 48 53]) true)))
  (test/testing "Probando comprobar-tarjetas"
    (test/is (= (core/comprobar-tarjetas "41 48 83 86 17" "83 86  6 31 17  9 48 53") 4))
    (test/is (= (core/comprobar-tarjetas "13 32 20 16 61" "61 30 68 82 17 32 24 19") 2))
    (test/is (= (core/comprobar-tarjetas "1 21 53 59 44" "69 82 63 72 16 21 14  1") 2))
    (test/is (= (core/comprobar-tarjetas "41 92 73 84 69" "59 84 76 51 58  5 54 83") 1))
    (test/is (= (core/comprobar-tarjetas "87 83 26 28 32" "88 30 70 12 93 22 82 36") 0))
    (test/is (= (core/comprobar-tarjetas "31 18 13 56 72" "74 77 10 23 35 67 36 11") 0)))
  (test/testing "Probando procesar-tarea-1"
    (test/is (= (core/procesar-tarea-1 "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53") 8))
    (test/is (= (core/procesar-tarea-1 "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19") 2))
    (test/is (= (core/procesar-tarea-1 "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1") 2))
    (test/is (= (core/procesar-tarea-1 "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83") 1))
    (test/is (= (core/procesar-tarea-1 "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36") 0))
    (test/is (= (core/procesar-tarea-1 "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11") 0)))
  (test/testing "Probando obtener-puntuacion"
    (test/is (= (core/obtener-puntuacion 1) 1))
    (test/is (= (core/obtener-puntuacion 2) 2))
    (test/is (= (core/obtener-puntuacion 3) 4))))