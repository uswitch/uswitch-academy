(ns uswitch.sols.labs03s)

(defmacro letp [bindings & body]
  (let [ks (take-nth 2 bindings)
        vs (take-nth 2 (rest bindings))]
    `(let ~(vec (interleave ks (map #(list 'future %) vs)))
       (let ~(vec (interleave ks (map #(list 'deref %) ks)))
         ~@body))))
