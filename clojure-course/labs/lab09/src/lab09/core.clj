(ns lab09.core)

(defprotocol JSON
  (to-json [this]))

(extend-protocol JSON

  java.lang.Long
  (to-json [this]
    (str this))

  java.lang.String
  (to-json [this]
    (str "\"" this "\""))

  nil
  (to-json [this]
    "null")))

(defmulti fill
  "Fill a xml/html node with the provided value."
  (fn [node value] (:tag node)))

(defmethod fill :div
  [node value]
  (assoc node :content [(str value)]))
(defmethod fill :input
  [node value]
  (assoc-in node [:attrs :value] (str value)))
(defmethod fill :default
  [node value]
  (assoc node :content [(str value)]))
