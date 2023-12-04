(ns day-4.core
  (:gen-class)
  (:require [clojure.string :as st])
  (:require [clojure.math :as math]))

; obtener-tarjetas :: String -> [String]
(defn obtener-tarjetas [cadena]
  (->>
   (-> cadena
       (st/split #":")
       (second)
       (st/trim)
       (st/split #"\|"))
   (map st/trim)))

; incluido-en-lista? :: Int -> [Int] -> Bool
(defn incluido-en-lista? [valor lista]
  (let [mapa (zipmap lista lista)]
    (not (nil? (mapa valor)))))

; comprobar-tarjetas :: String -> String -> Int
(defn comprobar-tarjetas [cad-ganadora cad-mia]
  (let [lista-ganadora (->> (st/split cad-ganadora #" ")
                            (remove #(= % ""))
                            (map #(Integer/parseInt %))) 
        lista-mia (->> (st/split cad-mia #" ")
                       (remove  #(= % ""))
                       (map #(Integer/parseInt %)))]
    (-> (filter #(incluido-en-lista? % lista-ganadora) lista-mia)
        (count))))

; obtener-puntuacion :: Int -> Int
(defn obtener-puntuacion [numero]
  (int (math/pow 2 (- numero 1))))

; procesar-tarea-1 :: String -> Int
(defn procesar-tarea-1 [fila]
  (let [tarjetas (obtener-tarjetas fila)
        ganadora (first tarjetas)
        mia (second tarjetas)
        valor (comprobar-tarjetas ganadora mia)]
    (obtener-puntuacion valor)))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        salida-tarea-1 (map procesar-tarea-1 entrada-tareas) 
        tarea-1 (->> salida-tarea-1
                     (reduce + 0))] 
    (println tarea-1)))
