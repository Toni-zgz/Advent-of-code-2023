(ns day-3.core-test
  (:require [clojure.test :as test]
            [day-3.core :as core]))

(def inicial [[\4 \6 \7 \. \. \1 \1 \4 \. \.]
              [\. \. \. \* \. \. \. \. \. \.]
              [\. \. \3 \5 \. \. \6 \3 \3 \.]
              [\. \. \. \. \. \. \# \. \. \.]
              [\6 \1 \7 \* \. \. \. \. \. \.]
              [\. \. \. \. \. \+ \. \5 \8 \.]
              [\. \. \5 \9 \2 \. \. \. \. \.]
              [\. \. \. \. \. \. \7 \5 \5 \.]
              [\. \. \. \$ \. \* \. \. \. \.]
              [\. \6 \6 \4 \. \5 \9 \8 \. \.]])

(def coord-simbolos '[(1 3) (3 6) (4 3) (5 5) (8 3) (8 5)])

(def coord-adyacentes '[(0 2) (0 3) (0 4) (1 2) (1 4) (2 2) (2 3) (2 4)
                        (2 5) (2 6) (2 7) (3 5) (3 7) (4 5) (4 6) (4 7)
                        (3 2) (3 3) (3 4) (4 2) (4 4) (5 2) (5 3) (5 4)
                        (4 4) (4 5) (4 6) (5 4) (5 6) (6 4) (6 5) (6 6)
                        (7 2) (7 3) (7 4) (8 2) (8 4) (9 2) (9 3) (9 4)
                        (7 4) (7 5) (7 6) (8 4) (8 6) (9 4) (9 5) (9 6)])

(def coord-digitos '[(0 2) (2 2) (2 3) (2 6) (2 7) (4 2) (6 4) (9 2) (9 3) (7 6) (9 5) (9 6)])

(def coord-numeros '[((0 0) (0 2)) ((2 2) (2 3)) ((2 2) (2 3)) ((2 6) (2 8)) ((2 6) (2 8)) ((4 0) (4 2))
                     ((6 2) (6 4)) ((9 1) (9 3)) ((9 1) (9 3)) ((7 6) (7 8)) ((9 5) (9 7)) ((9 5) (9 7))])

(def sin-duplicados '[((0 0) (0 2)) ((2 2) (2 3)) ((2 6) (2 8)) ((4 0) (4 2))
                     ((6 2) (6 4)) ((9 1) (9 3)) ((7 6) (7 8)) ((9 5) (9 7))])

(def lista-cadenas '("467" "35" "633" "617" "592" "664" "755" "598"))

(def lista-numeros '[467 35 633 617 592 664 755 598])

(test/deftest day-3-test
  (test/testing "Probando la funcion obtener-coord-simbolos"
    (test/is (= (core/obtener-coord-simbolos inicial) coord-simbolos)))
  (test/testing "Probando la funcion obtener-coord-adyacentes"
    (test/is (= (core/obtener-coords-adyacentes-con-digito inicial coord-simbolos) coord-adyacentes)))
  (test/testing "Probando la funcion obtener-coord-digitos"
    (test/is (= (core/obtener-coord-digitos inicial coord-adyacentes) coord-digitos)))
  (test/testing "Probando la funcion obtener-coord-numeros"
    (test/is (= (core/obtener-coord-numeros inicial coord-digitos) coord-numeros)))
  (test/testing "Probando la funcion quitar-duplicados"
    (test/is (= (core/quitar-duplicados coord-numeros) sin-duplicados)))
  (test/testing "Probando la funcion obtener-cadenas-numeros"
    (test/is (= (core/obtener-cadenas-numeros inicial sin-duplicados) lista-cadenas)))
  (test/testing "Probando la funcion lista-cadenas-numeros->lista-numeros"
    (test/is (= (core/lista-cadenas-numeros->lista-numeros lista-cadenas) lista-numeros)))
  (test/testing "Probando la funcion procesar-tarea-1"
    (test/is (= (->> inicial
                 (core/procesar-tarea-1)
                    (reduce + 0)) 
                4361))))
