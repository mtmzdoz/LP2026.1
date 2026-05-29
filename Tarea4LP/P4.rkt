#lang scheme

(define (hash-desconexion token)
  ;; Paso 4: Plegamos (sumamos) toda la lista numérica final, empezando en 0.
  (foldl + 0
         
         ;; Paso 3: Mapeamos la lista filtrada convirtiendo cada letra a su ASCII.
         (map char->integer
              
              ;; Paso 2: Filtramos la lista dejando únicamente las letras.
              (filter char-alphabetic?
                      
                      ;; Paso 1: Convertimos el texto inicial a una lista de caracteres.
                      (string->list token)))))


;; Ejemplo 1: Token con letras, símbolos y números.
(hash-desconexion "N!u1lS#e*c")
;; R: 586

;; Ejemplo 2: Token puramente corrupto (sin letras).
(hash-desconexion "12345!@#$")
;; R: 0

;; Ejemplo 3: Un token relativamente limpio.
(hash-desconexion "A-B-C")
;; R: 198