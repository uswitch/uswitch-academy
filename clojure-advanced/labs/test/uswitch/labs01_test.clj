(ns uswitch.labs01-test
  (:require [clojure.test :refer :all]
            [uswitch.labs01 :as l1]))

(deftest s3-list-objects
  (let [_ (reset! l1/counter 0)
        s3-objs (->> {:marker 0} l1/list-objects)]
    (testing "it returns 1000 objects"
      (is (= 1000 (count (take 1000 s3-objs)))))
    #_(testing "it calls s3 once"
      (is (= 1 @l1/counter)))
    #_(testing "it calls s3 again"
      (let [more-objs (doall (take 1001 s3-objs))]
        (is (= 2 @l1/counter))))))

(deftest lazy-seq-is-caching
  (let [_ (reset! l1/counter 0)
        s3-objs (->> {:marker 0} l1/list-objects)]
    #_(testing "it makes a call"
      (is (= 1 (do (count (take 1000 s3-objs)) @l1/counter))))
    #_(testing "it does not make another call"
      (is (= 1 (do (count (take 1000 s3-objs)) @l1/counter))))))
