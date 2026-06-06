Nombre: Matilde Vásquez
Rol: 202473652-3
Versión DrRacket: v9.1



Instrucciones
1. Para la P1, definir la función matemática como daemon (en pdf esta como daemon-alfa), ya que si se usa otro formato no reconocerá para poder ejecutar las 2 funciones
2. Para P1 definir daemon y puertos antes de las funciones o pasarlos directo como parámetros
3. En general tener cuidado con la comilla, usar '
4. Asegurarse que #lang scheme este solo una vez por archivo para evitar errores de compilación 

Consideraciones
1. Para P1 si bien se podía de otra forma, se decidió usar reverse por eficacia (también se aclaro en el foro que se podía utilizar)
2. Para P1 se uso (define puertos '(1 2 3)) y (define daemon (lambda (x) (modulo (+ x 15) 10))). Se llego a los mismos resultados de los ejemplos
3. Para P3 se decidió usar una función auxiliar para aplicar los daemons (lógica matemática) aparte y no en la misma para no tener toda la lógica en un mismo lugar
4. En general, si se ocupan todos los ejemplos del pdf se llega el mismo resultado