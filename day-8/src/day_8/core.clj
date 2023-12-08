(ns day-8.core
  (:gen-class)
  (:require [clojure.string :as st]))

; obtener-numero-ciclos :: String -> {String, [String String]} -> Int
(defn obtener-numero-ciclos [cadena nodos]
  (loop [numero-ciclos 0
         indice-programa 0
         nodo "AAA"]
    (if (= nodo "ZZZ")
      numero-ciclos
      (let [nuevo-indice-programa (if (= indice-programa (- (count cadena) 1))
                                    0
                                    (+ indice-programa 1))
            direccion (-> (get cadena indice-programa)
                          (str))
            nuevo-nodo (if (= direccion "L")
                         (-> (get nodos nodo)
                             (first))
                         (-> (get nodos nodo)
                             (second)))]
        (recur (+ numero-ciclos 1) nuevo-indice-programa nuevo-nodo)))))

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

(defn procesar-tarea-1 [lista-filas]
  (let [cadena-programa (first lista-filas)
        dict-nodos (obtener-datos (drop 2 lista-filas))]
    (obtener-numero-ciclos cadena-programa dict-nodos)))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        salida-tarea-1 (procesar-tarea-1 entrada-tareas)]
    (println salida-tarea-1)))