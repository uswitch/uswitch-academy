(ns uswitch.labs01
  (:import [java.io FileInputStream InputStream]))

(defn f1 [& args]
  (println "Hello, World!"))



(defn byte-seq [^InputStream is size]
  (let [ib (byte-array size)]
    ((fn step []
       (lazy-seq
         (let [n (.read is ib)]
           (when (not= -1 n)
             (let [cb (chunk-buffer size)]
               (dotimes [i size] (chunk-append cb (aget ib i)))
               (chunk-cons (chunk cb) (step))))))))))

;; Example with a text file and block size 4096.
(with-open [is (FileInputStream. "/usr/share/dict/words")]
  (let [bs (byte-seq is 4096)]
    (String. (byte-array (take 20 bs)))))
;; "A\na\naa\naal\naalii\naam"
