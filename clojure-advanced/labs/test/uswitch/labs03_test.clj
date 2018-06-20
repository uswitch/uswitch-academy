(ns uswitch.labs03-test
  (:require [clojure.test :refer :all]
            [uswitch.labs03 :as l3]))

(deftest parallel-let
  #_(testing "it works like normal let"
      (is (= 3 (l3/letp [a 1 b 2] (+ a b))))
      (is (= ":a:b(0 1)" (l3/letp [a (str :a :b) b (range 2)] (str a b)))))
  #_(testing "Not a real test, but letp-elapsed should be 1/2 of let-elapsed"
      (is (= (time (let [a (do (Thread/sleep 1000) (+ 1 1))
                         b (do (Thread/sleep 1000) (* 2 2))]
                     (+ a b)))
             (time (l3/letp [a (do (Thread/sleep 1000) (+ 1 1))
                             b (do (Thread/sleep 1000) (* 2 2))]
                     (+ a b)))))))
