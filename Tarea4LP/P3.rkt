#lang scheme

;Funcion aux para aplicar daemon
;parametro numero: numero al que se le aplicara daemon
;parametro daemons: lista de funciones matematicas de un arg
;retorno: el numero final tras aplicarle los daemons
(define (aplicar-daemon numero daemons)
    (cond
      [(null? daemons) numero];Lista vacia, se devuelve el número modificado (caso 1)
      [else (aplicar-daemon ((car daemons) numero);;modifica el primer elemento con daemon, recursion con la lista restante  
                             (cdr daemons))]))

;parametro mapa: arbol n-ario que contiene numeros y 'X
;parametro daemons: lista de funciones unarias (expresiones lambda). Representa un ataque secuencial 
;retorno: una nueva lista generada con daemons aplicada a cada elemento y sin 'X, preservando el orden original.
(define (ejecutor-cascada mapa daemons)
  (define (recorrer lista)
    (cond
      [(null? lista) '()] ;Lista vacia (caso 1)
      
      [(list? (car lista)) ;Si hay sublista se mantiene la estructura usando cons (caso 2)
       (cons (recorrer (car lista)) ;para recorrer la sublista
             (recorrer (cdr lista)))] ;aavanza en la lista principal
             
      [(eq? (car lista) 'X) ; si el elemento es una 'X se borra saltándonos el cons (caso 3)
       (recorrer (cdr lista))]
       
      [(number? (car lista)) ;Si el elemento es un número se aplica daemon y guardamos el resultado (caso 4)
       (cons (aplicar-daemon (car lista) daemons) ;toma el primer elemento y lo manda a aploicar
             (recorrer (cdr lista)))])) ; avanza en la lista principal
  (recorrer mapa)) ;para que se ejecute

