#include <stdio.h>
#include <stdlib.h>
#include "armas.h"
#include "main.h"
#include "piezas.h"
#include "tablero.h"

// --- En armas.c ---

bool disparar_armas(struct Juego *juego, int arma_id){
    if (arma_id < 0 || arma_id > 3){ //Si el id de arma es <0 o >2 es invalido
        return false; 
    }
    
    printf("Ingrese dirección (WASD): ");
    char dir;
    scanf(" %c", &dir);

    // 3. Crear y calcular las variables que exige el puntero a función
    int dir_x = 0, dir_y = 0;
    if (dir == 'w') dir_y = -1;
    else if (dir == 's') dir_y = 1;
    else if (dir == 'a') dir_x = -1;
    else if (dir == 'd') dir_x = 1;
    else {
        sprintf(juego->mensaje, "Dirección inválida. Tiro cancelado.");
        return false; 
    }

    // 4. Llamar al arma usando el nombre correcto ("juego") y las variables creadas
    return juego->arsenal.disparar[arma_id](juego, dir_x, dir_y);
}

bool escopeta(struct Juego *j, int dir_x, int dir_y) {
    if (j->arsenal.municion_actual[0] <= 0) {
        sprintf(j->mensaje, "¡CLICK! Sin munición de Escopeta.");
        return false;
    }

    j->arsenal.municion_actual[0]--;
    int p_x = j->jugador->x;
    int p_y = j->jugador->y;

    // FASE 1: IMPACTO PRINCIPAL (Distancia 1)
    int t1_x = p_x + dir_x;
    int t1_y = p_y + dir_y;

    if (t1_x >= 0 && t1_x < j->t->W && t1_y >= 0 && t1_y < j->t->H) {
        Celda *c1 = (Celda*)j->t->celdas[t1_y][t1_x];
        if (c1->pieza != NULL && c1->pieza->tipo != 'R') {
            c1->pieza->dano_pendiente += 2; // Marcamos daño 2
        }
    }

    // FASE 2: CONO DE DISPERSIÓN (Distancia 2)
    int centro_x = p_x + (dir_x * 2);
    int centro_y = p_y + (dir_y * 2);
    int ort_x = dir_y; 
    int ort_y = dir_x;

    for (int i = -1; i <= 1; i++) {
        int sec_x = centro_x + (i * ort_x);
        int sec_y = centro_y + (i * ort_y);

        if (sec_x >= 0 && sec_x < j->t->W && sec_y >= 0 && sec_y < j->t->H) {
            Celda *c_sec = (Celda*)j->t->celdas[sec_y][sec_x];
            if (c_sec->pieza != NULL && c_sec->pieza->tipo != 'R') {
                c_sec->pieza->dano_pendiente += 1; // Marcamos daño 1
            }
        }
    }

    sprintf(j->mensaje, "¡BAM! Perdigones disparados.");
    return true; 
}

// Funciones temporales para que el Makefile compile sin errores
bool francotirador(struct Juego *j, int dir_x, int dir_y) {
    
    if (j->arsenal.municion_actual[1] <= 0) {
        sprintf(j->mensaje, "¡CLICK! Sniper sin municion.");
        return false;
    }

    j->arsenal.municion_actual[1]--;
    int tx = j->jugador->x + dir_x;
    int ty = j->jugador->y + dir_y;

    while (tx >= 0 && tx < j->t->W && ty >= 0 && ty < j->t->H) {
        Celda *c = (Celda*)j->t->celdas[ty][tx];
        if (c->pieza != NULL && c->pieza->tipo != 'R') {
            c->pieza->dano_pendiente += 3; // 3 de daño
            return true; // "No atraviesa": cortamos la función aquí
        }
        tx += dir_x;
        ty += dir_y;
    }
    sprintf(j->mensaje, "El disparo se perdio en el horizonte...");
    return true;
}

bool granada(struct Juego *j, int dir_x, int dir_y) {
    if (j->arsenal.municion_actual[2] <= 0) {
        sprintf(j->mensaje, "¡CLICK! No quedan granadas.");
        return false;
    }

    j->arsenal.municion_actual[2]--;

    // El impacto es a EXACTAMENTE 3 casillas
    int centro_x = j->jugador->x + (dir_x * 3);
    int centro_y = j->jugador->y + (dir_y * 3);

    sprintf(j->mensaje, "¡BOOM! Granada lanzada a (%d,%d)", centro_x, centro_y);

    // Explosión 3x3 centrada en (centro_x, centro_y)
    for (int dy = -1; dy <= 1; dy++) {
        for (int dx = -1; dx <= 1; dx++) {
            int ex = centro_x + dx;
            int ey = centro_y + dy;

            if (ex >= 0 && ex < j->t->W && ey >= 0 && ey < j->t->H) {
                Celda *c = (Celda*)j->t->celdas[ey][ex];
                if (c->pieza != NULL && c->pieza->tipo != 'R') {
                    c->pieza->dano_pendiente += 2; // 2 de daño en área
                }
            }
        }
    }
    return true;
}

bool especial(struct Juego *j, int dir_x, int dir_y) {
    sprintf(j->mensaje, "Arma: Especial en construccion.");
    return false; 
}