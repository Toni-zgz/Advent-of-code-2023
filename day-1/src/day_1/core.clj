(ns day-1.core
  (:gen-class)
  (:require [clojure.string :as st]))

; procesar-tarea1 :: String -> Int
(defn procesar-tarea-1 [fila]
  (let [lista-numeros (filter #(Character/isDigit %) fila)
        primer-numero (first lista-numeros)
        ultimo-numero (last lista-numeros)
        cadena-numeros (str primer-numero ultimo-numero)]
    (Integer/parseInt cadena-numeros)))

; procesar-tarea2 :: String -> Int
(defn procesar-tarea-2 [fila]
  (-> fila
      (st/replace #"one" "o1e")   ; sustituyo la palabra por el numero manteniendo 
      (st/replace #"two" "t2o")   ; las letras inicial y final de la palabra
      (st/replace #"three" "t3e") ; en vez de sustituir la palabra en ingles por el 
      (st/replace #"four" "f4r")  ; numero para evitar que se pierdan numeros
      (st/replace #"five" "f5e")  ; anteriores que esten en una regla inferior.
      (st/replace #"six" "s6x")
      (st/replace #"seven" "s7n")
      (st/replace #"eight" "e8t")
      (st/replace #"nine" "n9e")
      (st/replace #"zero" "z0o")
      (procesar-tarea-1)))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        salida-tarea-1 (map procesar-tarea-1 entrada-tareas)
        salida-tarea-2 (map procesar-tarea-2 entrada-tareas)
        tarea-1 (reduce + 0 salida-tarea-1)
        tarea-2 (reduce + 0 salida-tarea-2)]
    (println tarea-1)
    (println tarea-2)))
