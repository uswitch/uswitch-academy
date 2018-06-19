(ns uswitch.labs02
  (:refer-clojure :exclude [deliver]))

(def dispatch (comp keyword #(str "uswitch.labs02/" %) :vendor))

(defmulti process dispatch)
(defmethod process ::vodafone [instr] "process vodafone")
(defmethod process ::giffgaff [instr] "process giffgaff")

(defmulti validate dispatch)
(defmethod validate ::vodafone [instr] "validate vodafone")
(defmethod validate ::giffgaff [instr] "validate giffgaff")

(defmulti deliver dispatch)
(defmethod deliver ::vodafone [instr] "deliver vodafone")
(defmethod deliver ::giffgaff [instr] "deliver giffgaff")
