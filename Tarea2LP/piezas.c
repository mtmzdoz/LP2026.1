#include <stdio.h>
#include <stdlib.h>  
#include <stdbool.h>
#include <string.h>
#include "main.h"
#include "tablero.h"
#include "piezas.h"

/*
* Nombre: PoolNivel
* Parámetros: Puntero al juego
* Retorno: void
* Descripción: Según el nivel se inicializa cuántas piezas de cada tipo aparecerán en el tablero 
*/
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

/*
* Nombre: spawn_nivel
* Parámetros:  Puntero al juego. El número del nivel.
* Retorno: void
* Descripción: Ubica las piezas en el tablero. Primero posiciona al Rey 
* en una columna aleatoria de la fila inferior. Luego, inicializa y distribuye 
* aleatoriamente los enemigos definidos en el pool en las filas superiores
*/
void spawn_nivel(struct Juego *juego, int nivel){
    Tablero *t = juego->t;
    int W = t->W;
    int y = juego->t->H - 1;

    int reyX = 1 + (rand() % (W - 2)); 
    int reyY = 0;
    
    if (juego->jugador == NULL){
        juego->jugador = malloc(sizeof(Pieza));
        juego->jugador->tipo = 'R';
        juego->jugador->hp = 1;
        juego->jugador->danoPendiente= 0;

    }

    juego->jugador->x = reyX;
    juego->jugador->y = reyY;
    ((Celda*)t->celdas[reyY][reyX])->pieza = juego->jugador;
    
    //Peones
    for (int i = 0; i < juego->pool.peones; i++){
        int x; 
        int y1 = juego->t->H - 2;
        do{
            x = rand() % W;
            
        }while (((Celda*)t->celdas[y1][x])->pieza != NULL);

        Pieza *peon = malloc(sizeof(Pieza));
        peon->tipo = 'P';
        peon->hp = 1;
        peon->x = x;
        peon->y = y1;
        peon->danoPendiente = 0;
        juego->vivos++;
        ((Celda*)t->celdas[y1][x])->pieza = peon;
    }

    //Caballos
    for (int i = 0; i < juego->pool.caballos; i++){
        int x;
        do{
            x = rand() % W;
        }while (((Celda*)t->celdas[y][x])->pieza != NULL);

        Pieza *caballo = malloc(sizeof(Pieza));
        caballo->tipo = 'C';
        caballo->hp = 2;
        caballo->x = x;
        caballo->y = y;
        caballo->danoPendiente = 0;
        juego->vivos++;
        ((Celda*)t->celdas[y][x])->pieza = caballo;
    }

    //Alfil
    for (int i = 0; i < juego->pool.alfiles; i++){
        int x;
        do{
            x = rand() % W;
        }while (((Celda*)t->celdas[y][x])->pieza != NULL);

        Pieza *alfil = malloc(sizeof(Pieza));
        alfil->tipo = 'A';
        alfil->hp = 2;
        alfil->x = x;
        alfil->y = y;
        alfil->danoPendiente = 0;
        juego->vivos++;
        ((Celda*)t->celdas[y][x])->pieza = alfil;
    }

    //Torre
    for (int i = 0; i < juego->pool.torres; i++){
        int x;
        do{
            x = rand() % W;
        }while (((Celda*)t->celdas[y][x])->pieza != NULL);

        Pieza *torre = malloc(sizeof(Pieza));
        torre->tipo = 'T';
        torre->hp = 4; 
        torre->x = x;
        torre->y = y;
        torre->danoPendiente = 0;
        juego->vivos++;
        ((Celda*)t->celdas[y][x])->pieza = torre;
    }

    //Reina
    for (int i = 0; i < juego->pool.reinas; i++){
        int x;  
        do{
            x = rand() % W;
        }while (((Celda*)t->celdas[y][x])->pieza != NULL);

        Pieza *reina = malloc(sizeof(Pieza));
        reina->tipo = 'Q';
        reina->hp = 3; 
        reina->x = x;
        reina->y = y;
        reina->danoPendiente = 0;
        juego->vivos++;
        ((Celda*)t->celdas[y][x])->pieza = reina;
    }
}

