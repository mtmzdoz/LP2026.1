#include <stdio.h>
#include <stdlib.h>  
#include <stdbool.h>
#include "main.h"
#include "tablero.h"
#include "piezas.h"


void PoolNivel(Juego *juego){
    if (juego->nivel_actual == 1){  //Ez
        juego->pool.peones = 4;
        juego->pool.caballos = 2;
        juego->pool.alfiles = 2; 
        juego->pool.torres = 0;
        juego->pool.reinas = 0; 

    }else if (juego->nivel_actual == 2){ //Medio
        juego->pool.peones = 4;
        juego->pool.caballos = 2;  
        juego->pool.alfiles = 0;
        juego->pool.torres = 2;
        juego->pool.reinas = 0;

    }else{ //Dificil
        juego->pool.peones = 2;
        juego->pool.caballos = 0;
        juego->pool.alfiles = 1;  
        juego->pool.torres = 1;
        juego->pool.reinas = 1;
    }
}

void spawn_nivel(struct Juego *juego, int nivel){
    Tablero *t = juego->t;
    int W = t->W;
    int y = juego->t->H - 1;



    // ==========================================
    // 1. SPAWN DEL REY (Fila inferior, sin esquinas)
    // ==========================================
    int rey_x = 1 + (rand() % (W - 2)); 
    int rey_y = 0;

    Pieza *rey = malloc(sizeof(Pieza));
    rey->tipo = 'R';
    rey->hp = 1;
    rey->x = rey_x;
    rey->y = rey_y;

    juego->jugador = rey;
    ((Celda*)t->celdas[rey_y][rey_x])->pieza = rey;

    // ==========================================
    // 2. SPAWN DE ENEMIGOS (Dinámico para cualquier nivel)
    // ==========================================
    
    // --- Peones (Segunda fila -> y = 1) ---
    for (int i = 0; i < juego->pool.peones; i++) {
        int x,yPeon; 
        int y1 = juego->t->H - 1;
        int y2 = juego->t->H - 2;
        do {
            x = rand() % W;
            yPeon = (rand() % 2 == 0) ? y1 : y2; // Alterna entre la fila 1 y 2 para más dispersión
        } while (((Celda*)t->celdas[yPeon][x])->pieza != NULL);

        Pieza *peon = malloc(sizeof(Pieza));
        peon->tipo = 'P';
        peon->hp = 1;
        peon->x = x;
        peon->y = yPeon;
        ((Celda*)t->celdas[yPeon][x])->pieza = peon;
    }

    // --- Caballos (Primera fila -> y = 0) ---
    for (int i = 0; i < juego->pool.caballos; i++) {
        int x;
        do {
            x = rand() % W;
        } while (((Celda*)t->celdas[y][x])->pieza != NULL);

        Pieza *caballo = malloc(sizeof(Pieza));
        caballo->tipo = 'C';
        caballo->hp = 2;
        caballo->x = x;
        caballo->y = y;
        ((Celda*)t->celdas[y][x])->pieza = caballo;
    }

    // --- Alfiles (Primera fila -> y = 0) ---
    for (int i = 0; i < juego->pool.alfiles; i++) {
        int x;
        do {
            x = rand() % W;
        } while (((Celda*)t->celdas[y][x])->pieza != NULL);

        Pieza *alfil = malloc(sizeof(Pieza));
        alfil->tipo = 'A';
        alfil->hp = 2;
        alfil->x = x;
        alfil->y = y;
        ((Celda*)t->celdas[y][x])->pieza = alfil;
    }

    // --- Torres (Primera fila -> y = 0) ---
    for (int i = 0; i < juego->pool.torres; i++) {
        int x;
        do {
            x = rand() % W;
        } while (((Celda*)t->celdas[y][x])->pieza != NULL);

        Pieza *torre = malloc(sizeof(Pieza));
        torre->tipo = 'T';
        torre->hp = 4; // HP de la torre según PDF
        torre->x = x;
        torre->y = y;
        ((Celda*)t->celdas[y][x])->pieza = torre;
    }

    // --- Reina (Primera fila -> y = 0) ---
    for (int i = 0; i < juego->pool.reinas; i++) {
        int x;  
        do {
            x = rand() % W;
        } while (((Celda*)t->celdas[y][x])->pieza != NULL);

        Pieza *reina = malloc(sizeof(Pieza));
        reina->tipo = 'Q';
        reina->hp = 3; // HP de la reina según PDF
        reina->x = x;
        reina->y = y;
        ((Celda*)t->celdas[y][x])->pieza = reina;
    }
}