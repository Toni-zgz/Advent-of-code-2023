(ns day-3.core
  (:gen-class)
  (:require [clojure.string :as st]))

;;;;;;;;;;;;;;;;;;;;; Funciones internas ;;;;;;;;;;;;;;;;;;;;;;

; simbolo? :: Char -> True
(defn- simbolo? [caracter]
  ; Es un simbolo si no es un digito ni un punto.
  (and (not (Character/isDigit caracter))
        (not (= \. caracter))))

; asterisco? :: Char -> True
(defn- asterisco? [caracter]
       (= \* caracter))

; obtener-coords :: fn -> [[Char]] -> [(Int Int)]
(defn- obtener-coords [pred tablero]
  (loop [num-fila 0
         coords []]
    (if (= num-fila (count tablero))
      ; Como la estructura de coords tiene muchos vectores vacios y vectores anidados dentro,
      ; aplanamos la estructura, cogemos los elementos de 2 en 2 y los encerramos en un vector.
      (->> coords
           (flatten)
           (partition 2)) 
      (let [fila (nth tablero num-fila)
            nuevo-num-fila (+ num-fila 1)
            nuevas-coord  (loop [num-columna 0
                                 elems []]
                            (if (= num-columna (count fila))
                              elems
                              (let [nuevo-num-columna (+ num-columna 1)
                                    coords-simbolo (if (pred (nth fila num-columna))
                                                     (conj elems (list num-fila num-columna))
                                                     elems)]
                                (recur nuevo-num-columna coords-simbolo))))] 
        (recur nuevo-num-fila (conj coords nuevas-coord))))))

; dentro-tablero? :: Int -> Int -> Int -> Int -> Bool
(defn- dentro-tablero? [fila columna max-fila max-columna]
  (cond (= fila 0) false
        (= columna 0) false
        (= fila max-fila) false
        (= columna max-columna) false
        :else true))

