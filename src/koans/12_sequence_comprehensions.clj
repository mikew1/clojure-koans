(ns koans.12-sequence-comprehensions
  (:require [koan-engine.core :refer :all]))

(meditations
  "Sequence comprehensions can bind each element in turn to a symbol"
  (= [0 1 2 3 4 5]
     (for [x (range 6)]
       x))

  "They can easily emulate mapping"
  (= '(0 1 4 9 16 25)
     (map (fn [x] (* x x))  ; <-  (map square (range 6)) seems clearest here
          (range 6))
     (for [x (range 6)] ; emulate map by defining the start sequence in the params of for []
       (* x x)))

  "And also filtering"
  (= '(1 3 5 7 9)
     (filter odd? (range 10)) ; <- filter is better in this case
     (for [x (range 10) :when (odd? x)]
       x))

  "Combinations of these transformations is trivial"
  (= '(1 9 25 49 81)
     (map (fn [x] (* x x))
          (filter odd? (range 10)))
     (for [x (range 10) :when (odd? x)]
       (* x x)))

        ; looks easy to build a game board with a seq. comp/list comp/for
        ;  like this (combinatorial with :when to filter if need it; amazing):
  "More complex transformations simply take multiple binding forms"
  (= [[:top :left] [:top :middle] [:top :right]
      [:middle :left] [:middle :middle] [:middle :right]
      [:bottom :left] [:bottom :middle] [:bottom :right]]
     (for [row [:top :middle :bottom]
           column [:left :middle :right]]
       [row column])))
