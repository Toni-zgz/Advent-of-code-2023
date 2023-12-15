(ns day-15.core
  (:gen-class)
  (:require [clojure.string :as st]))

(defn obtener-hash [cadena]
  (reduce (fn [acc elt]
           (if (= elt \space)
             acc
             (-> elt
                (int)
                (+ acc)
                (* 17)
                (rem 256))))
          0 cadena))

(defn procesar-tarea-1 [lista]
  (map obtener-hash lista))

(defn -main [& args]
  (let [entrada-tareas (-> "./resources/input.lst"
                            (slurp)
                            (st/split #","))
        salida-tarea-1 (procesar-tarea-1 entrada-tareas) 
        tarea-1 (->> salida-tarea-1
                     (reduce + 0))]
         (println tarea-1)))
