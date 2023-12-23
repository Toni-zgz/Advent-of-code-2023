(ns day-21.core-test
  (:require [clojure.test :refer :all]
            [day-21.core :refer :all]))

(def tablero-1 ["..........."
                ".....###.#."
                ".###.##..#."
                "..#.#.O.#.."
                "...O#O#...."
                ".##.OS####."
                ".##O.#...#."
                "....O..##.."
                ".##.#.####."
                ".##..##.##."
                "..........."])

(def tablero-2 ["..........."
                ".....###.#."
                ".###.##.O#."
                ".O#O#O.O#.."
                "O.O.#.#.O.."
                ".##O.O####."
                ".##.O#O..#."
                ".O.O.O.##.."
                ".##.#.####."
                ".##O.##.##."
                "..........."])

(deftest day-21-test
  (testing "probando get-2d"
    (is (= (get-2d tablero-1 2 3) 
           \#))
    (is (= (get-2d tablero-1 4 5) 
           \O)))
  (testing "Probando obtener-coordenadas"
    (is (= (obtener-coordenadas tablero-1) 
           [[3 6] [4 3] [4 5] [5 4] [6 3] [7 4]]))))
  
