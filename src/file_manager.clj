(ns file_manager)

(require '[clojure.string :as str])

(defn file-to-vec "Leo archivo y devuelvo lista" [archivo]  
  (str/split (str/replace (str/replace (slurp archivo) #"\." "") #" " "")  #"\r\n"))

(defn rule-set "filtro de lista por reglas" [lista]
  (filter #(str/includes? %  "-") lista))

(defn data-set "filtro de lista por declaraciones" [lista]
  (filter #(not (str/includes? %  "-")) lista))