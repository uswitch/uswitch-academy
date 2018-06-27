# ### 3. Xf core.async

# Where it all started

* core.async was shaping up back in 2012-2013
* Having `(map f in out)` was desirable feat.
* But a channel is not a sequence
* Don't want to reimplement it all over:

```clojure
(defn map [f in out]
 (go-loop []
  (let [val (<! in)]
   (if (nil? val)
    (close! out)
    (do (doseq [v (f val)] (>! out v))
        (when-not (impl/closed? out)
         (recur)))))))
```

# Reuse the same xforms

* No need to reimplement all over.
* Reuse the same xforms! 3 options:
* `a/channel`
* `a/transduce`
* `a/pipeline`

# `a/channel`

```clojure
(defn consumer [xf]
  (let [in (a/chan (a/sliding-buffer 64) xf)]
    (a/go-loop [x (a/<! in)]
      (when x
        (println "consumer received data" x)
        (recur (a/<! in)))) in))

(defn producer [xs & chs]
  (a/go (doseq [x xs ch chs]
      (a/<! (a/timeout 1000))
      (a/>! ch x))))
```

```clojure
(producer
  "hello"
  (consumer (map (comp keyword str)))
  (consumer (map int)))

;; consumer received data 104
;; consumer received data :e
…
```

# a/transduce

* Accepts a channel as input
* Returns a channel with the reduction results

```clojure
(a/<!!
  (a/transduce
    (comp (map inc) (filter odd?))
    + 0
    (a/to-chan (range 10))))
;; 25
```

# a/pipeline

```clojure
(let [out (a/chan (a/buffer 100))]
  (a/pipeline 4 out
    (comp (map inc))
    (a/to-chan (range 10)))
  (a/<!! (a/into [] out)))
;; [1 2 3 4 5 6 7 8 9 10]
```

# Resources

* Communicating Sequential Processes (CSP) [paper](www.usingcsp.com/cspbook.pdf)
* Core.async [walk-through](https://github.com/clojure/core.async/blob/master/examples/walkthrough.clj)
* [Brave Clojure guide](https://www.braveclojure.com/core-async/)

# Lab 03

* Task 1: create channels and orchestrate them so incoming products end up in a local cache.
* Task 2: use the "prepare-data" transducer from `lab01` to transform incoming products.
