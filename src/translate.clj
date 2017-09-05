(ns translate)
(require '[clojure.string :as str])

(defn translate "parseo datos" [frase]
(str/split  (str/replace (str/replace frase #" " "") #"[^a-zA-Z0-9]" " ") #" "))

(defn db-to-vec "Leo base de datos y devuelvo lista" [database]  
  (str/split  (str/replace (str/replace database #" " "") #"[^a-zA-Z0-9.(),:-]" "") #"\."))

(defn translate-input "parseo input" [frase]
(str/replace
  (str/replace  
    (str/replace  
      (str/replace frase #" " "") 
      #"[(]" " \\\"" )
    #"[,]" "\\\" \\\"")
  #"[)]" "\\\"" )
)

(defn translate-opts "parseo opts" [frase]
(str/join ["(" (str/replace
  (str/replace  
    (str/replace  
      (str/replace frase #" " "") 
      #"[(]" " " )
    #"[,]" " ")
  #"[)]" "" )
   ") "]       
)
)

(defn translate-rule-options "parseo las opciones que debe cumplir la regla" [frase]
  (str/join
  (into []
  (map #( translate-opts %1 )
    (str/split 
      (str/replace 
        (subs frase (+ (.indexOf frase "-") 1) ) 
        #"\)," ") ")
      #" ")  )  )  )  )


(defn translate-rule-name "parseo nombre de reglas" [frase]
   (str/replace
   (str/replace
     (str/replace
     (str/replace 
      (subs frase 0 (+ (.indexOf frase ")") 1) ) 
      #"[(]" " [" )
     #"[)]" "]")
    #"[)]" "]")
   #"," " ")             
  )