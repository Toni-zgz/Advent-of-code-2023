(ns day-8.core
  (:gen-class)
  (:require [clojure.string :as st]))

;;;;;;;;;;;;;;;;;;;;; Funciones comunes  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; obtener-nuevo-ciclo :: {String, [String String]} -> String -> String -> [String]
(defn obtener-nuevo-nodo [nodos nodo direccion] 
  (if (= direccion "L")
    (->> nodo 
        (get nodos)
        (first))
    (->> nodo
        (get nodos)
        (second))))

; obtener-datos :: [String] -> {String, [String String]}
(defn obtener-datos [lista-cadenas]
  (loop [datos {}
         lista lista-cadenas]
    (if (= lista '())
      datos
      (let [cadena (first lista)
            lista-clave-valor (st/split cadena #" =")
            clave (-> (first lista-clave-valor)
                      (st/trim))
            valor (-> (second lista-clave-valor) 
                      (st/replace "(" "")
                      (st/replace ")" " ")
                      (st/trim)
                      (st/split #", "))
            nuevos-datos (assoc datos clave valor)]
        (recur nuevos-datos (rest lista))))))

; igual-a-ZZZ :: String -> Bool
(defn igual-a-ZZZ [cadena]
  (= cadena "ZZZ"))

; termina-en-z? :: String -> Bool
(defn termina-en-z? [cadena]
  (st/ends-with? cadena "Z"))

; gcd :: Int -> Int -> Int
(defn gcd [a b]
  (if (= b 0)
  a
  (gcd b (mod a b))))

;;;;;;;;;;;;;;;;;;;;; Tarea 1 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; obtener-numero-ciclos-tarea-1 :: String -> {String, [String String]} -> String -> Fn -> Int
(defn obtener-numero-ciclos-tarea-1 [cadena nodos valor-inicial valor-final]
  (loop [numero-ciclos 0
         indice-programa 0
         nodo valor-inicial]
   (if (valor-final nodo)
     numero-ciclos
     (let [nuevo-indice-programa (if (= indice-programa (- (count cadena) 1))
                                   0
                                   (+ indice-programa 1))
           direccion (-> (get cadena indice-programa)
                         (str))
           nuevo-nodo (obtener-nuevo-nodo nodos nodo direccion)]
       (recur (+ numero-ciclos 1) nuevo-indice-programa nuevo-nodo)))))

; procesar-tarea-1 :: [String] -> Integer
(defn procesar-tarea-1 [lista-filas]
  (let [cadena-programa (first lista-filas)
        dict-nodos (obtener-datos (drop 2 lista-filas))]
    (obtener-numero-ciclos-tarea-1 cadena-programa dict-nodos "AAA" igual-a-ZZZ)))

;;;;;;;;;;;;;;;;;;;;; Tarea 2 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; obtener-lista-inicial :: {String, [String String]} -> [String]
(defn obtener-lista-inicial [dict]
  (->> dict
       (keys)
       (filter #(st/ends-with? % "A"))))

; obtener-numero-ciclos-tarea-2 :: String -> {String, [String String]} -> Int
(defn obtener-numero-ciclos-tarea-2 [cadena nodos]
  (let [nodo (obtener-lista-inicial nodos)]
   (map #(obtener-numero-ciclos-tarea-1 cadena nodos % termina-en-z?) nodo)))

; procesar-tarea-2 :: [String] -> Integer
; Para ejecutar la tarea 2 en un tiempo optimo, se calcula el numero de ciclos de cada
; elemento de la lista y se calcula el mcm de dichos ciclos para calcular cuando todos
; los ciclos son coincidentes.
(defn procesar-tarea-2 [lista-filas]
  (let [cadena-programa (first lista-filas)
        dict-nodos (obtener-datos (drop 2 lista-filas))
        lista-numero-ciclos (->> dict-nodos
                                 (obtener-numero-ciclos-tarea-2 cadena-programa)
                                 (map #(bigint %)))
        gcd (reduce #(gcd %1 %2) lista-numero-ciclos) ; mcd de los numeros de la lista
        producto (reduce * lista-numero-ciclos)] 
    (/ producto gcd)))   ; Para calcular el mcm, se divide el producto entre el mcd.

;;;;;;;;;;;;;;;;;;;;; programa principal ;;;;;;;;;;;;;;;;

(defn -main [& _args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        salida-tarea-1 (procesar-tarea-1 entrada-tareas)
        salida-tarea-2 (procesar-tarea-2 entrada-tareas)]
    (println salida-tarea-1)
    (println salida-tarea-2)))