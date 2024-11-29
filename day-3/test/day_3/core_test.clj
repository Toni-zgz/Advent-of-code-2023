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

(def coord-adyacentes-simbolos '[(0 2) (0 3) (0 4) (1 2) (1 4) (2 2) (2 3) (2 4)
                                 (2 5) (2 6) (2 7) (3 5) (3 7) (4 5) (4 6) (4 7)
                                 (3 2) (3 3) (3 4) (4 2) (4 4) (5 2) (5 3) (5 4)
                                 (4 4) (4 5) (4 6) (5 4) (5 6) (6 4) (6 5) (6 6)
                                 (7 2) (7 3) (7 4) (8 2) (8 4) (9 2) (9 3) (9 4)
                                 (7 4) (7 5) (7 6) (8 4) (8 6) (9 4) (9 5) (9 6)])

(def coord-digitos-simbolos '[(0 2) (2 2) (2 3) (2 6) (2 7) (4 2) (6 4) (9 2) (9 3) (7 6) (9 5) (9 6)])

(def coord-numeros-simbolos '[((0 0) (0 2)) ((2 2) (2 3)) ((2 2) (2 3)) ((2 6) (2 8)) ((2 6) (2 8)) ((4 0) (4 2))
                              ((6 2) (6 4)) ((9 1) (9 3)) ((9 1) (9 3)) ((7 6) (7 8)) ((9 5) (9 7)) ((9 5) (9 7))])

(def sin-duplicados-simbolos '[((0 0) (0 2)) ((2 2) (2 3)) ((2 6) (2 8)) ((4 0) (4 2))
                               ((6 2) (6 4)) ((9 1) (9 3)) ((7 6) (7 8)) ((9 5) (9 7))])

(def lista-cadenas-simbolos '("467" "35" "633" "617" "592" "664" "755" "598"))

(def lista-numeros-simbolos '[467 35 633 617 592 664 755 598])

(test/deftest tarea-1-test
  (test/testing "Probando la funcion obtener-coord-simbolos"
    (test/is (= (core/obtener-coord-simbolos inicial) coord-simbolos)))
  (test/testing "Probando la funcion obtener-coord-adyacentes-con-digito-simbolos"
    (test/is (= (core/obtener-coords-adyacentes-con-digito-simbolos inicial coord-simbolos) coord-adyacentes-simbolos)))
  (test/testing "Probando la funcion obtener-coord-digitos-simbolos"
    (test/is (= (core/obtener-coord-digitos-simbolos inicial coord-adyacentes-simbolos) coord-digitos-simbolos)))
  (test/testing "Probando la funcion obtener-coord-numeros-simbolos"
    (test/is (= (core/obtener-coord-numeros-simbolos inicial coord-digitos-simbolos) coord-numeros-simbolos)))
  (test/testing "Probando la funcion quitar-duplicados"
    (test/is (= (core/quitar-duplicados coord-numeros-simbolos) sin-duplicados-simbolos)))
  (test/testing "Probando la funcion obtener-cadenas-numeros"
    (test/is (= (core/obtener-cadenas-numeros inicial sin-duplicados-simbolos) lista-cadenas-simbolos)))
  (test/testing "Probando la funcion lista-cadenas-numeros->lista-numeros"
    (test/is (= (core/lista-cadenas-numeros->lista-numeros lista-cadenas-simbolos) lista-numeros-simbolos)))
  (test/testing "Probando la funcion procesar-tarea-1"
    (test/is (= (core/procesar-tarea-1 inicial) 4361))))

(def coord-asteriscos '[(1 3) (4 3) (8 5)])

(def coord-adyacentes-asteriscos '[((0 2) (0 3) (0 4) (1 2) (1 4) (2 2) (2 3) (2 4))
   ((3 2) (3 3) (3 4) (4 2) (4 4) (5 2) (5 3) (5 4))
   ((7 4) (7 5) (7 6) (8 4) (8 6) (9 4) (9 5) (9 6))])

(def coord-digitos-asteriscos '[((0 2) (2 2) (2 3))
                                ((4 2))
                                ((7 6) (9 5) (9 6))])

(def coord-numeros-asteriscos '[(((0 0) (0 2)) ((2 2) (2 3)) ((2 2) (2 3))) 
                                (((4 0) (4 2)))
                                (((7 6) (7 8)) ((9 5) (9 7)) ((9 5) (9 7)))])

(def sin-duplicados-asteriscos '[(((0 0) (0 2)) ((2 2) (2 3)))
                                 (((4 0) (4 2)))
                                 (((7 6) (7 8)) ((9 5) (9 7)))])

(def coord-numeros-engranajes '[(((0 0) (0 2)) ((2 2) (2 3)))
                               (((7 6) (7 8)) ((9 5) (9 7)))])

(def lista-cadenas-engranajes '(("467" "35") ("755" "598")))

(def lista-numeros-engranajes '[(467 35) (755 598)])

(def lista-potencia-engranajes '[16345 451490])

(test/deftest tarea-2-test
  (test/testing "Probando la funcion obtener-coord-asteriscos"
    (test/is (= (core/obtener-coord-asteriscos inicial) coord-asteriscos)))
  (test/testing "Probando la funcion obtener-coord-adyacentes-con-digito-asteriscos"
    (test/is (= (core/obtener-coords-adyacentes-con-digito-asteriscos inicial coord-asteriscos) coord-adyacentes-asteriscos)))
  (test/testing "Probando la funcion obtener-coord-digitos-asteriscos"
    (test/is (= (core/obtener-coord-digitos-asteriscos inicial coord-adyacentes-asteriscos) coord-digitos-asteriscos)))
  (test/testing "Probando la funcion obtener-coord-numeros-asteriscos"
    (test/is (= (core/obtener-coord-numeros-asteriscos inicial coord-digitos-asteriscos) coord-numeros-asteriscos)))
  (test/testing "Probando la funcion quitar-duplicados-asteriscos"
    (test/is (= (core/quitar-duplicados-asteriscos coord-numeros-asteriscos) sin-duplicados-asteriscos)))
  (test/testing "Probando la funcion obtener-coord-numeros-engranajes"
    (test/is (= (core/obtener-coord-digitos-engranajes sin-duplicados-asteriscos) coord-numeros-engranajes)))
  (test/testing "Probando la funcion obtener-cadenas-numeros-engranajes"
    (test/is (= (core/obtener-cadenas-numeros-engranajes inicial coord-numeros-engranajes) lista-cadenas-engranajes)))
  (test/testing "Probando la funcion lista-cadenas-numeros-engranajes->lista-numeros-engranajes"
    (test/is (= (core/lista-cadenas-numeros-engranajes->lista-numeros-engranajes lista-cadenas-engranajes) lista-numeros-engranajes)))
  (test/testing "Probando la funcion calcular-potencia-engranaje"
    (test/is (= (core/calcular-potencia-engranaje lista-numeros-engranajes) lista-potencia-engranajes)))
  (test/testing "Probando la funcion procesar-tarea-2"
    (test/is (= (core/procesar-tarea-2 inicial) 467835))))
