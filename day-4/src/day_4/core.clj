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

; actualizar-vector :: [Int] -> (Int) -> [Int]
(defn actualizar-vector [vector sec-cambios]
  (loop [sec sec-cambios
         vector vector]
    (if (= sec '())
      vector
      (let [indice-cambio (first sec)
            nuevo-valor (+ (get vector indice-cambio) 1)
            nuevo-vector (assoc vector indice-cambio nuevo-valor)]
        (recur (rest sec) nuevo-vector)))))

; procesar-tarea-2 :: [String] -> Int
(defn procesar-tarea-2 [lista]
  (let [lista-tarjetas-ganadoras (map (fn [elt] (-> elt
                                                    (obtener-tarjetas)
                                                    (first)))
                                      lista)
        lista-tarjetas-mias (map (fn [elt] (-> elt
                                               (obtener-tarjetas)
                                               (second)))
                                 lista)
        lista-valores (into [] (map comprobar-tarjetas lista-tarjetas-ganadoras lista-tarjetas-mias))]
    (loop [num-iteraciones 0
           indice 0
           vector-procesados (into [] (repeat (count lista) 1))]
      (if (= indice (count lista))
        num-iteraciones
        (let [valor-calculado (get lista-valores indice)
              seq-cambios (range (+ indice 1) (+ indice valor-calculado 1))
              nuevo-valor (- (get vector-procesados indice) 1)
              nuevo-vector-procesados (-> vector-procesados
                                          (actualizar-vector seq-cambios)
                                          (assoc indice nuevo-valor))
              nuevo-indice (if (= (get nuevo-vector-procesados indice) 0)
                             (+ indice 1)
                             indice)]
          (recur (+ num-iteraciones 1) nuevo-indice nuevo-vector-procesados))))))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        salida-tarea-1 (map procesar-tarea-1 entrada-tareas)
        tarea-1 (->> salida-tarea-1
                     (reduce + 0))
        tarea-2 (procesar-tarea-2 entrada-tareas)]
    (println tarea-1)
    (println tarea-2)))
