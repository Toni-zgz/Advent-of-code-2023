(ns day-9.core
  (:gen-class)
  (:require [clojure.string :as st]))

; obtener-diferencias :: [Int] -> [Int]
(defn obtener-diferencias [lista] 
  (loop [indice 0
         lista-salida []]
    (if (= indice (- (count lista) 1))
      lista-salida
      (let [nuevo-indice (+ indice 1)
            valor1 (get lista indice)
            valor2 (get lista (+ indice 1))
            diferencia (- valor2 valor1)
            nueva-lista-salida (conj lista-salida diferencia)] 
        (recur nuevo-indice nueva-lista-salida)))))

; zeros :: Int -> [0 .. 0]
(defn zeros [numero-ceros]
  (-> (repeat numero-ceros 0)
      (into [])))

; obtener-arbol-diferencias :: [Int] -> [[Int]]
(defn obtener-arbol-diferencias [lista]
  (loop [arbol-diferencias [lista]] 
    (if (= (last arbol-diferencias) (zeros (count (last arbol-diferencias))))
      arbol-diferencias
      (let [lista-examinada (last arbol-diferencias)
            lista-diferencias (obtener-diferencias lista-examinada)
            nuevo-arbol-diferencias (conj arbol-diferencias lista-diferencias)]
        (recur nuevo-arbol-diferencias)))))

; obtener-siguiente-numero :: [[Int]] -> Int
(defn obtener-siguiente-numero [lista-de-listas]
  (loop [indice (count lista-de-listas)
         acumulador 0]
    (if (= indice 0)
      acumulador
      (let [lista (get lista-de-listas (- indice 1))
            valor (last lista)]
        (recur (- indice 1) (+ acumulador valor))))))

; procesar-tarea-1 :: String -> Int
(defn procesar-tarea-1 [cadena]
  (let [lista-numeros (->>
                       (-> cadena
                           (st/split #" "))
                       (map #(Integer/parseInt %))
                       (into []))
        arbol-diferencias (obtener-arbol-diferencias lista-numeros)]
    (obtener-siguiente-numero arbol-diferencias)))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        salida-tarea-1 (map procesar-tarea-1 entrada-tareas) 
        tarea-1 (reduce + 0 salida-tarea-1)]
    (println tarea-1)))