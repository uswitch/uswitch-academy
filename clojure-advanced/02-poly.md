# 2. Polymorphism

# Polymorphism in Clojure

* Emphasis on functional dispatch
* Emphasis on flexibility
* Less interested in inheritance
* Less interested in subtyping
* Less interested in type based dispatch

# Available options

* Map lookup (straightforward, centralised)
* Namespace lookup (allow same names)
* Multimethods (flexible)
* Protocols (fast)

# Map lookup

```clojure
(defn process-bg   [instr] (println "bg"))
(defn process-edf  [instr] (println "edf"))
(defn process-bulb [instr] (println "bulb"))

(def vendors
  {:bg    process-bg
   :edf   process-edf
   :bulb  process-bulb})

(defn handle-request [instr]
  ((vendors (keyword (:vendor instr))) instr))

(handle-request {:date "today" :vendor "edf"})
;; edf
```

# Namespace lookup

```clojure
(in-ns 'bg)
(clojure.core/defn process [instr] (clojure.core/println "bg"))
(in-ns 'edf)
(clojure.core/defn process [instr] (clojure.core/println "edf"))
(in-ns 'bulb)
(clojure.core/defn process [instr] (clojure.core/println "bulb"))

(in-ns 'user)

(defn handle-request [instr]
  (when-let [f (find-var (symbol (str (:vendor instr) "/process")))]
    (@f instr)))

(handle-request {:date "today" :vendor "edf"})
;; edf
```

# Multimethods

```clojure
(defmulti process (comp keyword :vendor))
(defmethod process :bg   [instr] (println "bg"))
(defmethod process :edf  [instr] (println "edf"))
(defmethod process :bulb [instr] (println "bulb"))

(process {:date "today" :vendor "edf"})
;; edf
```

# Hierarchical multimethods

```clojure
(defmulti process (comp keyword #(str "user/" %) :vendor))
(defmethod process ::junifer [instr] (println "junifer"))
(defmethod process ::bulb    [instr] (println "bulb"))

(derive ::edf ::junifer)
(derive ::bg ::junifer)

(process {:date "today" :vendor "edf"})
;; junifer
```

# isa? relationships

* `(isa? ::bg ::junifer)` => `true`
* `(parents ::bg)` => `#{:user/junifer}`
* `(descendants ::junifer)` => `#{:user/edf :user/bg}`
* Store in global `@#'clojure.core/global-hierarchy`
* You can pass your own as a param.

# Protocols

```clojure
(defprotocol Vendor
  (process [vendor instr])
  (dispatch [vendor instr]))

(defrecord Edf [live? endpoint]
  Vendor
  (process [vendor instr] (when live? (println "process edf")))
  (dispatch [vendor instr] (println "sending to" endpoint)))

(defn lookup-vendor [instr]
  (let [initf (find-var (symbol (str "user/->" (:vendor instr))))]
    (initf true "http")))

(let [instr {:date "today" :vendor "Edf"}]
  (process (lookup-vendor instr) instr)) ;; prints "process edf"
```

# Adding behaviour

```clojure
(defrecord Bg [live? endpoint]
  Vendor
  (process [vendor instr] (when live? (println "process bg")))
  (dispatch [vendor instr] (println "sending to" endpoint)))

(let [instr {:date "today" :vendor "Bg"}]
  (dispatch (lookup-vendor instr) instr))
;; "sending to http"
```

# Sharing behaviour

```clojure
(defrecord Bg  [live? endpoint])

(def vendor-common
  {:dispatch (fn [vendor instr]
    (println "sending via" (:endpoint vendor)))})

(extend Bg
  Vendor
  (assoc vendor-common
    :process (fn [vendor instr]
      (when (:live? vendor) (println "process bg")))))

(let [instr {:date "today" :vendor "Bg"}]
  (dispatch (lookup-vendor instr) instr))
;; "sending via http"
```

# Gotchas

* Multimethods and Protocols need explicit "require"
* Using them from other namespaces can be tricky
* Protocol functions can clash with local functions

# Lab 02

* Extend our system to work with a new vendor
