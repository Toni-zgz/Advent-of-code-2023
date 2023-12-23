(ns day-21.core
  (:gen-class)
  (:require clojure.string :as st))

; get-2d :: [A] -> Int -> Int -> A
(defn get-2d [lista nfila ncolumna]
  (-> lista
       (get nfila)
       (get ncolumna)))

; set-2d :: [A] -> Int -> Int -> A -> [A]
(defn set-2d [lista nfila ncolumna valor]
  (let [fila (get lista nfila)
        nueva-fila (assoc fila ncolumna valor)]
    (assoc lista nfila nueva-fila)))

; obtener-coordenadas :: [String] -> [[Int Int]]
(defn obtener-coordenadas [lista-cadenas]
  (let [alto (count lista-cadenas)
        ancho (-> (first lista-cadenas)
                  (count))
        rango-columnas (range 0 ancho)
        rango-filas (range 0 alto)
        lista-coordenadas (for [fila rango-filas
                                columna rango-columnas]
                            [fila columna])
        es-una-o? (fn [elt]
                    (let [x (first elt)
                          y (second elt)
                          valor (get-2d lista-cadenas x y)]
                      (= valor \O)))]
    (filter es-una-o? lista-coordenadas)))

(defn -main [& args]
  (let [entrada-tareas (->> "./resources/input.lst"
                            (slurp)
                            (st/split-lines))
        num-pasos 64
        salida-tarea-1 (procesar-tarea-1 entrada-tareas num-pasos) 
        tarea-1 (reduce + 0 salida-tarea-1)]
    (println tarea-1)))
