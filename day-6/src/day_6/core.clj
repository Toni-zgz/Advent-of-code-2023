(ns day-6.core
  (:gen-class)
  (:require [clojure.string :as st]))

; obtener-distancia :: Int -> Int -> Int
(defn obtener-distancia [tiempo-total tiempo-carga]
  (let [velocidad tiempo-carga
        tiempo-carrera (- tiempo-total tiempo-carga)]
    (* velocidad tiempo-carrera)))

; obtener-numero-jugadas-optimas :: Int -> Int -> Int
(defn obtener-numero-jugadas-optimas [tiempo distancia]
  (let [lista-tiempos-carga (range 1 tiempo)
        lista-distancias (map #(obtener-distancia tiempo %) lista-tiempos-carga)
        lista-distancias-optimas (filter #(> % distancia) lista-distancias)]
    (count lista-distancias-optimas)))

; obtener-lista-tarea-1 :: String -> [Int]
(defn obtener-lista-tarea-1 [cadena]
  (->>
   (-> cadena
       (st/split #":")
       (second)
       (st/trim)
       (st/split #" "))
   (filter #(not (= "" %)))
   (map #(Integer/parseInt %))))

; obtener-lista-tarea-2 :: String -> Int
(defn obtener-numero-tarea-2 [cadena]
  (->>
   (-> cadena
       (st/split #":")
       (second)
       (st/trim)
       (st/split #" "))
   (filter #(not (= "" %)))
   (st/join "")
   (biginteger)))

; procesar-tarea-1 :: [String] -> [Int]
(defn procesar-tarea-1 [filas]
  (let [tiempo (obtener-lista-tarea-1 (first filas))
        distancia (obtener-lista-tarea-1 (second filas))]
    (map obtener-numero-jugadas-optimas tiempo distancia)))

; procesar-tarea-2 :: [String] -> [Int]
(defn procesar-tarea-2 [filas]
  (let [tiempo (obtener-numero-tarea-2 (first filas))
        distancia (obtener-numero-tarea-2 (second filas))]
    (obtener-numero-jugadas-optimas tiempo distancia)))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        salida-tarea-1 (procesar-tarea-1 entrada-tareas)
        salida-tarea-2 (procesar-tarea-2 entrada-tareas)
        tarea-1 (reduce * 1 salida-tarea-1)] 
    (println tarea-1)
    (println salida-tarea-2)))
