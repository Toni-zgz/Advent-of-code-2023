(ns day-8.core-test
  (:require [clojure.test :as test]
            [day-8.core :as core]))

(test/deftest day-8-test
  (test/testing "probando obtener-datos"
    (test/is (= (core/obtener-datos ["AAA = (BBB, CCC)"
                                "BBB = (DDD, EEE)"
                                "CCC = (ZZZ, GGG)"
                                "DDD = (DDD, DDD)"
                                "EEE = (EEE, EEE)"
                                "GGG = (GGG, GGG)"
                                "ZZZ = (ZZZ, ZZZ)"]) 
                {"AAA" ["BBB" "CCC"]
                 "BBB" ["DDD" "EEE"]
                 "CCC" ["ZZZ" "GGG"]
                 "DDD" ["DDD" "DDD"]
                 "EEE" ["EEE" "EEE"]
                 "GGG" ["GGG" "GGG"]
                 "ZZZ" ["ZZZ" "ZZZ"]})))
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
                                        "ZZZ = (ZZZ, ZZZ)"]) 6))))