; obtener-coords-adyacentes-casilla :: [[Char]] -> (Int Int) -> [(Int Int)]
(defn- obtener-coords-adyacentes-casilla [tablero casilla] 
  (let [fila (first casilla)
        columna (second casilla)
        max-fila (count tablero)
        max-columna max-fila
        sup-izq (if (dentro-tablero? fila columna max-fila max-columna)
                  (list (- fila 1) (- columna 1))
                  '())
        sup-cen (if (dentro-tablero? fila columna max-fila max-columna)
                  (list (- fila 1) columna)
                  '())
        sup-der (if (dentro-tablero? fila columna max-fila max-columna)
                  (list (- fila 1) (+ columna 1))
                  '())
        cen-izq (if (dentro-tablero? fila columna max-fila max-columna)
                  (list fila (- columna 1))
                  '())
        cen-der (if (dentro-tablero? fila columna max-fila max-columna)
                  (list fila (+ columna 1))
                  '())
        inf-izq (if (dentro-tablero? fila columna max-fila max-columna)
                  (list (+ fila 1) (- columna 1))
                  '())
        inf-cen (if (dentro-tablero? fila columna max-fila max-columna)
                  (list (+ fila 1) columna)
                  '())
        inf-der (if (dentro-tablero? fila columna max-fila max-columna)
                  (list (+ fila 1) (+ columna 1))
                  '())] 
    (list sup-izq sup-cen sup-der cen-izq cen-der inf-izq inf-cen inf-der)))

; obtener-coords-adyacentes-con-digito :: [[Char]] -> [(Int Int)] -> ([A] -> [A] -> [A] | [(A)]) -> [(Int Int)] | [((Int Int))]
(defn- obtener-coords-adyacentes-con-digito [tablero lista-coords funcion-union]
  (loop [lista-entrada lista-coords
         salida []]
    (if (= lista-entrada [])
      salida
      (let [casilla (first lista-entrada)
            nueva-lista-entrada (rest lista-entrada)
            nuevos-elems (obtener-coords-adyacentes-casilla tablero casilla)
            nueva-salida (funcion-union salida nuevos-elems)]
        (recur nueva-lista-entrada nueva-salida)))))

;;;;;;;;;;;;;;;;;;;;; Funciones tarea 1 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; obtener-coord-simbolos :: [[Char]] -> [(Int Int)]
(defn obtener-coord-simbolos [tablero]
  (obtener-coords simbolo? tablero))

; obtener-coords-adyacentes-con-digito-simbolos :: [[Char]] -> [(Int Int)] -> [(Int Int)]
(defn obtener-coords-adyacentes-con-digito-simbolos [tablero lista-coords]
  (obtener-coords-adyacentes-con-digito tablero lista-coords concat))

; obtener-coord-digitos-simbolos :: [[Char]] -> [(Int Int)] -> [(Int Int)]
(defn obtener-coord-digitos-simbolos [tablero lista-coords]
  (let [numero? (fn [casilla]
                  (let [fila (first casilla)
                        columna (second casilla)
                        elt (-> tablero
                                 (nth fila)
                                 (nth columna))]
                    (Character/isDigit elt)))]
    (filter numero? lista-coords)))

; obtener-coord-numeros-simbolos :: [[Char]] -> [(Int Int)] -> [((Int Int) (Int Int))]
(defn obtener-coord-numeros-simbolos [tablero lista-coords]
  (let [funcion-mapeo (fn [elt]
                        (let [casilla elt
                              fila (first casilla)
                              columna (second casilla)
                              columna-maxima (-> tablero
                                                 (first)
                                                 (count)
                                                 (- 1))
            ; Tengo que inicializar la variable caracter a \0 porque sino me da un error
            ; java.lang.IllegalArgumentException: No matching method isDigit found taking 1 args,
            ; dicha inicialización no afecta al programa porque se supone que el caracter inicial es un digito.
                              datos-izq (loop [indice columna
                                               caracter \0]
                                          (cond (not (Character/isDigit caracter)) (list fila (+ indice 1))
                                                (= indice 0) (list fila 0)
                                                :else (let [nuevo-indice (- indice 1)
                                                            nuevo-caracter (-> tablero
                                                                               (nth fila)
                                                                               (nth nuevo-indice))]
                                                        (recur nuevo-indice nuevo-caracter))))
            ; Tengo que inicializar la variable caracter a \0 porque sino me da un error
            ; java.lang.IllegalArgumentException: No matching method isDigit found taking 1 args,
            ; dicha inicialización no afecta al programa porque se supone que el caracter inicial es un digito.
                              datos-dcha (loop [indice columna
                                                caracter \0]
                                           (cond (not (Character/isDigit caracter)) (list fila (- indice 1))
                                                 (= indice columna-maxima) (list fila columna-maxima)
                                                 :else (let [nuevo-indice (+ indice 1)
                                                             nuevo-caracter (-> tablero
                                                                                (nth fila)
                                                                                (nth nuevo-indice))]
                                                         (recur nuevo-indice nuevo-caracter))))]
                          (list datos-izq datos-dcha)))]
    (map funcion-mapeo lista-coords)))  

; quitar-duplicados :: [A] -> [A]
(defn quitar-duplicados [lista-entrada]
  (distinct lista-entrada))

; obtener-cadenas-numeros :: [[Char]] -> [((Int Int) (Int Int))] -> [String]
(defn obtener-cadenas-numeros [tablero lista-limites]
  (let [coord->cadenas (fn [elt]
                         (let [coord-inic (first elt)
                               coord-final (second elt)
                               fila (first coord-inic)
                               col-inic (second coord-inic)
                               col-final (second coord-final)]
                           (loop [indice col-inic
                                  salida []]
                             (if (> indice col-final)
                               (apply str salida)
                               (let [nuevo-indice (+ indice 1)
                                     digito (-> tablero
                                                (nth fila)
                                                (nth indice)
                                                (list))
                                     nueva-salida (concat salida digito)]
                                 (recur nuevo-indice nueva-salida))))))]
    (map coord->cadenas lista-limites)))

; lista-cadenas-numeros->lista-numeros :: [String] -> [Int]
(defn lista-cadenas-numeros->lista-numeros [lista-cadenas]
  (map read-string lista-cadenas))

; procesar-tarea-1 :: [[Char]] -> Int
(defn procesar-tarea-1 [tablero]
  (->> tablero
       (obtener-coord-simbolos)
       (obtener-coords-adyacentes-con-digito-simbolos tablero)
       (obtener-coord-digitos-simbolos tablero)
       (obtener-coord-numeros-simbolos tablero)
       (quitar-duplicados)
       (obtener-cadenas-numeros tablero)
       (lista-cadenas-numeros->lista-numeros) 
       (reduce + 0)))

;;;;;;;;;;;;;;;;;;;;; Funciones tarea 2 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; obtener-coord-asteriscos :: [[Char]] -> [(Int Int)]
(defn obtener-coord-asteriscos [tablero]
  (obtener-coords asterisco? tablero))

; obtener-coords-adyacentes-con-digito-asteriscos :: [[Char]] -> [(Int Int)] -> [((Int Int))]
(defn obtener-coords-adyacentes-con-digito-asteriscos [tablero lista-coords]
  (obtener-coords-adyacentes-con-digito tablero lista-coords conj))

; obtener-coord-digitos-asteriscos :: [[Char]] -> [((Int Int))] -> [((Int Int))]
(defn obtener-coord-digitos-asteriscos [tablero lista-coords]
  (let [numero? (fn [casilla]
                  (let [fila (first casilla)
                        columna (second casilla)
                        elt (-> tablero
                                 (nth fila)
                                 (nth columna))]
                    (Character/isDigit elt)))
        funcion-mapeo (fn [elt]
                        (filter numero? elt))]
    (map funcion-mapeo lista-coords)))

; obtener-coord-numeros-asteriscos :: [[Char]] -> [((Int Int))] -> [(((Int Int) (Int Int)))]
(defn obtener-coord-numeros-asteriscos [tablero lista-coords]
  (let [funcion-mapeo-interna (fn [elt]
                        (let [casilla elt
                              fila (first casilla)
                              columna (second casilla)
                              columna-maxima (-> tablero
                                                 (first)
                                                 (count)
                                                 (- 1))
            ; Tengo que inicializar la variable caracter a \0 porque sino me da un error
            ; java.lang.IllegalArgumentException: No matching method isDigit found taking 1 args,
            ; dicha inicialización no afecta al programa porque se supone que el caracter inicial es un digito.
                              datos-izq (loop [indice columna
                                               caracter \0]
                                          (cond (not (Character/isDigit caracter)) (list fila (+ indice 1))
                                                (= indice 0) (list fila 0)
                                                :else (let [nuevo-indice (- indice 1)
                                                            nuevo-caracter (-> tablero
                                                                               (nth fila)
                                                                               (nth nuevo-indice))]
                                                        (recur nuevo-indice nuevo-caracter))))
            ; Tengo que inicializar la variable caracter a \0 porque sino me da un error
            ; java.lang.IllegalArgumentException: No matching method isDigit found taking 1 args,
            ; dicha inicialización no afecta al programa porque se supone que el caracter inicial es un digito.
                              datos-dcha (loop [indice columna
                                                caracter \0]
                                           (cond (not (Character/isDigit caracter)) (list fila (- indice 1))
                                                 (= indice columna-maxima) (list fila columna-maxima)
                                                 :else (let [nuevo-indice (+ indice 1)
                                                             nuevo-caracter (-> tablero
                                                                                (nth fila)
                                                                                (nth nuevo-indice))]
                                                         (recur nuevo-indice nuevo-caracter))))]
                          (list datos-izq datos-dcha)))
        funcion-mapeo-externa (fn [elt] 
                                (map funcion-mapeo-interna elt))]
      (map funcion-mapeo-externa lista-coords)))

; quitar-duplicados-asteriscos :: [(A)] -> [(A)]
(defn quitar-duplicados-asteriscos [lista-entrada]
  (map quitar-duplicados lista-entrada))

; obtener-coord-digitos-engranajes :: [((Int Int))] -> [((Int Int))]
(defn obtener-coord-digitos-engranajes [lista-coords]
  (let [engranaje? (fn [elt]
                        (-> elt
                          (count)
                          (= 2)))]
    (filter engranaje? lista-coords)))

; obtener-cadenas-numeros-engranajes :: [[Char]] -> [(((Int Int) (Int Int)))] -> [(String)]
(defn obtener-cadenas-numeros-engranajes [tablero lista-limites]
  (map #(obtener-cadenas-numeros tablero %) lista-limites))

; lista-cadenas-numeros-engranajes->lista-numeros-engranajes :: [(String)] -> [(Int)]
(defn lista-cadenas-numeros-engranajes->lista-numeros-engranajes [lista-cadenas]
  (map #(map read-string %) lista-cadenas))

; calcular-potencia-engranaje :: [(Int)] -> [Int]
(defn calcular-potencia-engranaje [lista-numeros]
  (map #(reduce * 1 %) lista-numeros))

; procesar-tarea-2 :: [[Char]] -> Int
 (defn procesar-tarea-2 [tablero]
  (->> tablero
       (obtener-coord-asteriscos)
       (obtener-coords-adyacentes-con-digito-asteriscos tablero)
       (obtener-coord-digitos-asteriscos tablero)
       (obtener-coord-numeros-asteriscos tablero)
       (quitar-duplicados-asteriscos)
       (obtener-coord-digitos-engranajes)
       (obtener-cadenas-numeros-engranajes tablero)
       (lista-cadenas-numeros-engranajes->lista-numeros-engranajes)
       (calcular-potencia-engranaje)
       (reduce + 0)))

;;;;;;;;;;;;;;;;;;;;; Programa principal ;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)          ; Unit -> String
                            (st/split-lines) ; String -> [String]
                            (map seq))       ; [String] -> [[Char]]
        salida-tarea-1 (procesar-tarea-1 entrada-tareas)
        salida-tarea-2 (procesar-tarea-2 entrada-tareas)]
    (println salida-tarea-1)
    (println salida-tarea-2)))
