Nombre: Matilde Vásquez
Rol: 202473652-3
WSL: Ubuntu-24.04

Consideraciones
1. En el pdf no se menciona el caso de que el Rey pase a los peones, pero si es asi, estos avanzaran hasta el final del tablero y se quedaran ahí.
2. Arma especial: Es un "laser". Misma funcionalidad del sniper pero este atraviesa las piezas y hace daño 1
3. Si un arma se queda sin municion, y dispara, NO se consume el turno.
4. Se agregaron unos structs y funciones para facilitar un poco el codigo. En cada .h esta especificado con "//Yo" o "//agregados yo"
5. Cuando se dispara las casillas no se marcan con [X], pero estan los print debug para ver donde fue el impacto.

Consideraciones del foro
1. duda avance del peon
R: se mueve siguiendo las reglas originales del ajedrez simplemente, no hay reglas especiales para el peón más que la de poder atacar al rey de manera frontal
Por lo que solo puede comer hacia adelante, no detras. 

2. Avance de las piezas
R:con que 2 o más piezas avancen a la vez, con eso será suficiente y pueden agregar en el readme como decidieron implementar la mecánica
Por lo que se hizo que todas las piezas se movieran al mismo tiempo, a excepcion de la torre que es cada 2 turnos

3. Condicion de derrota
R:si al finalizar tu turno estás en una casilla alcanzable por otra pieza, pierdes, pero si aún estás en la ejecución de tu turno y 
tienes movimientos disponibles para escapar de la situación de jaque el rey no muere.

