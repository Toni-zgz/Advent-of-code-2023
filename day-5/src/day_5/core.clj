(ns day-5.core
  (:gen-class)
  (:require [clojure.string :as st]))

; convertir :: [[Long]] -> Long -> Long
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

; procesar-tarea-1 :: [String] -> [Long]
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

; procesar-tarea-2 :: [String] -> [Long]
(defn procesar-tarea-2 [texto]
  (let [tablas (obtener-datos texto)
        tabla-semillas (->> tablas
                            (:seeds)
                            (partition 2))
        tabla-semillas-a-suelo (:seed-to-soil tablas)
        tabla-suelo-a-fertilizante (:soil-to-fertilizer tablas)
        tabla-fertilizante-a-agua (:fertilizer-to-water tablas)
        tabla-agua-a-luz (:water-to-light tablas)
        tabla-luz-a-temperatura (:light-to-temperature tablas)
        tabla-temperatura-a-humedad (:temperature-to-humidity tablas)
        tabla-humedad-a-localizacion (:humidity-to-location tablas)]
    (loop [lista-entrada tabla-semillas
           lista-salida []]
        (if (= lista-entrada '())
            lista-salida
            (let [elt (first lista-entrada)
                  nueva-lista-entrada (rest lista-entrada)
                  actual (first elt)
                  num-elems (second elt)
                  elem-procesado (loop [indice actual
                                        quedan num-elems
                                        valor 1e100]
                                     (if (= quedan 0)
                                         valor
                                         (let [nuevo-indice (+ indice 1)
                                               nuevo-quedan (- quedan 1)
                                               valor-obtenido (->> indice
                                                                  (convertir tabla-semillas-a-suelo)
                                                                  (convertir tabla-suelo-a-fertilizante)
                                                                  (convertir tabla-fertilizante-a-agua)
                                                                  (convertir tabla-agua-a-luz)
                                                                  (convertir tabla-luz-a-temperatura)
                                                                  (convertir tabla-temperatura-a-humedad)
                                                                  (convertir tabla-humedad-a-localizacion))
                                               nuevo-valor (min valor valor-obtenido)]
                                             (println quedan)
                                             (recur nuevo-indice nuevo-quedan nuevo-valor))))
                  nueva-lista-salida (conj lista-salida elem-procesado)]
                (recur nueva-lista-entrada nueva-lista-salida))))))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp))
        ;salida-tarea-1 (procesar-tarea-1 entrada-tareas)
        salida-tarea-2 (procesar-tarea-2 entrada-tareas)
        ;; tarea-1 (->> salida-tarea-1
        ;;              (reduce min))
        tarea-2 (->> salida-tarea-2
                     (reduce min))]
    ;(println tarea-1)
    (println tarea-2)))
