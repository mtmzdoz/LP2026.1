#include <stdio.h>
#include <stdlib.h>
#include "tablero.h"
#include "armas.h"
#include "main.h"


Tablero* tablero_crear(int ancho, int alto){
    Tablero *tabla = malloc(sizeof(Tablero)); //Memoria para el tablero
    tabla->W= ancho;
    tabla->H= alto;

    tabla->celdas = malloc(alto * sizeof(void **)); //Memoria filas
    for (int y = 0; y < alto; y++) { 
        tabla->celdas[y] = malloc(ancho * sizeof(void *)); //memoria columnas
        for (int x = 0; x < ancho; x++) {
            Celda *celda = malloc(sizeof(Celda)); //Se crea la celda
            celda->pieza = NULL;   
             
            tabla->celdas[y][x] = (void*) celda; //Se guarda el puntero de la celda
        }
    }
    return tabla; 
}

void tablero_imprimir(struct Juego *juego){
    //system("clear");
    //printf("DENTRO IMPRIMIR TAB turno=%d, vivos=%d\n", juego->turno, juego->vivos);
    Tablero *t = juego->t; //Tablero actual
    
    printf("================\n");
    for (int y = t->H - 1; y >= 0; y--){ // De arriba hacia abajo
        printf("%2d ", y + 1);
        for (int x = 0; x < t->W; x++){
            Celda *celda = (Celda*) t->celdas[y][x]; //La celda
                if (celda->pieza == NULL){
                    printf("[ ]");  //Vacio
                } else {
                // Imprime directamente la letra que tenga la pieza
                printf("[%c]", celda->pieza->tipo); 
            }  
        }
        printf("\n");
    }

    printf("   ");
    for (int x = 0; x < t->W; x++) {
        printf(" %d ", x + 1);
    }
    printf("\n");
}

void tablero_liberar(struct Tablero *tablero) {
    if (tablero == NULL) {
        return; 
    }
    for (int y = 0; y < tablero->H; y++) { //filas
        for (int x = 0; x < tablero->W; x++) { //col
            Celda *celda = (Celda*) tablero->celdas[y][x];  
            if (celda->pieza != NULL){
                free(celda->pieza);
            }
            free(celda); 
        }
        free(tablero->celdas[y]); //Free col
    }
    
    free(tablero->celdas); //Fre filas
    free(tablero); 
}