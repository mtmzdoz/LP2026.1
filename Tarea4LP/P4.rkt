#lang scheme

;parametro token: una cadena de texto (string) que representa la clave interceptada
;retorno: el numero final tras sumar los valores ASCII de solo las letras
(define (hash-desconexion token)
  (foldl + 0;P4, plegar(sumar) toda la lista numérica anterior, empezando en 0
         (map char->integer ;P3, se transforma la lista de letras a su valor númerico (ascii)
              (filter char-alphabetic? ;P2, se elimina lo que no sean letras del alfabeto
                      (string->list token))))) ;P1, texto inicial se convierte a una lista de caracteres


