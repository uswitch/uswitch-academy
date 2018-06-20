# ### 3. Macros

# Why macros?

* Expanding the language possibilities
* Loads of examples in the stdlib: `->>`, `for`, `definline`, `lazy-seq`, etc etc.
* Avoid repetition, for example generating lots of similar functions
* Setup/teardown behaviour (e.g. `with-open`)
* DSLs and small compilers (e.g. `for` has a "dsl" and related compiler)

# Home-made macro

```clojure
(defn like-a-macro [[op & args :as form]]
(println (type op))
  (if (fn? op) (apply op args) form))

(like-a-macro (list + 1 1)) ;; 2
(like-a-macro (list :a :b :c)) ;; (:a :b :c)
```

* Can't really write programs as lists... but what if
* Special fn taking unevaluated forms as arguments
* Compiler uses fn output instead of actual form
* This is what macros are for.
* Access to full power manipulation of sources!

# A real macro

```clojure
(defmacro is-a-macro [[op & args :as form]]
  (if-let [f (find-var (symbol (str "clojure.core/" op)))]
    (cons f args)
    (cons 'list form)))

(is-a-macro (+ 1 1)) ;; 2
(is-a-macro (:a :b :c)) ;; (:a :b :c)
```

* We had to "qualify" and lookup the symbol
* The input is now a list of unevaluated symbols
* It needs to return a new form (list)
* The compiler take that "in place" of original call.
* `defmacro` is itself a macro built on top of `defn`

# Help please

Syntax quote (the back tick) allows for:

* Auto-qualification of symbols
* Unquote `~` evaluated context
* Unquote-splicing unquote all items in a collection
* Auto-gensym prevents accidental override of symbols
* `&form` contains the surrounding form
* `&env` contains the local bindings

# Syntax quote

* Like `quote` prevents evaluation
* Plus all mentioned goodies

```clojure
`(1 2 3)
;; (1 2 3)

(= `(1 2 3) '(1 2 3))
;; true
```

# Auto-qualification

```clojure
(require '[clojure.string :as s :refer [lower-case])

`s/upper-case
;; clojure.string/upper-case

`lower-case
;; clojure.string/lower-case

`foo
;; user/foo
```

# Unquote and splicing

```clojure
`[1 2 (+ 1 2)  ~(+ 1 2)]
;; [1 2 (clojure.core/+ 1 2) 3]

`[1 2 ~[3 4] ~@[3 (+ 1 2)]]
;; [1 2 [3 4] 3 3]
```

* Used to mix between the macro expansion and evaluation context
* Splicing works similarly to `apply` to spread arguments

# Accidental capturing

```clojure
(defmacro ** [a b]
  `(let [~'y (* ~a ~a)]
    (* ~'y ~b)))

(macroexpand '(** a b))
;; (let [y (* a a)] (* y b))

(let [x 2 y 5] (** x y))
;; 16
```

* `~'` (tilde single-quote) expands into the actual symbol.
* Equivalent to `~(quote y)`
* Which is the "evaluation of quote y"
* Or y itself.

# Auto-gensym

```clojure
(defmacro ** [a b]
  `(let [y# (* ~a ~a)]
    (* y# ~b)))

(macroexpand '(** a b))
;; (let [y__2270 (* a a)] (* y__2270 b))

(let [x 2 y 5] (** x y))
;; 20
```

# &form

```clojure
(defmacro just-print-me [& args] (println &form)

(just-print-me foo :bar 123)
;; (just-print-me foo :bar 123)
```

* `&form` captures the actual macro call as data
* Useful to inspect metadata (like type hints)

# &env

```clojure
(defmacro with-negatives [& body]
  (let [pos (vec (keys &env))
        neg (mapv #(symbol (str % "'")) pos)
        values (mapv #(.eval (.init %)) (vals &env))]
    `(let [~pos ~values
           ~neg (mapv - ~values)]
       ~@body)))

(let [x 1 y 2]
  (with-negatives [[x y] [x' y']]))
;; [[1 2] [-1 -2]]
```

* Useful for pre-processing of local bindings
* For example generating additional ones
* x' and y' are generated from the originals

# Lab 03

* A parallel `let`
* Easier than it sounds
