(ns logical-interpreter
  (:use [translate])
  (:use [file_manager])
  (:use [function_interpreter]))

(require '[clojure.string :as str])


(defn legal-func "recibo string y digo si puede ser una funcion" 
  ([expr] (legal-func (clojure.string/split expr #"") 0))
  ([[x & xs] count]
    (cond (neg? count) false
          (nil? x) (zero? count)
          (= x "(") (recur xs (inc count))
          (= x ")") (recur xs (dec count))
          :else (recur xs count)))
  )


(defn data-to-condiciones "Agrupa diccionarios segun claves" [mapa listax]
  (let[clave (first listax)
         contenido (into [] (rest listax)) ]
    (if(nil? (mapa clave))
    (merge mapa {clave [contenido]})
    (merge mapa {clave (conj (mapa clave) contenido)} ))))



(defn execute-query "recibe lista de reglas y query a ejecutar y devuelve true false o nil" [base query]
  (if (every? true? [(legal-func query) 
                     (every? true? (reduce conj [] (map #(str/ends-with? %1 ")") base ) )) 
                     (str/ends-with? query ")") 
                     ])
	  (do
	  (def reglas (rule-set base))
	  (def mapa  (reduce data-to-condiciones {} (map translate (data-set base))))
	  
	  (craft-bases mapa)
	  (craft-rules reglas)
	  
	   (let [pars (str "(" (translate-input query) ")") ]
	   (if(eval(read-string pars ))
	     true
	     false     
	     )))
	    nil
  ))

(defn evaluate-query
  "Returns true if the rules and facts in database imply query, false if not. If
  either input can't be parsed, returns nil"
  [database query]
  (if (legal-func database)
    (execute-query (db-to-vec database) query)
    nil
  )
 )


(defn -main [& args]
  (do
    (def lista (file-to-vec "example.txt" ))
    (if (execute-query lista (read-line) ) 
         (println "SI")
         (println "NO")
    )))
