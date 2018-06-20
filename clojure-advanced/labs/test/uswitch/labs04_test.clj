(ns uswitch.labs04-test
  (:require [clojure.test :refer :all]
            [uswitch.labs04 :as l4]
            [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]))

(defmacro generative
  "Convinient macro to perform the given number of
  generative tests on a function. It integrates with clojure.test"
  [sym num-tests]
  `(let [opts# {:clojure.spec.test.check/opts {:num-tests ~num-tests}}]
     (-> (stest/check ~sym opts#)
         first
         stest/abbrev-result
         :failure
         nil?
         (is "\n*** Failed generative testing ***\n"))))

