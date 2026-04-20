#ifndef MAIN_H
#define MAIN_H

#include "armas.h"
#include "piezas.h"
#include "tablero.h"

typedef struct Juego{
    Tablero *t;
    Armas arsenal;
    Pieza *jugador; //Rey
    int nivel_actual; 
    int turno_enemigos; //Para torre
    //agregados yo 
    int derrota; //Para terminar el programa cuando matan al rey
    int turno, vivos;
    PoolPiezas pool;
    char mensaje[1000], piezaJaque; //Para poder mostrar el mensaje de las armas y quien fue el que mato al rey
}Juego;

//Yo
bool moverJugador(struct Juego *juego, char direccion);
void sgteNivel(struct Juego *juego);

#endif