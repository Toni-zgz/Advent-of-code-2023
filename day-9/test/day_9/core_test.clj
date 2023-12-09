(ns day-9.core-test
  (:require [clojure.test :as test]
            [day-9.core :as core]))

(test/deftest day-9-test
  (test/testing "Probando obtener-diferencias"
    (test/is (= (core/obtener-diferencias [1 3 6 10 15 21]) [2 3 4 5 6]))
    (test/is (= (core/obtener-diferencias [2 3 4 5 6]) [1 1 1 1]))
    (test/is (= (core/obtener-diferencias [1 1 1 1]) [0 0 0])))
  (test/testing "Probando obtener-arbol-diferencias"
    (test/is (= (core/obtener-arbol-diferencias [0 3 6 9 12 15]) [[0 3 6 9 12 15] [3 3 3 3 3] [0 0 0 0]]))
    (test/is (= (core/obtener-arbol-diferencias [1 3 6 10 15 21]) [[1 3 6 10 15 21] [2 3 4 5 6] [1 1 1 1] [0 0 0]]))
    (test/is (= (core/obtener-arbol-diferencias [10 13 16 21 30 45 68]) [[10 13 16 21 30 45 68] [3 3 5 9 15 23] [0 2 4 6 8] [2 2 2 2] [0 0 0]])))
  (test/testing "Probando obtener-siguiente-numero"
    (test/is (= (core/obtener-siguiente-numero [[0 3 6 9 12 15] [3 3 3 3 3] [0 0 0 0]]) 18))
    (test/is (= (core/obtener-siguiente-numero [[1 3 6 10 15 21] [2 3 4 5 6] [1 1 1 1] [0 0 0]]) 28))
    (test/is (= (core/obtener-siguiente-numero [[10 13 16 21 30 45] [3 3 5 9 15] [0 2 4 6] [2 2 2] [0 0]]) 68))))
