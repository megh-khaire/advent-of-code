(ns advent-of-code.2015.day-2
  (:require [clojure.java.io :as io]
            [clojure.string :as cstr]))

;;;; --- Day 2: I Was Told There Would Be No Math ---

(def input-file-path "2015/day_2_input.txt")

(def text (slurp (io/resource input-file-path)))

(def dimensions (cstr/split-lines text))


(defn calculate-smallest-side-area
  [a b c]
  (cond
    (and (<= a b) (<= c b)) (* a c)
    (and (<= a c) (<= b c)) (* a b)
    (and (<= b a) (<= c a)) (* b c)))


(defn calculate-wrapping-paper-for-present
  [dimension]
  (let [[a* b* c*] (cstr/split dimension #"x")
        a (parse-long a*)
        b (parse-long b*)
        c (parse-long c*)]
    (+ (* 2 a b)
       (* 2 a c)
       (* 2 b c)
       (calculate-smallest-side-area a b c))))


(defn calculate-total-wrapping-paper
  [dimensions]
  (reduce (fn [total-paper dimension]
            (+ total-paper
               (calculate-wrapping-paper-for-present dimension)))
          0
          dimensions))


(defn calculate-smallest-face-perimeter
  [a b c]
  (cond
    (and (<= a b) (<= c b)) (* 2 (+ a c))
    (and (<= a c) (<= b c)) (* 2 (+ a b))
    (and (<= b a) (<= c a)) (* 2 (+ b c))))


(defn calculate-ribbon-for-present
  [dimension]
  (let [[a* b* c*] (cstr/split dimension #"x")
        a (parse-long a*)
        b (parse-long b*)
        c (parse-long c*)]
    (+ (* a b c)
       (calculate-smallest-face-perimeter a b c))))


(defn calculate-total-ribbon
  [dimensions]
  (reduce (fn [total-ribbon dimension]
            (+ total-ribbon
               (calculate-ribbon-for-present dimension)))
          0
          dimensions))

(println "How many total square feet of wrapping paper should they order?"
       (calculate-total-wrapping-paper dimensions))

(println "How many total feet of ribbon should they order?"
       (calculate-total-ribbon dimensions))
