(ns lab04.core)

(defn execute
  "start the computation"
  [x]
  (u/sanitize (s/upper-case x)))
