(ns transducers-workshop.lab03
  (:require
    [clojure.edn :as edn]
    [transducers-workshop.solutions.lab01 :refer [prepare-data]]
    [clojure.core.async :refer [go go-loop chan >! <! <!! close!]]))

; Welcome to lab3: attaching a transducer chain to a core.async process.

; In this lab we simulate a stream of incoming financial products.
; The products are nested maps of values, lists or other data.
; In lab 1 we developed a transducer to prepare this data for searching.
; The preparation step can be attached directly to the stream of
; incoming (raw) products so we can search them directly.
; We are going to save already prepared products in a local cache.

; The following are functions helpers to be used in the tasks:

(defn load-data [] (edn/read-string (slurp "feed.edn")))
(def cache (atom []))
(def products (load-data))

(defn to-stream
  "Takes a collection and simulate a stream by
  adding items one at a time into a channel.
  The go-loop builds the blocking queue
  waiting for a consumer downstream to pick the items up."
  [items in out]
  (go-loop []
    (when-some [item (<! in)]
      (>! out item)
      (recur)))
  (go
    (doseq [item items]
      (>! in item))))

(defn consume
  "Consume products out of a channel and put them
  in the internal 'atom' cache"
  [out]
  (loop []
    (when-some [item (<!! out)]
      (println "adding to cache")
      (swap! cache conj item)
      (recur))))

; ############ Task 1: create an input and output channel with buffer size 1.

(def in
  ;... complete here
  )
(def out
  ;... complete here
  )

; This is how to start the consumer. Note that "consume" is a blocking
; call, waiting for products to flow through. We detach for the blocking
; thread putting it in a separate thread with "future".
(future (consume out))

; ######### Task 2: stream some products into the input channel.
; The consumer will start consuming from the "future" thread.

; (to-stream
;   ; complete here
;   )

; ########## Task 3: check that something ended up in the cache.

; Are the product in the cache ready for searching or they require preparation?
; Grab the first product and print it on screen. To stop the channels use
; (stop!) on the channel. Reset the cache so you can see new results later.
; To do this, you can just redefine the var.

(println (first "Check the cache here"))

; ############ Task 4: repeat the experience by attaching an xform
; Create the input and output channel. This time be sure to attach the transducer chain in one of them. How does it change if you put the xform in one channel or the other? What it there are many consumers?

(def in
  ;... complete here
  )
(def out
  ;... complete here
  )

; start the consumer in a future as we did before.

; stream a few products in:
; (to-stream
;   ; complete here
;   )

; check the content of the cache again Are the product different?
; remember to close the channels.
