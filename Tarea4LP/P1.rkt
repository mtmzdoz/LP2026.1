#lang scheme



;; 1. Definimos los "puertos" (nuestra lista de estadísticas de ataque)
;; Nota: El apóstrofe ' antes de los paréntesis es la forma rápida en Scheme 
;; de decir "esto es una lista de datos, no intentes ejecutarlo como una función".
(define puertos '(1 2 3))

;; 2. Definimos el "daemon" (nuestra función matemática)
;; Esta función lambda recibe un valor "x" y le suma 5.
(define daemon (lambda (x) (+ x 5)))

;Función con recursion simple
;parametro daemon: función matematica de un argumento 
;parametro puertos: lista de simple de números (puede ser vacía)
;retorno: una nueva lista generada con daemon aplicada a cada elemento, preservando el orden original.

(define (icebreaker-simple daemon puertos)
  (if (null? puertos)
      '() ;;Lista Vacía
      (cons(daemon (car puertos)) ;;Si no esta vacía
      (icebreaker-simple daemon(cdr puertos)))))


(icebreaker-simple daemon puertos)


;Función con recursion de cola
;parametro daemon: función de matemática de un argumento
;parametro puertos lista simple de números (puede ser vacía)
;retorno: una nueva lista generada con daemon aplicada a cada elemento, preservando el orden original
;(Como la lista se va haciendo en reversa hay que usar reverse y asi queda el orden original).

(define (icebreaker-cola daemon puertos)
  (let recorrer ((lista-actual puertos) ;;Lista con los puertos
   (lista-invertida '()))      ;;Lista vacía
    (if (null? lista-actual)
        (reverse lista-invertida) ;;Si V, reversa. Si F, aplicamos daemon.
        (recorrer (cdr lista-actual)                               
        (cons (daemon (car lista-actual)) lista-invertida))))) 
        
(icebreaker-cola daemon puertos)

