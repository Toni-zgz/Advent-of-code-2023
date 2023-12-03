(ns day-1.core
  (:gen-class)
  (:require [clojure.string :as st]))

; procesar-fila :: String -> Int
(defn procesar-fila [fila]
  (let [lista-numeros (filter #(Character/isDigit %) fila)
        primer-numero (first lista-numeros)
        ultimo-numero (last lista-numeros)
        cadena-numeros (str primer-numero ultimo-numero)]
    (Integer/parseInt cadena-numeros)))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        salida-tarea-1 (map procesar-fila entrada-tareas)
        tarea-1 (reduce + 0 salida-tarea-1)]
    (println tarea-1)))
