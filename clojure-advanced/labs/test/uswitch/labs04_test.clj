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

(deftest fizzy
  (testing "I wrote my tests, so it works. What about going generative?"
    (is (= [1 2 "fizz"] (l4/fizz-buzz-seq 3)))
    (is (= [1 2 "fizz" 4 "buzz"] (l4/fizz-buzz-seq 5)))
    (is (= [1 2 "fizz" 4 "buzz" "fizz" 7 8 "fizz" "buzz" 11 "fizz" 13 14 "fizzbuzz"]
           (l4/fizz-buzz-seq 15)))))

;; Uncomment to run the generative tests.
; (deftest fizzy-gen (generative `l4/fizz-buzz-seq 30))
