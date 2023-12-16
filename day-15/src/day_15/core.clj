(ns day-15.core
  (:gen-class)
  (:require [clojure.string :as st]))

; obtener-hash :: String -> Int
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

; obtener-puntuacion :: {Int, [[String Int]]} -> [Int]
(defn obtener-puntuacion [mapa]
  (map (fn [elt1]
         (loop [valor 0
                lista-entrada (second elt1)
                slot 1] 
           (if (= lista-entrada [])
             valor
             (let [elt2 (first lista-entrada)
                   caja (+ (first elt1) 1)
                   longitud-focal (second elt2)
                   calculo (* caja slot longitud-focal)
                   nuevo-valor (+ valor calculo)
                   nueva-lista-entrada (rest lista-entrada)]
             (recur nuevo-valor nueva-lista-entrada (+ slot 1))))))
       mapa))

; actualizar-valor :: [[String Int]] -> [String Int] -> [[String Int]]
(defn actualizar-valor [lista-de-listas lista]
  (loop [lista-entrada lista-de-listas
         actualizado? false
         lista-salida []]
    (cond (and (= lista-entrada []) 
               actualizado?) lista-salida  
          (= lista-entrada []) (conj lista-salida lista)
          (nil? lista-de-listas) [lista]
          :else (let [elt (first lista-entrada)
                      identificador (first elt)
                      ident-buscar (first lista)
                      valor-a-actualizar (second lista)
                      nuevo-actualizado? (if (= identificador ident-buscar)
                                           true
                                           actualizado?)
                      nuevo-valor (if (= identificador ident-buscar)
                                    [identificador valor-a-actualizar]
                                    elt)
                      nueva-lista-salida (conj lista-salida nuevo-valor)]
                  (recur (rest lista-entrada) nuevo-actualizado? nueva-lista-salida)))))

; eliminar-identificador :: [[String Int]] -> String -> [[String Int]]
(defn eliminar-identificador [lista-de-listas identificador]
  (filter (fn [elt]
            (not (= (first elt) identificador)))
          lista-de-listas))

; lista-codigo->mapa :: [String] -> {Int, [[String Int]]}
(defn lista-codigo->mapa [lista]
  (loop [mapa-salida {}
         lista-entrada lista]
    (if (= lista-entrada [])
      mapa-salida
      (let [elemento (first lista-entrada)
            codigo (if (st/includes? elemento "=")
                     (let [lista-codigo (st/split elemento #"=")
                           identificador (first lista-codigo)
                           valor (Long/parseLong (second lista-codigo))]
                       [identificador valor])
                     (st/split elemento #"-"))
            identificador (first codigo)
            clave (obtener-hash identificador)
            valor (mapa-salida clave)
            nuevo-valor (if (st/includes? elemento "=")
                         (actualizar-valor valor codigo)
                         (eliminar-identificador valor identificador))
            nuevo-mapa-salida (if (= nuevo-valor '())
                                (dissoc mapa-salida clave)
                                (assoc mapa-salida clave nuevo-valor))
            nueva-lista-entrada (rest lista-entrada)]
        (recur nuevo-mapa-salida nueva-lista-entrada)))))

; procesar-tarea-2 :: [String] -> [Int]
(defn procesar-tarea-2 [lista]
  (-> lista
      (lista-codigo->mapa)
      (obtener-puntuacion)))

; procesar-tarea-1 :: [String] -> [Int]
(defn procesar-tarea-1 [lista]
  (map obtener-hash lista))

(defn -main [& args]
  (let [entrada-tareas (-> "./resources/input.lst"
                           (slurp)
                           (st/split #","))
        salida-tarea-1 (procesar-tarea-1 entrada-tareas)
        salida-tarea-2 (procesar-tarea-2 entrada-tareas)
        tarea-1 (->> salida-tarea-1
                     (reduce + 0))
        tarea-2 (->> salida-tarea-2
                     (reduce + 0))]
    (println tarea-1)
    (println tarea-2)))
