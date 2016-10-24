(ns lab07.core-test
  (:require [clojure.test :refer :all]
            [lab07.core :refer :all]))

(deftest a-test
  (testing "Should not fail"
    (is (= 1 1))))

(deftest test-with-grouped-asserts
  (testing "many way to sum up to 5"
    (are [x y] (= 5 (+ x y))
         2 3
         1 4
         3 2)))

(defn check-int? [a]
  (or (instance? Long a)
      (string? a)))

(defn some-fixture
  [f]
  (try
    (with-redefs [clojure.core/integer? check-int?]
      (f))
    (finally
      "release db-conn.")))

(use-fixtures :once some-fixture)

(deftest mocking-and-fixtures
  (testing "I really want strings as ints."
    (is (integer? 4))
    (is (integer? "4"))))
