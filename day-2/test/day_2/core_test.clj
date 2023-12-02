(ns day-2.core-test
  (:require [clojure.test :as test]
            [day-2.core :as core]))

(test/deftest a-test
  (test/testing "probando procesar-tarea-1"
    (test/is (= (core/procesar-tarea-1 "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green") 1))
    (test/is (= (core/procesar-tarea-1 "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue") 2))
    (test/is (= (core/procesar-tarea-1 "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red") 0))
    (test/is (= (core/procesar-tarea-1 "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red") 0))
    (test/is (= (core/procesar-tarea-1 "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green") 5)))
  (test/testing "probando procesar-tarea-2"
    (test/is (= (core/procesar-tarea-2 "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green") 48))
    (test/is (= (core/procesar-tarea-2 "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue") 12))
    (test/is (= (core/procesar-tarea-2 "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red") 1560))
    (test/is (= (core/procesar-tarea-2 "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red") 630))
    (test/is (= (core/procesar-tarea-2 "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green") 36)))
  (test/testing "probando obtener-maximos"
    (test/is (= (core/obtener-maximos "3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green") [4 2 6]))
    (test/is (= (core/obtener-maximos "1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue") [1 3 4]))
    (test/is (= (core/obtener-maximos "8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red") [20 13 6]))
    (test/is (= (core/obtener-maximos "1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red") [14 3 15]))
    (test/is (= (core/obtener-maximos "6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green") [6 3 2])))
  (test/testing "probando obtener-componentes"
    (test/is (= (core/obtener-componentes ["3 blue, 4 red" "1 red, 2 green, 6 blue" "2 green"]) '([4 0 3] [1 2 6] [0 2 0])))
    (test/is (= (core/obtener-componentes ["1 blue, 2 green" "3 green, 4 blue, 1 red" "1 green, 1 blue"]) '([0 2 1] [1 3 4] [0 1 1])))
    (test/is (= (core/obtener-componentes ["8 green, 6 blue, 20 red" "5 blue, 4 red, 13 green" "5 green, 1 red"]) '([20 8 6] [4 13 5] [1 5 0])))
    (test/is (= (core/obtener-componentes ["1 green, 3 red, 6 blue" "3 green, 6 red" "3 green, 15 blue, 14 red"]) '([3 1 6] [6 3 0] [14 3 15])))
    (test/is (= (core/obtener-componentes ["6 red, 1 blue, 3 green"  "2 blue, 1 red, 2 green"]) '([6 3 1] [1 2 2]))))
  (test/testing "probando obtener-componente"
    (test/is (= (core/obtener-componente "3 blue, 4 red") [4 0 3]))
    (test/is (= (core/obtener-componente "1 red, 2 green, 6 blue") [1 2 6]))
    (test/is (= (core/obtener-componente "2 green") [0 2 0]))))