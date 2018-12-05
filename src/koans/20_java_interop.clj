(ns koans.20-java-interop
  (:require [koan-engine.core :refer :all]))

(meditations
  "You may have done more with Java than you know"
  (= String (class "warfare")) ; hint: try typing (javadoc "warfare") in the REPL
                               ; String is a symbol (class String) (class Class)

  "The dot signifies easy and direct Java interoperation"
  (= "SELECT * FROM" (.toUpperCase "select * from")) ; to do this, clojure first reflects
                                                     ; the first arg, then calls the method
                                                     ; on that class... instance.
                                                     ; i.e. call .toUpperCase on what.
                                                     ; .toUpperCase is not really a clj fn.
                                                     ; it's being treated specially by clj.
                ; How to map with a java method:
                ; could map with clojure.string/upper-case,
                ; but just passing anon fn with one param 'does the trick' (< vid)
                ; (clj converts it; .toUpperCase alone won't work)
                ; 'simple way to *wrap* this call to a java method'
  "But instance method calls are very different from normal functions"
  (= ["SELECT" "FROM" "WHERE"] (map #(.toUpperCase %) ["select" "from" "where"]))

  "Constructing might be harder than breaking"
  (= 10 (let [latch (java.util.concurrent.CountDownLatch. 10)]  ; constructor is with dot at end
          (.getCount latch)))

  "Static methods are slashing prices!"
  (== 1024 (Math/pow 2 10)))
