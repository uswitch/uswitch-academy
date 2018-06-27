# ### 4. Going parallel

# Parallelism

* Present in Clojure in different forms:
* `pmap` (lazy, sequential, constrained)
* `r/fold` (work-stealing, fork-join)
* Custom (`future`, `agent`, etc.)

# Parallel transducers

* Transducers could run in parallel for additional perf.
* Approach 1. Divide and conquer: split the input and work in parallel
* Approach 2. `core.async` pipelines

# r/fold

* `clojure.core.reducers` is part of Clojure
* fork-join is a divide and conquer strategy with work-stealing
* The input can be split into chunks (so no laziness)
* Combining the chunks back must be commutative

#

![](images/fork-join.png)

# How

* Transducers are f: rf -> rf
* Call with "+" to obtain the transformed rf
* Use transformed rf in `r/fold` as usual
* To go parallel, you need vector/map
* Or possibly your custom `IFold`
* Works only with stateless transducers

# Example

```clojure
(require '[clojure.core.reducers :as r])

(r/fold +
  ((comp
    (map inc) (filter odd?)) +)
  (vec (range 1000)))
;; 250000
```

# Stateful problem

```clojure
(distinct (for [i (range 1000)]
  (r/fold +
    ((comp
      (map inc) (drop 1) (filter odd?)) +)
    (vec (range 1000)))))
;; (249999 249498 249499)
```

* Results are inconsistent
* Depending on which thread runs `(drop 1)`
* You get different results each run

# Pipelines

* `core.async` provides a `pipeline` construct
* `pipeline` can be further "piped" together
* Each `pipeline` declares the parallelism degree
* Each `pipeline` can apply a different transducer

# Example

```clojure
(a/pipeline
   (inc (.availableProcessors (Runtime/getRuntime)))
   (a/chan out)
   (comp (map inc) (filter odd?))
   (a/to-chan (range 1000)))
```

* "availableProcessor" is the number of parallel threads
* Adding 1 or 2 threads to keep it buys
* Use in/out channels to pipe more of them together

# Resources

* The [parallel](https://github.com/reborg/parallel#pfold-pxrf-and-pfolder) library enables consistent stateful xforms in parallel.
* [A Java fork-join framework](gee.cs.oswego.edu/dl/papers/fj.pdf) paper by Doug Lea
* [Clojure Applied](https://pragprog.com/book/vmclojeco/clojure-applied) book contains chapters dedicated to Transducers with core.async pipelines examples.
* [Standard Library book](https://livebook.manning.com/#!/book/clojure-standard-library/chapter-7), Chapter 7 Reducers and Transducers

# Lab 04

* Task1: parallelise the xform with reducers.
* Task2: parallelise the xform with pipelines.
* Task3: different pipelines for different transducers.
