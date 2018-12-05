(ns koans.15-destructuring
  (:require [koan-engine.core :refer :all]))

(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(meditations
  "Destructuring is an arbiter: it breaks up arguments"
  (= ":bar:foo" ((fn [[a b]] (str b a))
         [:foo :bar]))

  "Whether in function definitions"
  (= (str "An Oxford comma list of apples, "
          "oranges, "
          "and pears.")                                               ;or just  (list a b)
     ((fn [[a b c]] (str "An Oxford comma list of " (apply str (interpose ", " `(~a ~b))) ", and " c "."))
      ["apples" "oranges" "pears"]))

  "Or in let expressions"
  (= "Rich Hickey aka The Clojurer aka Go Time aka Lambda Guru"
   (let [[first-name last-name & aliases]
         (list "Rich" "Hickey" "The Clojurer" "Go Time" "Lambda Guru")]
     (str first-name " " last-name " aka " (apply str (interpose " aka " aliases))))) ; str on contents of list.

  "You can regain the full argument if you like arguing"
  (= {:original-parts ["Stephen" "Hawking"] :named-parts {:first "Stephen" :last "Hawking"}}
     (let [[first-name last-name :as full-name] ["Stephen" "Hawking"]]
       { :original-parts [first-name last-name] :named-parts { :first first-name :last last-name } }))

  "Break up maps by key"
  (= "123 Test Lane, Testerville, TX"
     (let [{street-address :street-address, city :city, state :state} test-address]
       (str street-address ", " city ", " state)))

  "Or more succinctly"
  (= "123 Test Lane, Testerville, TX"
     (let [{:keys [street-address city state]} test-address]
       (str street-address ", " city ", " state)))

  "All together now!"
  (= "Test Testerson, 123 Test Lane, Testerville, TX" ; this one took a lot more code than have needed up to now...
     ((fn [[first-name last-name]                  ; destructure first param  (power -do it in args, not let)
           {:keys [street-address city state]}]    ; destructure second param (power - do it in args, not let)
      (str first-name " " last-name ", " street-address ", " city ", " state))   ; use the destructured params
     ["Test" "Testerson"]  ; first param          ; initially did destructuring in a let, which was much more verbose...
     test-address))        ; second param         ; ... just note ability to see that params will need destructuring,
                                                  ;     so do it immediately, just skip completely giving any name to the param as a whole
)

