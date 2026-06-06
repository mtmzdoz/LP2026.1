#lang scheme

;parametro vulnerabilidad: una funcion lambda que recibe un numero y retorna #t si es vulnerable o #f si esta protegido
;retorno: una funcion lambda que aplica la vulnerabilidad, elimina nodos protegidos y ve nodos sublistas
(define (crear-gusano vulnerabilidad)
  (define (recorrer nodo)
    (cond
      [(null? nodo)  '()] ;Lista vacía? (caso 1)
      [(number? nodo) ;El nodo es un número? (caso 2)
       (if (vulnerabilidad nodo)
           nodo ;#t (caso V)
           'X)] ;#f (caso F)
      [else (cons (recorrer (car nodo)) ;El nodo es una lista, toma el primer elemento y recursion para leerlo  (caso else)
            (recorrer (cdr nodo)))])) recorrer) ;toma el resto de la lista y  usa rec. devuelve la funcion recorrer


