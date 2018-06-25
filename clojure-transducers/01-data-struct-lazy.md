# ### 1. The Basics

# What are transducers?

* A model for sequential processing
* An abstraction on top of `reduce`
* A functional pattern
* A computational recipe

# What are they not?

* A library
* A replacement for sequences
* A performance optimization (not only)
* Reducers (but they share a similar design)

# Quick compare

```clojure

(reduce +                    (transduce
  (filter odd?                 (comp
    (map inc                     (map inc)
      (range 10))))             (filter odd?))
                              +  (range 10))
```

# More compare

```clojure

(->>                        (transduce
  (range 10)                  (comp
  (map inc)                      (map inc)
  (filter odd?)                 (filter odd?))
  (reduce +))                 +  (range 10))
```

# Clear differences

```
**Sequential**                    **Transducers**

* No `comp`                       `comp` (remove nesting)
* Nested calls                    `transduce` instead of `reduce`
* Using `reduce`                  Transforms are "grouped"
```

# Subtle differences

```
**Sequential**                    **Transducers**
* n-intermediate sequences        * No intermediate sequences
* Isolated Transforms             * Composed Transforms
* n-sequential scans              * `transduce` uses `reduce`
* Transform on sequential scan    * Transform evaluates lazily
```

# Why do we care?

* Transforms are isolated from sequential mechanism
* Transforms are more composable/reusable
* Single pass iteration boost performances
* Sequential iteration is someone else's responsibility

# Always possible?

* Some transforms aren't immediate to translate:

```clojure
(->> [[0 1 2] [3 4 5] [6 7 8]] (apply map vector))
```

* Scenarios involving infinite laziness:

```clojure
(take 3 (sequence (mapcat repeat) [1])) ;; boom!
```

* Realising intermediate results unnecessarily:

```clojure
(first
  (sequence
    (comp (mapcat range) (mapcat range))
  [3000 6000 9000])) ;; boom!
```

# Always faster?

* Avoid on small collections
* Avoid for just few transforms
* Avoid for trivial transforms

**When in doubt, measure**

# Main API

* `transduce`: eager, single pass. All input evaluated.
* `sequence`: delayed, chunked (32), cached.
* `eduction`: delayed, chunked (32) no caching.
* `into`: eager. `transduce` "into" another data type.

# Current line-up

```
mapcat, remove, take, take-while, take-nth
drop, drop-while, replace, partition-by, halt-when
partition-all, keep, keep-indexed, map-indexed
distinct, interpose, dedupe, random-sample, cat
```

# Resources

* [Transducers presentation](https://www.youtube.com/watch?v=6mTbuzafcII) by Rich
* Transducers official [reference guide](https://clojure.org/reference/transducers)
* Article about the Transducers [functional abstraction](https://labs.uswitch.com/transducers-from-the-ground-up-the-essence/)

# Labs Intro

```bash
git clone https://github.com/uswitch/uswitch-academy.git
cd clojure-transducers
lein repl
```

It is assumed you can evaluate the code in the labs with your favourite IDE.

# Driving Example

* App receiving regular updates of fin products
* Users can search for the best product.
* Each update contains +10k products as Clojure maps.
* We want to process the data in a modular/timely manner.
* Open `src/transducers_workshop/lab01.clj` to get started
