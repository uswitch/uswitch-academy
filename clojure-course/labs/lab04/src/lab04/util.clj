(ns lab04.util)

(defn sanitize
  "remove bogus chars"
  [x]
  (apply str (remove #(= \? %) x)))
