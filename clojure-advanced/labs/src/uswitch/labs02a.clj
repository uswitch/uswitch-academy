(ns uswitch.labs02a
  (:refer-clojure :exclude [deliver]))

(def dispatch (comp keyword #(str "uswitch.labs02a/" %) :vendor))

(defmulti process dispatch)
(defmethod process ::vodafone [instr] "process vodafone")
(defmethod process ::giffgaff [instr] "process giffgaff")


(defmulti validate dispatch)
(defmethod validate ::vodafone [instr] "validate vodafone")
(defmethod validate ::giffgaff [instr] "validate giffgaff")

