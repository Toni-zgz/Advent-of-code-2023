(ns day-5.core
  (:gen-class)
  (:require [clojure.string :as st]))

; convertir :: [[Int]] -> Int
(defn convertir [tabla-conversion valor]
  (loop [tabla tabla-conversion]
    (if (= tabla '())
      valor
      (let [fila-tabla (first tabla)
            inicio-fuente (second fila-tabla)
            inicio-destino (first fila-tabla)
            rango (nth fila-tabla 2)
            final-fuente (+ inicio-fuente rango)]
        (if (and (>= valor inicio-fuente)
                 (<= valor final-fuente))
          (+ inicio-destino (- valor inicio-fuente))
          (recur (rest tabla)))))))

; obtener-datos :: String -> Dictionary
(defn obtener-datos [cadena]
  (let [lista (st/split cadena #"seeds:|\nseed-to-soil map:|\nsoil-to-fertilizer map:|\nfertilizer-to-water map:|\nwater-to-light map:|\nlight-to-temperature map:|\ntemperature-to-humidity map:|\nhumidity-to-location map:")
        tabla-semillas (->>
                        (-> (second lista)
                            (st/trim)
                            (st/split-lines)
                            (first)
                            (st/split #" "))
                        (map #(bigint %)))
        tabla-semillas-a-suelo (->>
                                (-> (nth lista 2)
                                    (st/trim)
                                    (st/split-lines))
                                (map #(st/split % #" "))
                                (map #(map (fn [elt] (bigint elt)) %)))
        tabla-suelo-a-fertilizante (->>
                                    (-> (nth lista 3)
                                        (st/trim)
                                        (st/split-lines))
                                    (map #(st/split % #" "))
                                    (map #(map (fn [elt] (bigint elt)) %)))
        tabla-fertilizante-a-agua (->>
                                   (-> (nth lista 4)
                                       (st/trim)
                                       (st/split-lines))
                                   (map #(st/split % #" "))
                                   (map #(map (fn [elt] (bigint elt)) %)))
        tabla-agua-a-luz (->>
                          (-> (nth lista 5)
                              (st/trim)
                              (st/split-lines))
                          (map #(st/split % #" "))
                          (map #(map (fn [elt] (bigint elt)) %)))
        tabla-luz-a-temperatura (->>
                                 (-> (nth lista 6)
                                     (st/trim)
                                     (st/split-lines))
                                 (map #(st/split % #" "))
                                 (map #(map (fn [elt] (bigint elt)) %)))
        tabla-temperatura-a-humedad (->>
                                     (-> (nth lista 7)
                                         (st/trim)
                                         (st/split-lines))
                                     (map #(st/split % #" "))
                                     (map #(map (fn [elt] (bigint elt)) %)))
        tabla-humedad-a-localizacion (->>
                                      (-> (nth lista 8)
                                          (st/trim)
                                          (st/split-lines))
                                      (map #(st/split % #" "))
                                      (map #(map (fn [elt] (bigint elt)) %)))]
    {:seeds tabla-semillas
     :seed-to-soil tabla-semillas-a-suelo
     :soil-to-fertilizer tabla-suelo-a-fertilizante
     :fertilizer-to-water tabla-fertilizante-a-agua
     :water-to-light tabla-agua-a-luz
     :light-to-temperature tabla-luz-a-temperatura
     :temperature-to-humidity tabla-temperatura-a-humedad
     :humidity-to-location tabla-humedad-a-localizacion}))

; procesar-tarea-1 :: [String] -> [Int]
(defn procesar-tarea-1 [texto]
  (let [tablas (obtener-datos texto)
        tabla-semillas (:seeds tablas)
        tabla-semillas-a-suelo (:seed-to-soil tablas)
        tabla-suelo-a-fertilizante (:soil-to-fertilizer tablas)
        tabla-fertilizante-a-agua (:fertilizer-to-water tablas)
        tabla-agua-a-luz (:water-to-light tablas)
        tabla-luz-a-temperatura (:light-to-temperature tablas)
        tabla-temperatura-a-humedad (:temperature-to-humidity tablas)
        tabla-humedad-a-localizacion (:humidity-to-location tablas)]
    (->> tabla-semillas
         (map #(convertir tabla-semillas-a-suelo %))
         (map #(convertir tabla-suelo-a-fertilizante %))
         (map #(convertir tabla-fertilizante-a-agua %))
         (map #(convertir tabla-agua-a-luz %))
         (map #(convertir tabla-luz-a-temperatura %))
         (map #(convertir tabla-temperatura-a-humedad %))
         (map #(convertir tabla-humedad-a-localizacion %)))))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp))
        salida-tarea-1 (procesar-tarea-1 entrada-tareas)
        tarea-1 (->> salida-tarea-1
                     (reduce min))]
    (println tarea-1)))
