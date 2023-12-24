(ns day-21.core-test
  (:require [clojure.test :as test]
            [day-21.core :as core]))

(def tablero-inicial ["..........."
                      ".....###.#."
                      ".###.##..#."
                      "..#.#...#.."
                      "....#.#...."
                      ".##..S####."
                      ".##..#...#."
                      ".......##.."
                      ".##.#.####."
                      ".##..##.##."
                      "..........."])
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
                ".###.##..#."
                "..#O#O.O#.."
                "..O.#.#...."
                ".##O.O####."
                ".##.O#...#."
                "...O.O.##.."
                ".##.#.####."
                ".##..##.##."
                "..........."])

(def tablero-3 ["..........."
                ".....###.#."
                ".###.##..#."
                "..#.#...#.."
                "....###...."
                ".##..S####."
                ".##..#...#."
                ".......##.."
                ".##.#.####."
                ".##..##.##."
                "..........."])

(def tablero-4 ["..........."
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

(test/deftest day-21-test
  (test/testing "probando get-2d"
    (test/is (= (core/get-2d tablero-1 2 3)
                \#))
    (test/is (= (core/get-2d tablero-1 4 5)
                \O)))
  (test/testing "Probando obtener-coordenadas"
    (test/is (= (core/obtener-coordenadas tablero-1 \O)
                [[3 6] [4 3] [4 5] [5 4] [6 3] [7 4]])))
  (test/testing "probando set-2d"
    (test/is (= (core/set-2d tablero-inicial 4 5 "#") tablero-3)))
  (test/testing "probando imprimir-siguiente-paso"
    (test/is (= (core/imprimir-siguiente-paso tablero-inicial [[3 6] [4 3] [4 5] [5 4] [6 3] [7 4]])
                tablero-2)))
  (test/testing "probando procesar-tarea-1"
    (test/is (= (core/procesar-tarea-1 tablero-inicial 6) tablero-4))))
  