/*
* Nombre: mover_enemigos
* Parámetros: Puntero al juego
* Retorno: void
* Descripción: Moviliza las piezas segun su tipo. Calcula su siguiente movimiento orientándolos hacia 
*              la posición actual del Rey. Gestiona capturas y bloqueos entre piezas.
*/
void mover_enemigos(struct Juego *juego){
    Tablero *t = juego->t;
    Pieza *rey = juego->jugador;

    Pieza *piezas[144]; 
    int total = 0;
    for (int y = 0; y < t->H; y++){
        for (int x = 0; x < t->W; x++){
            Celda *c = (Celda*)t->celdas[y][x];
            if (c->pieza != NULL && c->pieza->tipo != 'R'){
                piezas[total++] = c->pieza;
            }
        }
    }

    for (int i = 0; i < total; i++){
        Pieza *pieza = piezas[i];

        if (juego->jugador == NULL || juego->derrota == 1) {
            break; 
        }

        if (pieza == NULL || ((Celda*)t->celdas[pieza->y][pieza->x])->pieza != pieza){
            continue; 
        }

        int nuevoX = pieza->x;
        int nuevoY = pieza->y;

        if (pieza->tipo == 'T' && juego->turno_enemigos % 2 != 0){
            continue; 
        }

        //Peon
        if (pieza->tipo == 'P'){
            
            int avanceY = -1; 
            
            // ataque
            if (rey->y == pieza->y + avanceY && abs(rey->x - pieza->x) <= 1){
                nuevoX = rey->x;
                nuevoY = rey->y;
            }else{ //movimiento
                nuevoY += avanceY;
            }
        //Caballo
        }else if (pieza->tipo == 'C'){
            if (abs(rey->y - pieza->y) >= abs(rey->x - pieza->x)){
                nuevoY += (rey->y > pieza->y) ? 2 : -2;
                nuevoX += (rey->x > pieza->x) ? 1 : -1;
            }else{
                nuevoX += (rey->x > pieza->x) ? 2 : -2;
                nuevoY += (rey->y > pieza->y) ? 1 : -1;
            }
        //Reina, alfil y torre se pueden combinar 
        }else if (pieza->tipo == 'T' || pieza->tipo == 'A' || pieza->tipo == 'Q'){
            int dx = (rey->x > pieza->x) ? 1 : (rey->x < pieza->x ? -1 : 0);
            int dy = (rey->y > pieza->y) ? 1 : (rey->y < pieza->y ? -1 : 0);

            if (pieza->tipo == 'T'){ 
                if (abs(rey->y - pieza->y) >= abs(rey->x - pieza->x)){
                    dx = 0; 
                }else{
                    dy = 0; 
                }
            }else if (pieza->tipo == 'A'){ 
                if (dx == 0) dx = 1; 
                if (dy == 0) dy = 1; 
            }

            int maxPasos = (pieza->tipo == 'Q') ? 4 : 2; 

            for (int p = 0; p < maxPasos; p++){
                int proxX = nuevoX + dx;
                int proxY = nuevoY + dy;

                // evitamos q salgan del tablero
                if (proxX < 0 || proxX >= t->W || proxY < 0 || proxY >= t->H){
                    break;
                }

                Celda *destino = (Celda*)t->celdas[proxY][proxX];

                if (destino->pieza != NULL){
                    if (destino->pieza->tipo == 'R'){ 
                        nuevoX = proxX; 
                        nuevoY = proxY; 
                    }
                    break; 
                }
                nuevoX = proxX;
                nuevoY = proxY;
            }
        }
        
        if (nuevoX >= 0 && nuevoX < t->W && nuevoY >= 0 && nuevoY < t->H){
            Celda *destino = (Celda*)t->celdas[nuevoY][nuevoX];

            if (destino->pieza != NULL && destino->pieza->tipo == 'R'){
                juego->derrota = 1; 
                juego->piezaJaque = pieza->tipo;
                free(destino->pieza); 
                juego->jugador = NULL; 
                destino->pieza = NULL;
                
            }
            if (destino->pieza == NULL || destino->pieza->tipo == 'R' ){
                ((Celda*)t->celdas[pieza->y][pieza->x])->pieza = NULL;
                pieza->x = nuevoX;
                pieza->y = nuevoY;
                destino->pieza = pieza;
            }
        }
    }
}

/*
* Nombre: verificar_estado_rey
* Parámetros: Puntero al juego.
* Retorno: Booleano true si el Rey fue capturado, false en caso contrario
* Descripción: Comprueba si alguna pieza enemiga ha logrado posicionarse en la misma celda que el Rey
*              En caso de que sea así, identifica qué tipo de pieza realizó la captura para informar en el 0Jaque Mate
*/
bool verificar_estado_rey(struct Juego *juego){
    if (juego->jugador == NULL){
        return false;
    }
    int reyX = juego->jugador->x;
    int reyY = juego->jugador->y;
    
    Celda *celdaRey = (Celda*)juego->t->celdas[reyY][reyX];

    if (celdaRey->pieza != NULL && celdaRey->pieza->tipo != 'R'){
        juego->piezaJaque = celdaRey->pieza->tipo; 
        return true; 
    }
    return false; 
}

/*
* Nombre: resolverDanos
* Parámetros: Puntero al juego
* Retorno: void
* Descripción: Aplica el daño pendiente a las piezas. Resta el daño en su HP y si llega a 0 o menos, elimina la pieza 
*           del tablero, libera su memoria y actualiza el contador de enemigos vivos. 
*           También tiene los mensajes de impacto para el registro del juego.
*/
void resolverDanos(struct Juego *j){
    for (int y = 0; y < j->t->H; y++){
        for (int x = 0; x < j->t->W; x++){
            Celda *celda = (Celda*)j->t->celdas[y][x];
            
            if (celda->pieza != NULL && celda->pieza->tipo != 'R' && celda->pieza->danoPendiente > 0){
                Pieza *pieza = celda->pieza;
                int dano = pieza->danoPendiente;
                int danoMostrado = dano;

                if (dano > pieza->hp) {
                    danoMostrado = pieza->hp;
                }
                pieza->hp -= dano;
                pieza->danoPendiente = 0; 
                char mensajeTemp[100] = "";

                if (pieza->hp <= 0){
                    sprintf(mensajeTemp, "IMPACTO!! - %d HP infligido a %c.\n ¡%c Ha caido!\n", danoMostrado, pieza->tipo, pieza->tipo);
                    strcat(j->mensaje, mensajeTemp);
                    free(pieza);
                    celda->pieza = NULL;
                    j->vivos--;
                }else{
                    sprintf(mensajeTemp, "IMPACTO!! - %d HP infligido a %c. (HP restante: %d)\n", danoMostrado, pieza->tipo, pieza->hp);
                    strcat(j->mensaje, mensajeTemp);
                }
            }
        }
    }
}