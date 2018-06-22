# ### 4. Specs

# Intro

* Declarative description of code properties
* `[clojure.spec.alpha :as s]` main ns
* `[clojure.spec.test.alpha :as stest])` testing tools
* `org.clojure/test.check` for generative
* `[clojure.spec.gen.alpha :as gen]`
* Usages: validation, documentation, testing, parsing

# Vocabulary

* rich APIs, it takes some time to get fluent
* but it's relatively easy to learn
* Building blocks: `s/def`, `s/fdef`, `s/cat`, `s/keys`…
* Interaction: `s/valid?`, `s/conform`, `s/explain`…
* Expressiveness: `s/and`, `s/or`, `s/*` …

# Process

Mostly personal taste but:

* Sketch out concepts from the bottom
* Compose them up into abstractions
* Spec-out public facing functions (or endpoints)
* Have some generative testing (optional)

# Example

* A "powerset" is the set of all subsets of a set.
* There are `2^n` subsets of `[0 1 2]` (2^3 = 8)
* ((0 1 2) (2) (0) (1) (1 2) (0 1) (2 0) ())
* We could write a lazy version for that

```clojure
(defn powerset [xs]
  (letfn [(rotate [xs]
            (map rest (iterate #(concat (rest %) (list (first %))) xs)))
          (step [n xs]
            (when-let [rs (seq (take n (rotate xs)))]
              (when (ffirst rs)
                (into (mapcat step (range (dec n) 0 -1) rs) rs))))]
    (keep seq (conj (step (count xs) xs) xs))))

(powerset [0 1 2]) ;; okay
;; ((0 1 2) (0 1) (2 0) (1 2) (1) (2) (0))

(powerset [2 2 2]) ;; we can delegate distinct to the caller
;; ((2 2 2) (2 2) (2 2) (2 2) (2) (2) (2))
```

# Properties

* We could write many examples of calls to `powerset`
* Or capture the essence of a powerset into a spec
* Including the need for the input to be `distinct`

```clojure
(require '[clojure.spec.alpha :as s]
         '[clojure.spec.test.alpha :as stest])

(s/fdef powerset
  :args (s/cat :xs (s/coll-of int? :distinct true))
  :ret seqable?
  :fn (fn [{:keys [args ret]}]
        (and (= (Math/pow 2 (count (:xs args))) (count ret))
             (= (count ret) (count (distinct ret))))))
```

# Generative Testing

* We have the properties for the function
* We can generate as much examples as we want

```clojure
(s/exercise-fn `powerset)
;; wall of numbers scrolling indefinitely
```

* We didn't say "xs" can't be millions of items...

# Limited Range

* We can create our own "type" as a new spec.
* A list of distinct integers up to 20 in size

```clojure
(s/def ::limited-range
  (s/coll-of int? :max-count 20 :distinct true))
```

* Then use it to specify the argument.

# Lab 04

* I wrote a "fizz-buzz" and I tested it!
* I'm 100% sure it works, even with negatives!
* Can you write a spec for it and prove me wrong?
* Be sure to not over specify it
* Your spec should not contain the implementation
* Search for general facts.
* For example: for any given "n" how many distinct strings?
