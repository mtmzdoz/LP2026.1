#include <stdio.h>
#include <stdlib.h>
#include "armas.h"
#include "main.h"
#include "piezas.h"
#include "tablero.h"

#include <stdio.h>
#include <stdlib.h>
#include "armas.h"
#include "main.h"
#include "piezas.h"
#include "tablero.h"

/*
* Nombre: dispararArmas
* Parámetros:  Puntero al juego e índice del arma seleccionada (0-3)
* Retorno: Booleano. true si el disparo se ejecutó, false si hubo error en dirección o ID de arma
* Descripción: Gestiona la interfaz de disparo. Solicita al usuario una dirección  (WASD y QEZC), traduce el input a vectores (dir_x, dir_y) 
*               y ejecuta la función de disparo correspondiente mediante el arreglo de punteros a función
*/
bool dispararArmas(struct Juego *juego, int arma_id){
    if (arma_id < 0 || arma_id > 3){ //Si el id de arma es <0 o >3 es invalido
        return false; 
    }
    
    printf("Ingrese dirección (WASD/QEZC): ");
    char dir;
    scanf(" %c", &dir);

    int dir_x = 0;
    int dir_y = 0;
    if (dir == 'w'){ 
        dir_y = 1;
    }else if (dir == 's'){
         dir_y = -1;
    }else if (dir == 'a'){
        dir_x = -1;
    }else if (dir == 'd'){
        dir_x = 1;
    }else if (dir == 'q'){ //Diagonal arriba izquierda
        dir_x = -1; dir_y = 1;  
    }else if (dir == 'e'){  //Diagonal arriba derecha
        dir_x = 1;  dir_y = 1;  
    }else if (dir == 'z'){ //Diagonal abajo izquierda
        dir_x = -1; dir_y = -1; 
    }else if (dir == 'c'){ //Diagonal abajo derecha
        dir_x = 1;  dir_y = -1; 
    }else {
        sprintf(juego->mensaje, "Dirección inválida");
        return false; 
    }

    return juego->arsenal.disparar[arma_id](juego, dir_x, dir_y);
}

/*
* Nombre: escopeta
* Parámetros: Puntero al juego. Componentes X e Y de la dirección
* Retorno: Booleano. true si se disparó, false si no hay munición
* Descripción: Realiza un disparo con dispersion. Si el disparo es derecho, afecta en 
*            forma de T, primera celda 2 de daño las 3 traseras 1. Si es diagonal afecta un área de 2x2
*/
bool escopeta(struct Juego *j, int dir_x, int dir_y){
    if (j->arsenal.municion_actual[0] <= 0){
        sprintf(j->mensaje, "¡Escopeta sin munición!\n");
        return false;
    }

    j->arsenal.municion_actual[0]--;
    int posX = j->jugador->x;
    int posY = j->jugador->y;
    bool impacto = false;

    int disparoAdelanteX = posX + dir_x;
    int disparoAdelanteY = posY + dir_y;
    
    if (dir_x != 0 && dir_y != 0) { //para disparo diagonal
        int bloquesX[4] = { posX + dir_x, posX + (dir_x * 2), posX + dir_x,       posX + (dir_x * 2) };
        int bloquesY[4] = { posY + dir_y, posY + dir_y,       posY + (dir_y * 2), posY + (dir_y * 2) };
        
        int danos[4] = {2, 1, 1, 1};

        for (int i = 0; i < 4; i++) {
            int posX = bloquesX[i];
            int posY = bloquesY[i];

            if (posX >= 0 && posX < j->t->W && posY >= 0 && posY < j->t->H){
                Celda *celda = (Celda*)j->t->celdas[posY][posX];
                //printf("Debug escopeta diagonal: Impacto en (%d, %d)\n", posX + 1, posY + 1);
                
                if (celda->pieza != NULL && celda->pieza->tipo != 'R'){
                    celda->pieza->danoPendiente += danos[i];
                    impacto = true; 
                }
            }
        }
        
    }else{ //disparo normal
        if (disparoAdelanteX >= 0 && disparoAdelanteX < j->t->W && disparoAdelanteY >= 0 && disparoAdelanteY < j->t->H){
            Celda *celdaAdelante = (Celda*)j->t->celdas[disparoAdelanteY][disparoAdelanteX];
            //printf("Debug escopeta: Impacto en (%d, %d)\n", disparoAdelanteX + 1, disparoAdelanteY + 1); //coordenada disparo adelante 
            if (celdaAdelante->pieza != NULL && celdaAdelante->pieza->tipo != 'R'){
                celdaAdelante->pieza->danoPendiente += 2; 
            }
        }

        int centroX = posX + (dir_x * 2);
        int centroY = posY + (dir_y * 2);
        int ortogonalX = -dir_y; 
        int ortogonalY = dir_x;

        for (int i = -1; i <= 1; i++){
            int disparoAtrasX = centroX + (i * ortogonalX);
            int disparoAtrasY = centroY + (i * ortogonalY);

            if (disparoAtrasX >= 0 && disparoAtrasX < j->t->W && disparoAtrasY >= 0 && disparoAtrasY < j->t->H){
                Celda *celdaAtras = (Celda*)j->t->celdas[disparoAtrasY][disparoAtrasX];
                //printf("Debug escopeta: Impacto en (%d, %d)\n", disparoAtrasX + 1, disparoAtrasY + 1); //cordenadas disparo de las 3 celdas de atras
                if (celdaAtras->pieza != NULL && celdaAtras->pieza->tipo != 'R'){
                    celdaAtras->pieza->danoPendiente += 1; 
                    impacto = true; 
                }
            }
        }
        
    }

    if (impacto){
        sprintf(j->mensaje, "¡Escopetazo hacia las piezas!\n");
        
    }else{
        sprintf(j->mensaje, "Ninguna bala alcanzo a una pieza\n");
        
    }
    return true;
}

