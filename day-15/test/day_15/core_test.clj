(ns day-15.core-test
  (:require [clojure.test :as test]
            [day-15.core :as core]))

(test/deftest day-15-test
  (test/testing "probando obtener-hash"
    (test/is (= (core/obtener-hash "HASH") 52)
             (= (core/obtener-hash "rn=1") 30)))
  (test/testing "probando procesar-tarea-1"
    (test/is (= (core/procesar-tarea-1 ["rn=1" "cm-" "qp=3" "cm=2" "qp-" "pc=4" "ot=9" "ab=5" "pc-" "pc=6" "ot=7"]) [30 253 97 47 14 180 9 197 48 214 231]))))
