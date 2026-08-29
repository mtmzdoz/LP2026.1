Nota:72
Nombre: Matilde Vásquez
Rol: 202473652-3
Rut: 21.715.078-7

Consideraciones
-No usar ñ en el nombre de las funciones/variables ya que se cae el programa
-El EBNF base de los comentarios se modifico porque sino era muy dificil para eliminarlos y no se me ocurrio de otra forma
-Para los posibles comentarios se asumio que solo se usaran letras(sin tilde) y números, si se pone cualquier caracter ej: ,.:(){} 
 no los lee y se cae el programa
- Se considero mantener la estructura original de snake_case, es decir, que no acepta que se empiece y termine con "_"
- Se asumio que no se ocuparian palabras reservadas para definir el nombre de una variable/funcion

Consideraciones Foro
1."duda identificación con saltos de linea en el archivo"
Se respondio: algo como "intvariable" sería inválido. 
Por lo que se asumio que si una funcion comenzaba asi tambien sera invalida y no se escribira en ningun txt 
2."Errores de sintaxis"
Se respondio: Sobre tener o no return correspondería a un error semántico, no sintáctico, solo debes preocuparte que el return esté bien escrito y nada más. 
Por lo que no hay ningun mensaje de error respecto a la ausencia del return
3."nombres con numeros en ellos"
Se respondio: Es correcto, el EBNF no incluye los números como algo válido en los nombres de las variables/funciones.
Por lo que se asumio que si existe por ej: funcion_123 se escribira en desconocido.txt
