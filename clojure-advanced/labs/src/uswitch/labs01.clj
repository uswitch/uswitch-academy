(ns uswitch.labs01
  (:import [java.io FileInputStream InputStream]))

(defonce counter (atom 0))

(defn fake-s3-list-objects [{:keys [marker]}]
  (swap! counter inc)
  {:object-summaries (range marker (+ 1000 marker))
   :truncated? true
   :next-marker (+ 1000 marker)})

(defn list-objects [req]
  ;; fetch objects
  ;; create a collection out of the :object-summaries plus...
  ;; ... the rest of objects (when available) that you obtain
  ;; by calling again fetch-objects recursively. To know if there are more
  ;; check the (:truncated? res) key in the response.
  ;; Next batch can be called by setting the marker in the next
  ;; request: (assoc req :marker (:next-marker res))
  (let [res (fake-s3-list-objects req)]
    ))
