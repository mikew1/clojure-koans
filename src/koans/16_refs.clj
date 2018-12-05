(ns koans.16-refs
  (:require [koan-engine.core :refer :all]))

(def the-world (ref "hello"))    ;  refs are about STM on mutable variables.
(def bizarro-world (ref {}))     ; "we'll write most code in functional way, & save this for problems
                                 ;   that could benefit most from mutability" - 7langs p233.

                                 ;  "it may seem painful to modify mutable variables in this way"
                                 ;  "but clojure is enforcing a little policy now to save a lot of pain later"
(meditations
  "In the beginning, there was a word"
  (= "hello" (deref the-world))

  "You can get the word more succinctly, but it's the same"
  (= "hello" @the-world)

  "You can be the change you wish to see in the world."
  (= "better" (do
          (dosync (ref-set the-world "better"))
          @the-world))

  "Alter where you need not replace"   ; looks like 'alter' is not a state mutation, but a new ref?
  (= "better!!!" (let [exclamator (fn [x] (str x "!"))]
          (dosync
           (alter the-world exclamator)   ; try call alter outside of a dosync (transaction) (do it!), and you get
           (alter the-world exclamator)   ;  'IllegalStateException". That's the _WHOLE POINT_, its forbidden.
           (alter the-world exclamator))  ;  fns allowed only in transaction: ensure, ref-set, alter, commute. see docs.
          @the-world))

  "Don't forget to do your work in a transaction!"
  (= 0 (do
         (dosync (alter the-world (fn [_] 0))) ; alter takes a function, which is given the ref as its first arg.
         @the-world))

  "Functions passed to alter may depend on the data in the ref"
  (= 20 (do
          (dosync (alter the-world (fn [x] (+ x 20)))))) ; this is altering global state, in a thread safe way.
                                                         ; (it's that refs are immutabile(?) that's another thing(?))
  "Two worlds are better than one"
  (= ["Real Jerry" "Bizarro Jerry"]
       (do
         (dosync
          (ref-set the-world {})
          (alter the-world assoc :jerry "Real Jerry")         ; hmm... would benefit here from having a real pain point
          (alter bizarro-world assoc :jerry "Bizarro Jerry")  ;        that required concurrency to solve it.
          [(:jerry @the-world) (:jerry @bizarro-world)]))))   ;        without that, none of this impresses as well as it could.
                                                              ;        concurrency book? does that have cool problems in it?...