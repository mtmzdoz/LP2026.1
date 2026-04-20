#ifndef PIEZAS_H
#define PIEZAS_H

#include <stdbool.h>
//Yo
typedef struct PoolPiezas{
    int peones;
    int caballos;
    int alfiles;
    int torres;
    int reinas;
} PoolPiezas;

typedef struct Pieza{
    char tipo; /* ’P’=Peon, ’C’=Caballo, ’A’=Alfil, ’T’=Torre, ’Q’=Reina, ’R’=Rey */
    int hp;
    int x, y;
    //yo 
    int danoPendiente;
}Pieza;

typedef struct Celda{
    Pieza *pieza; /* NULL si la celda esta vacia */
}Celda;

void spawn_nivel(struct Juego *juego, int nivel);
void mover_enemigos(struct Juego *juego);
bool verificar_estado_rey(struct Juego *juego); /* Revisa si el Rey esta en Jaque */
//Yo agreg                                      
void PoolNivel(struct Juego *juego);
void resolverDanos(struct Juego *juego);



#endif