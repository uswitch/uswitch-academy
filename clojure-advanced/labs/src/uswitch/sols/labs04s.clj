(ns uswitch.sols.labs04s
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as stest]))

(s/def ::integers (s/coll-of int? :distinct true))
(s/def ::limited-integers (s/coll-of int? :max-count 5 :distinct true))

(s/fdef powerset
  :args (s/cat :xs ::integers)
  :ret seqable?
  :fn (fn [{:keys [args ret]}]
        (and (== (dec (Math/pow 2 (count (:xs args)))) (count ret))
             (= (count ret) (count (distinct ret))))))

(defn powerset
  "Generates the 2^n - 1 subsets of xs including
  the input itself but not the empty set. 'rotate'
  generates combinations for each size. 'step' removes
  1 item each recursion forming smaller subsets. O(2^n)."
  [xs]
  (letfn [(rotate [xs]
            (map rest (iterate #(concat (rest %) (list (first %))) xs)))
          (step [n xs]
            (when-let [rs (seq (take n (rotate xs)))]
              (when (ffirst rs)
                (into (mapcat step (range (dec n) 0 -1) rs) rs))))]
    (keep seq (conj (step (count xs) xs) xs))))

; (deftest powerset-test
;   (testing "generates unique combinations irrespective of ordering"
;     (is (= (set '((0 1 2) (2) (0) (1) (1 2) (0 1) (2 0)))
;            (set (l4/powerset (range 3)))))
;     (is (= (set '((0 1) (0 2) (3 0) (1 2) (3 1) (2 3)))
;            (set (filter #(= 2 (count %)) (l4/powerset (range 4))))))
;     (is (= [] (l4/powerset ())))))

; (deftest powerset-gen (generative `l4/powerset 30))

; (deftest verification
;   (testing ""
;     (is (= [] (s/exercise-fn `l4/powerset)))))


(s/fdef fizz-buzz-seq
        :args (s/cat :n int?)
        :ret seqable?
        :fn (fn [{:keys [args ret]}]
              (if (> (:n args) 15)
                (= #{"fizz" "buzz" "fizzbuzz"}
                   (set (filter string? ret)))
                (= (count ret) (:n args)))))

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
