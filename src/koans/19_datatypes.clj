(ns koans.19-datatypes
  (:require [koan-engine.core :refer :all]))
                            ; defstruct is seen in sierpinski,
                            ; that's obsolete, replaced with defrecord.
                            ; what are these anyway

(defrecord Nobel [prize])   ; looks like classname, property
(deftype Pulitzer [prize])

(defprotocol Award
  (present [this recipient]))

(defrecord Oscar [category]
  Award
  (present [this recipient]
    (print (str "Congratulations on your "
                (:category this) " Oscar, "  ; with defrecord, its keyword :category
                recipient
                "!"))))

(deftype Razzie [category]
  Award
  (present [this recipient]
    (print (str "You're really the "
                (.category this) ", "        ; with type, need to do .category
                recipient
                "... sorry."))))

(meditations
  "Holding records is meaningful only when the record is worthy of you"
  (= "peace" (.prize (Nobel. "peace")))  ; (Nobel. "peace") macroexpands to (new Nobel "peace")
                                         ; so, .prize looks like it just gets the property
  "Types are quite similar"
  (= "literature" (.prize (Pulitzer. "literature")))   ; DOT AFTER IS CONSTRUCTOR

  "Records may be treated like maps"
  (= "physics" (:prize (Nobel. "physics")))

  "While types may not"
  (= nil (:prize (Pulitzer. "poetry")))

  "Further study reveals why"
  (= [true false]
     (map map? [(Nobel. "chemistry")
                (Pulitzer. "music")]))

  "Either sort of datatype can define methods in a protocol"
  (= "Congratulations on your Best Picture Oscar, Evil Alien Conquerors!"
     (with-out-str (present (Oscar. "Best Picture") "Evil Alien Conquerors")))

  "Surely we can implement our own by now"
  (= "You're really the Worst Picture, Final Destination 5... sorry."
     (with-out-str (present (Razzie. "Worst Picture") "Final Destination 5"))))
