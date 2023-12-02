(ns day-2.core
  (:gen-class)
  (:require [clojure.string :as st]))

; extraer-componente :: [String] -> String
(defn extraer-componente [lista color]
  (let [lista-color (filter #(st/ends-with? % color) lista)
        lista-color-arreglada (if (= '() lista-color)
                                '("0")
                                lista-color)]
    (-> lista-color-arreglada
        (first)
        (st/split #" ")
        (first))))

; obtener-componente :: String -> [Integer]
(defn obtener-componente [cadena]
  (let [lista (->> (st/split cadena #",")
                   (map st/trim))
        cad-rojos (extraer-componente lista "red")
        cad-verdes (extraer-componente lista "green")
        cad-azules (extraer-componente lista "blue")
        num-rojos (Integer/parseInt cad-rojos)
        num-verdes (Integer/parseInt cad-verdes)
        num-azules (Integer/parseInt cad-azules)]
    [num-rojos num-verdes num-azules]))

; obtener-componentes :: [String] -> [[Integer]]
(defn obtener-componentes [lista]
  (map obtener-componente lista))

; obtener-maximos :: String -> [Integer]
(defn obtener-maximos [cadena]
  (let [division-cadena (st/split cadena #";")
        componentes (obtener-componentes division-cadena)
        max-rojos (reduce (fn [acc elt] (max acc (nth elt 0))) 0 componentes) 
        max-verdes (reduce (fn [acc elt] (max acc (nth elt 1))) 0 componentes)
        max-azules (reduce (fn [acc elt] (max acc (nth elt 2))) 0 componentes)]
    [max-rojos max-verdes max-azules]))

; procesar-tarea-1 :: String -> Int
(defn procesar-tarea-1 [fila]
  (let [division-cadena (st/split fila #":")
        id-juego (-> division-cadena
                         (first)
                         (st/trim)
                         (st/split #" ")
                         (second))
        num-juego (Integer/parseInt id-juego)
        resto-cadena (second division-cadena)
        maximos (obtener-maximos resto-cadena)
        max-rojos (first maximos)
        max-verdes (second maximos)
        max-azules (nth maximos 2)]
    (if (and (<= max-rojos 12)
             (<= max-verdes 13)
             (<= max-azules 14))
      num-juego
      0)))

; procesar-tarea-2 :: String -> Int
(defn procesar-tarea-2 [fila]
  (let [division-cadena (st/split fila #":")
        cadena (second division-cadena)
        maximos (obtener-maximos cadena)
        max-rojos (first maximos)
        max-verdes (second maximos)
        max-azules (nth maximos 2)]
    (* max-rojos max-verdes max-azules)))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        salida-tarea-1 (map procesar-tarea-1 entrada-tareas)
        salida-tarea-2 (map procesar-tarea-2 entrada-tareas)
        tarea-1 (->> salida-tarea-1
                     (reduce + 0))
        tarea-2 (->> salida-tarea-2
                     (reduce + 0))] 
    (println tarea-1)
    (println tarea-2)))
