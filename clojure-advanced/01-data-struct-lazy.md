# 1. Lazy Sequences

# Laziness

```clojure

 (println "hi")  ;; evaluates immediately
 ;; hi

#(println "hi")  ;; needs invocation
;; #object[user$eval1843$fn__1844 0x9036860]

```
* Deferred code evaluation
* Code evaluates when requested

# Why do I care?

Performances and expressiveness:

* Consume data beyond memory capacity
* Avoid unnecessary evaluations
* Leverage caching
* Work with infinite sequences
* Detach producers/consumers

# How?

* Everything needs wrapping in `#()`
* Without language support it would look like:

```clojure

(defn lazy-list [coll]
  (map
    (fn [x]
      (fn [] (println "eval" x) x)) ;; need wrapping
    coll))

(def l (lazy-list [1 2 3]))

((first l)) ;; need invocation
;; eval 1
```

Clojure has that baked directly into Sequences

# Sequences

* Abstract Data Type (or ADT)
* Iterated sequentially (can't access Nth before Nth-1)
* Stateless cursor: no shared callers, only forward
* Commonly (not necessarily) lazy
* Persistent and immutable

# Generators options

![](images/01-sequence-generators.png)

# Some Examples

```clojure
;; No hanging
(def a (map inc (range 100000000000)))

;; Self referential
(def fibs
  (cons 0
    (cons 1
      (lazy-seq (map +' fibs (rest fibs))))))

;; Moving window memory load
(require '[clojure.java.io :refer [reader]])
(with-open [r (reader "/huge/petabytes/file")]
  (count (line-seq r)))
```

# Traps and gotchas

* Holding the head

```clojure
(let [res (map inc (range 1e7))] (first res) (last res))
;; 10000000
(let [res (map inc (range 1e7))] (last res) (first res))
;; Out of mem
```

* Chunkiness

```clojure
(first (map #(do (print ".") %) (range 1000)))
;; ................................0
```

# Taking control

* Create your own lazy sequence
* Optionally define chunkiness
* Your friends: `lazy-seq`, `cons`, `chunk-cons`

# Recursive Pattern

![](images/01-lazy-seq-pattern-diagram.png)

# Unchunk Example

* A lazy sequence generator using the pattern
* Apparently doing nothing, but it removes chunking:

```clojure
(defn unchunk [xs]
  (lazy-seq
    (when-first [x xs]
      (cons x (unchunk (rest xs))))))

(first (map #(do (print ".") %) (unchunk (range 1000))))
;; .0
```

# S3 Examples

```clojure
(defn fake-s3-list-objects [{:keys [marker]}]
  (println "calling s3")
  {:object-summaries (range marker (+ 1000 marker))
   :truncated? true :next-marker (+ 1000 marker)})

(defn list-objects [req]
  (let [res (fake-s3-list-objects req)]
    (concat
      (:object-summaries res)
      (when (:truncated? res)
        (lazy-seq
          (list-objects (assoc req :marker (:next-marker res))))))))

(last (take 10000 (list-objects {:marker 0})))
```
(prints "calling s3" x10 times, then 9999)
