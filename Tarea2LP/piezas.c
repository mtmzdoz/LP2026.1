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
        int x; 
        int y1 = juego->t->H - 2;
        do {
            x = rand() % W;
            
        } while (((Celda*)t->celdas[y1][x])->pieza != NULL);

        Pieza *peon = malloc(sizeof(Pieza));
        peon->tipo = 'P';
        peon->hp = 1;
        peon->x = x;
        peon->y = y1;
        peon->dano_pendiente = 0;
        juego->vivos++;
        ((Celda*)t->celdas[y1][x])->pieza = peon;
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
        caballo->dano_pendiente = 0;
        juego->vivos++;
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
        alfil->dano_pendiente = 0;
        juego->vivos++;
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
        torre->dano_pendiente = 0;
        juego->vivos++;
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
        reina->dano_pendiente = 0;
        juego->vivos++;
        ((Celda*)t->celdas[y][x])->pieza = reina;
    }
}


void mover_enemigos(struct Juego *juego) {
    Tablero *t = juego->t;
    Pieza *rey = juego->jugador;

    // 1. Recolección (Igual que lo tienes tú)
    Pieza *enemigos[144]; 
    int total = 0;
    for (int y = 0; y < t->H; y++) {
        for (int x = 0; x < t->W; x++) {
            Celda *c = (Celda*)t->celdas[y][x];
            if (c->pieza != NULL && c->pieza->tipo != 'R') {
                enemigos[total++] = c->pieza;
            }
        }
    }

    // 2. Procesar cada enemigo
    for (int i = 0; i < total; i++) {
        Pieza *e = enemigos[i];

        if (e == NULL || ((Celda*)t->celdas[e->y][e->x])->pieza != e) {
            continue; 
        }

        int n_x = e->x;
        int n_y = e->y;

        // --- LÓGICA DEL PEÓN ---
        if (e->tipo == 'P') {
            
            int avance_y = -1; 
            
            // 1. ATAQUE: Solo ataca si el Rey está exactamente 1 casilla ADELANTE
            // abs(rey->x - e->x) <= 1 cubre la casilla de al frente y las 2 diagonales
            if (rey->y == e->y + avance_y && abs(rey->x - e->x) <= 1) {
                n_x = rey->x;
                n_y = rey->y;
            } 
            // 2. MOVIMIENTO NORMAL: Marcha recta
            else {
                n_y += avance_y;
                // n_x se mantiene igual (no se mueve hacia los lados)
            }
          
        } 
            // 3. MOVIMIENTO NORMAL
        
        

        // --- PASO 3: ACTUALIZACIÓN FÍSICA (Esto es lo que te faltaba) ---
        // Solo movemos si la celda de destino está vacía
        // (Ignoramos al Rey por ahora como pediste)
        if (n_x >= 0 && n_x < t->W && n_y >= 0 && n_y < t->H) {
            Celda *destino = (Celda*)t->celdas[n_y][n_x];

            if (destino->pieza != NULL && destino->pieza->tipo == 'R') {
                juego->derrota = 1; 
                destino->pieza->tipo = 'X';
                // No hacemos free aquí para evitar que el programa explote 
                // intentando leer al Rey más tarde.
            }
            if (destino->pieza == NULL) {
                // 1. Quitamos la pieza de su celda actual
                ((Celda*)t->celdas[e->y][e->x])->pieza = NULL;
                
                // 2. Actualizamos las coordenadas internas de la pieza
                e->x = n_x;
                e->y = n_y;
                
                // 3. Ponemos la pieza en la nueva celda
                destino->pieza = e;
            }
        }
    }
}


void resolver_danos(struct Juego *j) {
    for (int y = 0; y < j->t->H; y++) {
        for (int x = 0; x < j->t->W; x++) {
            Celda *c = (Celda*)j->t->celdas[y][x];
            
            if (c->pieza != NULL && c->pieza->tipo != 'R') {
                Pieza *enemigo = c->pieza;
                
                if (enemigo->dano_pendiente > 0) {
                    enemigo->hp -= enemigo->dano_pendiente;
                    // Limpiamos el daño porque ya se aplicó a la vida
                    enemigo->dano_pendiente = 0; 
                    
                    if (enemigo->dano_pendiente > 0) {
                        printf("DEBUG: Enemigo tipo %c tiene %d de daño pendiente!\n", enemigo->tipo, enemigo->dano_pendiente);
                        enemigo->hp -= enemigo->dano_pendiente;
   
                    }
                    if (enemigo->hp <= 0) {
                        sprintf(j->mensaje, "IMPACTO LETAL: ¡%c ha caido!", enemigo->tipo);
                        
                        free(enemigo);
                        c->pieza = NULL;
                        j->vivos--;
                    }
                }
            }
        }
    }
}