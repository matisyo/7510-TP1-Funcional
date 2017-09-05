(ns function_interpreter
  (:use [translate]))
(require '[clojure.string :as str])

(defn crear-fun-base "Creo una funcion base" [name lista]
  (let [fun-string ["(defn " name "[& args] (some true? (map #(= args (seq %1)) [" ]
        item-string (str/join " " lista)
        fun-comp    (str/join [ (str/join fun-string) item-string "]) ))"] )
        ]
     (eval(read-string fun-comp))
  ))

(defn crear-fun-opt "Creo funciones compuestas de funcoines bases o funciones compuestas. " [name condiciones]
  (let [fun-string ["(defn " name " (not (some nil? [" condiciones "])))"]]
    (eval(read-string (str/join fun-string)))
    ))

(defn craft-bases "Crea todas las funciones base" [mapa]
  
  (doall  (for [x (into [] (keys mapa))] (crear-fun-base x (mapa x) )))
  
  )

(defn craft-rules "Crea todas las reglas" [reglas]
  
      (doall (for [x reglas] 
             (crear-fun-opt (translate-rule-name x)
                            (translate-rule-options x)  ) ))
  
  )