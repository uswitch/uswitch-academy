(ns uswitch.labs03)

(defmacro letp [bindings & body]
  ; `(let ~[compute the content of this first vector]
  ;   (let ~[and the content of the second one]
  ;     ~@body))
  )

(defmacro x []
  `(defmacro x []
     (println "writing")))
