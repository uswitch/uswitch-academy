(ns uswitch.sols.labs02bs
  (:refer-clojure :exclude [deliver]))

(defprotocol Vendor
  (process [vendor instr])
  (validate [vendor instr])
  (deliver [vendor instr]))

(defrecord Vodafone [endpoint]
  Vendor
  (process [vendor instr] "process Vodafone")
  (validate [vendor instr] "validate Vodafone")
  (deliver [vendor instr] "deliver Vodafone"))

(defrecord GiffGaff [endpoint]
  Vendor
  (process [vendor instr] "process GiffGaff")
  (validate [vendor instr] "validate GiffGaff")
  (deliver [vendor instr] "deliver GiffGaff"))

(defrecord EE [endpoint]
  Vendor
  (process [vendor instr] "process EE")
  (validate [vendor instr] "validate EE")
  (deliver [vendor instr] "deliver EE"))

(defn lookup-vendor [instr]
  (when-let [initf (find-var (symbol (str "uswitch.sols.labs02bs/->" (:vendor instr))))]
    (initf "http")))
