(ns uswitch.labs02b
  (:refer-clojure :exclude [deliver]))

(defprotocol Vendor
  ;; Declare functions here.
  )

(defrecord Vodafone [endpoint]
  Vendor
  ;; implement for each vendor
  )

(defn lookup-vendor
  "The lookup function is ready to use."
  [instr]
  (when-let [initf (find-var (symbol (str "uswitch.labs02b/->" (:vendor instr))))]
    (initf "http")))
