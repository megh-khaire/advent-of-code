(ns advent-of-code.2015.day-4
  (:require [clojure.java.io :as io]
            [clojure.string :as cstr]))

;;;; --- Day 4: The Ideal Stocking Stuffer ---


(def input-file-path "2015/day_4_input.txt")

(def secret-key (cstr/trim (slurp (io/resource input-file-path))))


(defn md5-hash
  [secret-key]
  (let [md5 (java.security.MessageDigest/getInstance "MD5")]
    (->> (.getBytes secret-key java.nio.charset.StandardCharsets/UTF_8)
         (.digest md5)
         (map (partial format "%02x"))
         (apply str))))


(defn has-leading-zeros?
  [num-leading-zeros hash]
  (let [prefix (str (apply str (repeat num-leading-zeros "0")))]
    (cstr/starts-with? hash prefix)))


(defn find-md5-hash-with-leading-zeros
  [secret-key num-leading-zeros hex-prefix]
  (loop [hex-prefix hex-prefix]
    (let [md5-hash (md5-hash (str secret-key hex-prefix))]
      (if (has-leading-zeros? num-leading-zeros md5-hash)
        {:md5-hash md5-hash
         :hex-prefix hex-prefix}
        (recur (inc hex-prefix))))))


(println "Lowest prefix for 5 leading zeros:" (find-md5-hash-with-leading-zeros secret-key 5 1))
(println "Lowest prefix for 6 leading zeros:" (find-md5-hash-with-leading-zeros secret-key 6 1))
