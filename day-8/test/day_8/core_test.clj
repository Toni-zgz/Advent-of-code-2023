(ns day-8.core-test
  (:require [clojure.test :as test]
            [day-8.core :as core]))

(def nodos1  {"AAA" ["BBB" "CCC"]
              "BBB" ["DDD" "EEE"]
              "CCC" ["ZZZ" "GGG"]
              "DDD" ["DDD" "DDD"]
              "EEE" ["EEE" "EEE"]
              "GGG" ["GGG" "GGG"]
              "ZZZ" ["ZZZ" "ZZZ"]})

(test/deftest day-8-test
  (test/testing "probando obtener-datos"
    (test/is (= (core/obtener-datos ["AAA = (BBB, CCC)"
                                     "BBB = (DDD, EEE)"
                                     "CCC = (ZZZ, GGG)"
                                     "DDD = (DDD, DDD)"
                                     "EEE = (EEE, EEE)"
                                     "GGG = (GGG, GGG)"
                                     "ZZZ = (ZZZ, ZZZ)"])
                nodos1)))
  (test/testing "probando obtener-nuevo-nodo"
    (test/is (= (core/obtener-nuevo-nodo nodos1 "AAA" "L") "BBB"))
    (test/is (= (core/obtener-nuevo-nodo nodos1 "CCC" "R") "GGG")))
  (test/testing "probando procesar-tarea-1"
    (test/is (= (core/procesar-tarea-1 ["RL"
                                        ""
                                        "AAA = (BBB, CCC)"
                                        "BBB = (DDD, EEE)"
                                        "CCC = (ZZZ, GGG)"
                                        "DDD = (DDD, DDD)"
                                        "EEE = (EEE, EEE)"
                                        "GGG = (GGG, GGG)"
                                        "ZZZ = (ZZZ, ZZZ)"]) 2))
    (test/is (= (core/procesar-tarea-1 ["LLR"
                                        ""
                                        "AAA = (BBB, BBB)"
                                        "BBB = (AAA, ZZZ)"
                                        "ZZZ = (ZZZ, ZZZ)"]) 6)))
  (test/testing "probando procesar-tarea-2"
    (test/is (= (core/procesar-tarea-2 ["LR"
                            ""
                            "11A = (11B, XXX)"
                            "11B = (XXX, 11Z)"
                            "11Z = (11B, XXX)"
                            "22A = (22B, XXX)"
                            "22B = (22C, 22C)"
                            "22C = (22Z, 22Z)"
                            "22Z = (22B, 22B)"
                            "XXX = (XXX, XXX)"]) 6))))
