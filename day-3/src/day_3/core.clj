(ns day-3.core
  (:gen-class)
  (:require [clojure.string :as st]))

; simbolo? :: Char -> True
(defn simbolo? [caracter]
  ; Es un simbolo si no es un digito ni un punto.
  (cond (Character/isDigit caracter) false
        (= \. caracter) false
        :else true))

; obtener-coord-simbolos :: [[Char]] -> [(Int Int)]
(defn obtener-coord-simbolos [tablero]
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
                                    coords-simbolo (if (simbolo? (nth fila num-columna))
                                                     (conj elems (list num-fila num-columna))
                                                     elems)]
                                (recur nuevo-num-columna coords-simbolo))))] 
        (recur nuevo-num-fila (conj coords nuevas-coord))))))

; dentro-tablero? :: Int -> Int -> Int -> Int -> Bool
(defn dentro-tablero? [fila columna max-fila max-columna]
  (cond (= fila 0) false
        (= columna 0) false
        (= fila max-fila) false
        (= columna max-columna) false
        :else true))

; obtener-coords-adyacentes-casilla :: [[Char]] -> (Int Int) -> [(Int Int)]
(defn obtener-coords-adyacentes-casilla [tablero casilla] 
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

; obtener-coords-adyacentes-numero :: [[Char]] -> [(Int Int)] -> [(Int Int)]
(defn obtener-coords-adyacentes-con-digito [tablero lista-coords]
  (loop [lista-entrada lista-coords
         salida []]
    (if (= lista-entrada [])
      (->> salida
           (flatten)
           (partition 2))
      (let [casilla (first lista-entrada)
            nueva-lista-entrada (rest lista-entrada)
            nuevos-elems (obtener-coords-adyacentes-casilla tablero casilla)
            nueva-salida (conj salida nuevos-elems)]
        (recur nueva-lista-entrada nueva-salida)))))

; obtener-coord-digitos :: [[Char]] -> [(Int Int)] -> [(Int Int)]
(defn obtener-coord-digitos [tablero lista-coords]
  (let [numero? (fn [casilla]
                  (let [fila (first casilla)
                        columna (second casilla)
                        elt (-> tablero
                                 (nth fila)
                                 (nth columna))]
                    (Character/isDigit elt)))]
    (filter numero? lista-coords)))

; obtener-coord-numeros :: [[Char]] -> [(Int Int)] -> [((Int Int) (Int Int))]
(defn obtener-coord-numeros [tablero lista-coords]
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

; procesar-tarea-1 :: [[Char]] -> [Int]
(defn procesar-tarea-1 [tablero]
  (->> tablero
       (obtener-coord-simbolos)
       (obtener-coords-adyacentes-con-digito tablero)
       (obtener-coord-digitos tablero)
       (obtener-coord-numeros tablero)
       (quitar-duplicados)
       (obtener-cadenas-numeros tablero)
       (lista-cadenas-numeros->lista-numeros)))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines)
                            (map seq))
        salida-tarea-1 (procesar-tarea-1 entrada-tareas)
        ;salida-tarea-2 (map procesar-tarea-2 entrada-tareas)
        tarea-1 (->> salida-tarea-1
                     (reduce + 0))
        ;tarea-2 (->> salida-tarea-2
        ;             (reduce + 0))
        ]
    (println tarea-1)
    ;(println tarea-2)
    ))
