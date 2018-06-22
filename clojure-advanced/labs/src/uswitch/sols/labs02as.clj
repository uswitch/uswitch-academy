(ns uswitch.sols.labs02as
  (:refer-clojure :exclude [deliver]))

(def dispatch (comp keyword #(str "uswitch.sols.labs02as/" %) :vendor))

(defmulti process dispatch)
(defmethod process ::vodafone [instr] "process vodafone")
(defmethod process ::giffgaff [instr] "process giffgaff")

(defmethod process ::ee [instr] "process ee")

(defmulti validate dispatch)
(defmethod validate ::vodafone [instr] "validate vodafone")
(defmethod validate ::giffgaff [instr] "validate giffgaff")

(defmethod validate ::ee [instr] "validate ee")

(defmulti deliver dispatch)
(defmethod deliver ::vodafone [instr] "deliver vodafone")
(defmethod deliver ::giffgaff [instr] "deliver giffgaff")

(defmethod deliver ::ee [instr] "deliver ee")
