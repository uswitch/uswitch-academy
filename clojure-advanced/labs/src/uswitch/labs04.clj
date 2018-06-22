(ns uswitch.labs04
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]))

(s/fdef fizz-buzz-seq
        )

(defn fizz-buzz-seq
  "We have here a fairly common fizz-buzz implementaiton.
  Can we definitely prove it's correct?"
  [n]
  (map
    (fn [i]
      (condp #(zero? (mod %2 %1)) i
        15 "fizzbuzz"
        3 "fizz"
        5 "buzz"
        i))
    (range 1 (inc n))))
