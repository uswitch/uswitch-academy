(ns lab07.sqrt)

(defn good-enough? [guess x]
  (< (Math/abs (- (* guess guess) x)) 0.001))

(defn improve [x guess]
  (/ (+ guess (/ x guess)) 2))

(defn sqrt [x]
  (loop [guess 1.0]
    (if (good-enough? guess x)
      guess
      (recur (improve x guess)))))
