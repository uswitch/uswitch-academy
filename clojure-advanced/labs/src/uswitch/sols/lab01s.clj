(ns uswitch.sols.lab01s
  (:import [java.io FileInputStream InputStream]))

(defonce counter (atom 0))

(defn fake-s3-list-objects [{:keys [marker]}]
  (swap! counter inc)
  {:object-summaries (range marker (+ 1000 marker))
   :truncated? true
   :next-marker (+ 1000 marker)})

(defn list-objects [req]
  (let [res (fake-s3-list-objects req)]
    (concat
      (:object-summaries res)
      (when (:truncated? res)
        (lazy-seq
          (list-objects (assoc req :marker (:next-marker res))))))))
