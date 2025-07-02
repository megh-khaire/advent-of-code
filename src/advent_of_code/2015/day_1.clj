(ns advent-of-code.2015.day-1
  (:require [clojure.java.io :as io]))

;;;; --- Day 1: Not Quite Lisp ---

(def input-file-path "2015/day_1_input.txt")

(def building (slurp (io/resource input-file-path)))


(defn move-to-next-floor
  [current-floor next-floor]
  (+ current-floor
     (cond
       (= next-floor \() 1
       (= next-floor \)) -1
       :else 0)))


(defn deliver-presents
  [floors]
  (reduce (fn [{:keys [current-floor floors-moved] :as santa}
               next-floor]
            (let [current-floor (move-to-next-floor current-floor next-floor)]
              (if (= current-floor -1)
                (reduced (inc floors-moved))
                (assoc santa
                       :current-floor current-floor
                       :floors-moved (inc floors-moved)))))
          {:current-floor 0
           :floors-moved 0}
          (vec floors)))


(println
 "What is the position of the character that causes Santa to first enter the basement?"
 (deliver-presents building))
