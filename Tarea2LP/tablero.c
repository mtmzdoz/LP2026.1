#include <stdio.h>
#include <stdlib.h>
#include "tablero.h"
#include "armas.h"
#include "main.h"

/*
* Nombre: tablero_crear
* Parámetros: Ancho y alto del tablero
* Retorno: Puntero al tablero creado
* Descripción: Crea un tablero dinámico con todas las celdas inicializadas, sin piezas y sin daño pendiente
*/
Tablero* tablero_crear(int ancho, int alto){
    Tablero *tabla = malloc(sizeof(Tablero)); //Memoria para el tablero
    tabla->W= ancho;
    tabla->H= alto;

    tabla->celdas = malloc(alto * sizeof(void **)); //Memoria filas
    for (int y = 0; y < alto; y++){ 
        tabla->celdas[y] = malloc(ancho * sizeof(void *)); //memoria columnas
        for (int x = 0; x < ancho; x++) {
            Celda *celda = malloc(sizeof(Celda)); //Se crea la celda
            celda->pieza = NULL;   
             
            tabla->celdas[y][x] = (void*) celda; //Se guarda el puntero de la celda
        }
    }
    return tabla; 
}

/*
* Nombre: tablero_imprimir
* Parámetros: Puntero al juego
* Retorno: void
* Descripción: Imprime el tablero con las piezas y jugador(Rey), además del HUD turno, vivos, armas y munición.
*               Y en caso de hacer daño también mostarlo por pantalla
*/
void tablero_imprimir(struct Juego *juego){
    Tablero *t = juego->t; //Tablero actual
    
    printf("================\n");
    for (int y = t->H - 1; y >= 0; y--){ // De arriba hacia abajo
        printf("%2d ", y + 1);
        for (int x = 0; x < t->W; x++){
            Celda *celda = (Celda*) t->celdas[y][x]; 
                if (celda->pieza == NULL){
                    printf("[ ]");  
                } else {
                printf("[%c]", celda->pieza->tipo); 
            }  
        }
        printf("\n");
    }

    printf("   ");
    for (int x = 0; x < t->W; x++){
        printf(" %d ", x + 1);
    }
    printf("\n");
    printf("================\n");
    printf("Nivel: %d | Turno: %d | Enemigos restantes: %d\n", juego->nivel_actual, juego->turno, juego->vivos);
    printf(" Arsenal: [1] Escopeta (%d/%d) | [2] Sniper (%d/%d)\n", juego->arsenal.municion_actual[0], juego->arsenal.municion_maxima[0], juego->arsenal.municion_actual[1], juego->arsenal.municion_maxima[1]);
    printf("          [3] Granada  (%d/%d) | [4] Especial (%d/%d)\n", juego->arsenal.municion_actual[2], juego->arsenal.municion_maxima[2], juego->arsenal.municion_actual[3], juego->arsenal.municion_maxima[3]);
    if (juego->mensaje[0] != '\0'){
        printf("%s", juego->mensaje);
        juego->mensaje[0] = '\0'; // Limpiar después de mostrar
    }
}

/*
* Nombre: tablero_liberar
* Parámetros: Puntero al tablero
* Retorno: void
* Descripción: Libera toda la memoria asociada al tablero y sus celdas, incluyendo piezas 
*/
void tablero_liberar(struct Tablero *tablero){
    if (tablero == NULL){
        return; 
    }
    for (int y = 0; y < tablero->H; y++){ //filas
        for (int x = 0; x < tablero->W; x++){ //col
            Celda *celda = (Celda*) tablero->celdas[y][x];  
            if (celda->pieza != NULL){
                free(celda->pieza);
                celda->pieza = NULL;
            }
            free(celda); 
        }
        free(tablero->celdas[y]); //Free col
    }
    
    free(tablero->celdas); //Fre filas
    free(tablero); 
}