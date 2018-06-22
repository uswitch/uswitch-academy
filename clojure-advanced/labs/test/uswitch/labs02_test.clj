(ns uswitch.labs02-test
  (:require [clojure.test :refer :all]
            [uswitch.labs02a :as l2a]
            [uswitch.labs02b :as l2b]
            ))

(deftest vendor-multimethods
  (testing "process returns the vendor as string"
    (is (= "process vodafone" (l2a/process {:vendor "vodafone"})))
    (is (= "process giffgaff" (l2a/process {:vendor "giffgaff"}))))
  (testing "validation is also vendor specific"
    (is (= "validate vodafone" (l2a/validate {:vendor "vodafone"})))
    (is (= "validate giffgaff" (l2a/validate {:vendor "giffgaff"}))))

  #_(testing "we also need to deliver each vendor"
    (is (= "deliver vodafone" (l2a/deliver {:vendor "vodafone"})))
    (is (= "deliver giffgaff" (l2a/deliver {:vendor "giffgaff"}))))
  #_(testing "now EE comes in, we need to install the new vendor"
    (is (= "process ee" (l2a/process {:vendor "ee"})))
    (is (= "validate ee" (l2a/validate {:vendor "ee"})))
    (is (= "deliver ee" (l2a/deliver {:vendor "ee"})))))

#_(deftest refactor-into-protocols
  (let [vodafone-instr {:vendor "Vodafone"}
        giffgaff-instr {:vendor "GiffGaff"}
        ee-instr {:vendor "EE"}
        vodafone (l2b/lookup-vendor vodafone-instr)
        giffgaff (l2b/lookup-vendor giffgaff-instr)
        ee (l2b/lookup-vendor ee-instr)]
    (testing "calling is very similar, but needs a vendor object"
      (is (= "process Vodafone"   (l2b/process vodafone vodafone-instr)))
      (is (= "validate Vodafone"  (l2b/validate vodafone vodafone-instr)))
      (is (= "deliver Vodafone"   (l2b/deliver vodafone vodafone-instr)))
      (is (= "process GiffGaff"   (l2b/process giffgaff giffgaff-instr)))
      (is (= "validate GiffGaff"  (l2b/validate giffgaff giffgaff-instr)))
      (is (= "deliver GiffGaff"   (l2b/deliver giffgaff giffgaff-instr)))
      (is (= "process EE"         (l2b/process ee ee-instr)))
      (is (= "validate EE"        (l2b/validate ee ee-instr)))
      (is (= "deliver EE"         (l2b/deliver ee ee-instr))))))
