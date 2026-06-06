#lang scheme

;Función con recursion simple
;parametro daemon: función matematica de un argumento 
;parametro puertos: lista de simple de números (puede ser vacía)
;retorno: una nueva lista generada con daemon aplicada a cada elemento, preservando el orden original.
(define (icebreaker-simple daemon puertos)
  (if (null? puertos)
      '() ;Lista vacía (caso V)
      (cons(daemon (car puertos)) ;Si no esta vacía, crea una lista nueva y modifica el primer elemento con daemon (caso F)
      (icebreaker-simple daemon(cdr puertos))))) ;recursion con la funcion pasandole el resto al daemon
(icebreaker-simple daemon puertos)

;Función con recursion de cola
;parametro daemon: función de matemática de un argumento
;parametro puertos lista simple de números (puede ser vacía)
;retorno: una nueva lista generada con daemon aplicada a cada elemento, preservando el orden original
;(Como la lista se va haciendo en reversa hay que usar reverse y asi queda el orden original)
(define (icebreaker-cola daemon puertos)
  (let recorrer ((lista-actual puertos) ; ciclo recorrer y lista con los puertos
   (lista-invertida '())) ;Lista vacía
    (if (null? lista-actual)
        (reverse lista-invertida) ;Reversa para la lista (caso V)
        (recorrer (cdr lista-actual);recursion a ciclo recorrer y le quitamos el primer elemento a la lista  (caso F)                          
        (cons (daemon (car lista-actual)) lista-invertida))))) ;comienza nueva lista y modifica el primer elemento con dameon. devuelve la lista-invertida  
(icebreaker-cola daemon puertos)

