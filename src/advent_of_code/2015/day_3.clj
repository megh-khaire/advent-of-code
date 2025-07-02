(ns advent-of-code.2015.day-3
  (:require [clojure.java.io :as io]))

;;;; --- Day 1: Not Quite Lisp ---

(def input-file-path "2015/day_3_input.txt")

(def directions (slurp (io/resource input-file-path)))


(defn get-next-house
  [[x y] direction]
  (cond
    (= direction \^) [x (+ y 1)]
    (= direction \v) [x (+ y -1)]
    (= direction \>) [(+ x 1) y]
    (= direction \<) [(+ x -1) y]))


(defn get-all-visited-houses-for-santa
  [directions]
  (reduce (fn [santa direction]
            (conj santa
                  (get-next-house (last santa)
                                  direction)))
          [[0 0]]
          (vec directions)))


(defn get-unique-visited-houses-for-santa
  [directions]
  (-> (get-all-visited-houses-for-santa directions)
      set
      count))


(defn get-all-visited-houses-for-robot-&-santa
  [directions]
  (reduce (fn [{:keys [santa robot] :as houses} direction]
            (if (> (count santa) (count robot))
              (update houses
                      :robot
                      #(conj % (get-next-house (last %) direction)))
              (update houses
                      :santa
                      #(conj % (get-next-house (last %) direction)))))
          {:santa [[0 0]]
           :robot [[0 0]]}
          (vec directions)))


(defn get-unique-visited-houses-for-robot-&-santa
  [directions]
  (let [{:keys [robot santa]} (get-all-visited-houses-for-robot-&-santa directions)
        houses (concat robot santa)]
    (count (set houses))))


(println "Only SANTA: How many houses receive at least one present?"
       (get-unique-visited-houses-for-santa directions))

(println "ROBOT AND SANTA: How many houses receive at least one present?"
       (get-unique-visited-houses-for-robot-&-santa directions))
