(ns lab07.calculator-test
  (:require [clojure.test :refer :all]
            [lab07.calculator :refer :all]))

(deftest a-calculator
  (testing "should sum up two numbers"
    (is (= 2 (plus 1 1)))))
