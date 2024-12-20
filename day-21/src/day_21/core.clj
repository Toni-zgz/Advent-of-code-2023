(ns day-21.core
  (:gen-class)
  (:require [clojure.string :as st]))

; get-2d :: [A] -> Int -> Int -> A
(defn get-2d [lista nfila ncolumna]
  (-> lista
       (get nfila)
       (get ncolumna)))

; set-2d :: [A] -> Int -> Int -> A -> [A]
(defn set-2d [lista nfila ncolumna valor]
  (let [nueva-fila (-> (get lista nfila)
                       (st/split #"")
                       (assoc ncolumna valor)
                       (st/join))]
    (assoc lista nfila nueva-fila)))

; obtener-coordenadas :: [String] -> [[Int Int]]
(defn obtener-coordenadas [tablero valor-a-buscar]
  (let [alto (count tablero)
        ancho (-> (first tablero)
                  (count))
        rango-columnas (range 0 ancho)
        rango-filas (range 0 alto)
        lista-coordenadas (for [fila rango-filas
                                columna rango-columnas]
                            [fila columna])
        es-una-o? (fn [elt]
                    (let [x (first elt)
                          y (second elt)
                          valor (get-2d tablero x y)]
                      (= valor valor-a-buscar)))]
    (filter es-una-o? lista-coordenadas)))

; imprimir-siguiente-paso :: [String] -> [[Int Int]] -> [String]
(defn imprimir-siguiente-paso [tablero-inicial lista-coordenadas]
  (loop [lista lista-coordenadas
         tablero tablero-inicial]
    (if (= lista [])
      tablero
      (let [nueva-lista (rest lista)
            coordenada (first lista)
            coordenadas-posibles (let [fila (first coordenada)
                                       columna (second coordenada)
                                       arriba [(- fila 1) columna]
                                       abajo [(+ fila 1) columna]
                                       izquierda [fila (- columna 1)]
                                       derecha [fila (+ columna 1)]]
                                   [arriba abajo derecha izquierda])
            coordenadas-validas (filter (fn [elt]
                                          (let [nfila (first elt)
                                                ncolumna (second elt)
                                                valor (get-2d tablero nfila ncolumna)]
                                            (not (= valor \#))))
                                        coordenadas-posibles)
            nuevo-tablero (loop [coordenadas coordenadas-validas
                                 tablero-bucle tablero]
                            (if (= coordenadas '())
                              tablero-bucle
                              (let [coord (first coordenadas)
                                    fila (first coord)
                                    columna (second coord)
                                    nuevo-tablero-bucle (set-2d tablero-bucle fila columna \O)]
                                (recur (rest coordenadas) nuevo-tablero-bucle))))]
        (recur nueva-lista nuevo-tablero)))))

(defn procesar-tarea-1 [tablero-inicial nveces]
  (loop [nveces-bucle nveces
         tablero-bucle tablero-inicial]
    (if (= nveces-bucle 0)
      tablero-bucle
      (let [lista-coordenadas (obtener-coordenadas tablero-bucle \O)
            lista-valida (if (= lista-coordenadas '())
                           (obtener-coordenadas tablero-bucle \S)
                           lista-coordenadas)
            nuevo-tablero-bucle (imprimir-siguiente-paso tablero-inicial lista-valida)]
        (recur (- nveces-bucle 1) nuevo-tablero-bucle)))))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        num-pasos 64
        salida-tarea-1 (procesar-tarea-1 entrada-tareas num-pasos) 
        tarea-1 (-> (obtener-coordenadas salida-tarea-1 \O)
                    (count))]
    (println tarea-1)))
