(ns day-15.core-test
  (:require [clojure.test :as test]
            [day-15.core :as core]))

(test/deftest day-15-test
  (test/testing "probando obtener-hash"
    (test/is (= (core/obtener-hash "HASH") 52))
    (test/is (= (core/obtener-hash "rn=1") 30)))
  (test/testing "probando procesar-tarea-1"
    (test/is (= (core/procesar-tarea-1 ["rn=1" "cm-" "qp=3" "cm=2" "qp-" "pc=4" "ot=9" "ab=5" "pc-" "pc=6" "ot=7"]) [30 253 97 47 14 180 9 197 48 214 231])))
  (test/testing "obtener-puntuacion"
    (test/is (= (core/obtener-puntuacion {0 [["rn" 1] ["cm" 2]]
                                          3 [["ot" 7] ["ab" 5] ["pc" 6]]}) [5 140])))
  (test/testing "probando eliminar-identificador"
    (test/is (= (core/eliminar-identificador [["rn" 1] ["cm" 2]] "rn") [["cm" 2]]))
    (test/is (= (core/eliminar-identificador [["rn" 1] ["cm" 2]] "cm") [["rn" 1]]))
    (test/is (= (core/eliminar-identificador [["rn" 1] ["cm" 2]] "qp") [["rn" 1] ["cm" 2]])))
  (test/testing "probando actualizar-valor"
    (test/is (= (core/actualizar-valor [["ot" 7] ["ab" 5] ["pc" 6]] ["ab" 3]) [["ot" 7] ["ab" 3] ["pc" 6]]))
    (test/is (= (core/actualizar-valor [["ot" 7] ["ab" 5] ["pc" 6]] ["rn" 2]) [["ot" 7] ["ab" 5] ["pc" 6]["rn" 2]])))
  (test/testing "probando lista-codigo->mapa"
    (test/is (= (core/lista-codigo->mapa ["rn=1" "cm-" "qp=3" "cm=2" "qp-" "pc=4" "ot=9" "ab=5" "pc-" "pc=6" "ot=7"]) {0 [["rn" 1] ["cm" 2]]
                                                                                                                       3 [["ot" 7] ["ab" 5] ["pc" 6]]}))))