/*
* Nombre: francotirador
* Parámetros:  Puntero al juego. Componentes X e Y de la dirección
* Retorno: Booleano true si se disparó, false si no hay munición
* Descripción: Dispara en línea recta o diagonal. La bala viaja hasta que impacta con la primera pieza
* o ninguna. Inflige 3 puntos de daño al objetivo.
*/
bool francotirador(struct Juego *j, int dir_x, int dir_y){
    if (j->arsenal.municion_actual[1] <= 0){
        sprintf(j->mensaje, "¡Sniper sin munición!\n");
        return false;
    }

    j->arsenal.municion_actual[1]--;
    int proxX = j->jugador->x + dir_x;
    int proxY = j->jugador->y + dir_y;
    bool impacto = false;

    while (proxX >= 0 && proxX < j->t->W && proxY >= 0 && proxY < j->t->H){
        Celda *celda = (Celda*)j->t->celdas[proxY][proxX];
        if (celda->pieza != NULL && celda->pieza->tipo != 'R'){
            //printf("Debug sniper Impacto en: (%d, %d)\n", proxX + 1, proxY + 1); //pieza q le pega
            celda->pieza->danoPendiente += 3;
            impacto = true;
            break;
            
        }else{
            //printf("Debug sniper: Bala atravesando: (%d, %d)\n", proxX + 1, proxY + 1); //Pir los bloques q pasa
        }
        proxX += dir_x;
        proxY += dir_y;
    }

    if (impacto){
        sprintf(j->mensaje, "¡El Sniper impacto a una pieza\n");
        
    }else{
        sprintf(j->mensaje, "El Sniper no impacto a ninguna pieza\n");
        
    }
    return true;
}

/*
* Nombre: granada
* Parámetros:  Puntero al juego. Componentes X e Y de la dirección
* Retorno: Booleano true si se exploto, false si no hay granada
* Descripción: Lanza una granada a una distancia fija de 3 celdas desde el jugador(Rey). 
*           Al impactar, genera una explosión en un área de 3x3 celdas, infligiendo 2 puntos de daño a todas
*           las piezas dentro del radio.
*/
bool granada(struct Juego *j, int dir_x, int dir_y){
    if (j->arsenal.municion_actual[2] <= 0){
        sprintf(j->mensaje, "¡Sin granadas!\n");
        return false;
    }

    j->arsenal.municion_actual[2]--;
    int centroX = j->jugador->x + (dir_x * 3);
    int centroY = j->jugador->y + (dir_y * 3);
    bool impacto = false;
    //printf("Debug granada: Centro (%d, %d)\n", centroX + 1, centroY + 1); //para ver el centro

    for (int dy = -1; dy <= 1; dy++){
        for (int dx = -1; dx <= 1; dx++){
            int explosionX = centroX + dx;
            int explosionY = centroY + dy;

            if (explosionX >= 0 && explosionX < j->t->W && explosionY >= 0 && explosionY < j->t->H){
                Celda *celda = (Celda*)j->t->celdas[explosionY][explosionX];
                //printf("Debug granada: celdas impacatadas (%d, %d)\n", explosionX + 1, explosionY + 1); //ver el alcance de la granada
                if (celda->pieza != NULL && celda->pieza->tipo != 'R'){
                    //printf("Debug granada:Pieza impactada en (%d, %d)!\n", explosionX + 1, explosionY + 1); //q piezas le pego
                    celda->pieza->danoPendiente += 2; 
                    impacto = true;
                }
            }
        }
    }

    if (impacto){
        sprintf(j->mensaje, "La granada alcanzó a las piezas\n");
       
    }else{
        sprintf(j->mensaje, "La granada explotó, pero no impacto a ninguna pieza\n");
        
    }
    return true;
}

/*
* Nombre: especial
* Parámetros:  Puntero al juego. Componentes X e Y de la dirección
* Retorno: Booleano true si se disparó, false si no hay munición
* Descripción: Dispara en línea recta o diagonal. El láser atraviesa a todas las piezas en la direccion deseada 
* o ninguna. Inflige 1 de daño a todas las piezas impactadas
*/
bool especial(struct Juego *j, int dir_x, int dir_y){
    if (j->arsenal.municion_actual[3] <= 0){
        sprintf(j->mensaje, "¡Sin carga de láser!\n");
        return false;
    }
    j->arsenal.municion_actual[3]--;
    int proxX = j->jugador->x + dir_x;
    int proxY = j->jugador->y + dir_y;
    bool impacto = false;
    
    
    while (proxX >= 0 && proxX < j->t->W && proxY >= 0 && proxY < j->t->H){    
        Celda *celda = (Celda*)j->t->celdas[proxY][proxX];
        if (celda->pieza != NULL && celda->pieza->tipo != 'R'){
            celda->pieza->danoPendiente += 1; 
            impacto = true;
            //printf("Debug especial Impacto en: (%d, %d)\n", proxX + 1, proxY + 1);
        }else{
            //printf("Debug especial: Láser atravesando (%d, %d)\n", proxX + 1, proxY + 1); 
        }
        proxX += dir_x;
        proxY += dir_y;
    }

    if (impacto){
        sprintf(j->mensaje, "¡El láser atravesó a las piezas!\n");
        
    }else{
        sprintf(j->mensaje, "El láser no atraveso a nadie\n");
        
    }
    return true;

}