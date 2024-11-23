(ns day-5.core-test
  (:require [clojure.test :as test]
            [day-5.core :as core]))

(def cadena "seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4")

(def datos {:seeds '(79 14 55 13)
            :seed-to-soil '((50 98 2)
                            (52 50 48))
            :soil-to-fertilizer '((0 15 37)
                                  (37 52 2)
                                  (39 0 15))
            :fertilizer-to-water '((49 53 8)
                                   (0 11 42)
                                   (42 0 7)
                                   (57 7 4))
            :water-to-light '((88 18 7)
                              (18 25 70))
            :light-to-temperature '((45 77 23)
                                    (81 45 19)
                                    (68 64 13))
            :temperature-to-humidity '((0 69 1)
                                       (1 0 69))
            :humidity-to-location '((60 56 37)
                                    (56 93 4))})

(def seed->soil '((50 98 2)
                  (52 50 48)))

(test/deftest day-5-test
  (test/testing "probando convertir"
    (test/is (= (core/convertir seed->soil 79) 81))
    (test/is (= (core/convertir seed->soil 14) 14))
    (test/is (= (core/convertir seed->soil 55) 57))
    (test/is (= (core/convertir seed->soil 98) 50)))
  (test/testing "probando obtener-datos"
    (test/is (= (core/obtener-datos cadena) datos)))
  (test/testing "probando procesar-tarea-1"
    (test/is (= (core/procesar-tarea-1 cadena) '(82 43 86 35))))
  (test/testing "probando procesar-tarea-1"
    (test/is (= (core/procesar-tarea-2 cadena) [46N 57N]))))