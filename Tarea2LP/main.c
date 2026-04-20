#include <stdio.h>
#include <stdlib.h>  
#include <stdbool.h>
#include <ctype.h>
#include <time.h>    //Para el time del srand 
#include "main.h"
#include "tablero.h"

/*
* Nombre: mover_jugador
* Parámetros: Puntero al juego y un char que indica la direccion donde se desea mover 'w', 'a', 's', 'd', 'q', 'e', 'z', 'c'
* Retorno: Booleano true si el movimiento fue exitoso, false si fue bloqueado o fuera del tablero
* Descripción: Calcula la nueva posición del jugador. Verifica que el movimiento no exceda los límites del tablero y que la 
*           celda de destino esté vacía. Si es válido, actualiza la pos del jugador (Rey)
*/
bool mover_jugador(Juego *juego, char direccion){ 
    int W = juego->t->W;
    int H = juego->t->H;
    int posxJugador = juego->jugador->x;
    int posyJugador = juego->jugador->y;

    direccion = tolower(direccion);
    if(direccion == 'w' && posyJugador < H-1){
        posyJugador++;
    }else if (direccion == 's' && posyJugador > 0){
        posyJugador--;
    }else if (direccion == 'a' && posxJugador > 0){
        posxJugador--;
    }else if (direccion == 'd' && posxJugador < W - 1){
        posxJugador++;
    }else if (direccion == 'q' && posxJugador > 0 && posyJugador < H - 1){
        posxJugador--;
        posyJugador++;
    }else if (direccion == 'e' && posxJugador < W - 1 && posyJugador < H - 1){
        posxJugador++;
        posyJugador++; 
    }else if (direccion == 'z' && posxJugador > 0 && posyJugador > 0){
        posxJugador--;
        posyJugador--;
    }else if (direccion == 'c' && posxJugador < W - 1 &&  posyJugador > 0){
        posxJugador++;
        posyJugador--; 
    }

    if (posxJugador == juego->jugador->x && posyJugador == juego->jugador->y){
        return false;
    }

    //Que la celda no este ocu
    Celda *nuevaCelda = (Celda*)juego->t->celdas[posyJugador][posxJugador];
    if (nuevaCelda->pieza != NULL){
        return false; 
    }

    Celda *antiguaCelda = (Celda*)juego->t->celdas[juego->jugador->y][juego->jugador->x];
    antiguaCelda->pieza = NULL;             
    nuevaCelda->pieza = juego->jugador;   

    // Actualizar las coords del rey
    juego->jugador->x = posxJugador;
    juego->jugador->y = posyJugador;

    return true;
}

/*
* Nombre: sgte_nivel
* Parámetros:  Puntero al juego
* Retorno: void
* Descripción: Gestiona avanzar al siguiente nivel. Limpia al jugador en el tablero actual, libera la memoria del tablero viejo, 
* incrementa el contador de nivel y crea un nuevo tablero (8x8 para el 
* nivel 2, 6x6 para nivel 3). Finalmente, hace el pool de enemigos del nivel y los pone en el nuevo tablero.
*/
void sgte_nivel(struct Juego *juego){
    printf("\n ¡Nivel %d completado! Avanzando de nivel \n", juego->nivel_actual);

    Celda *celdaJugador = (Celda*)juego->t->celdas[juego->jugador->y][juego->jugador->x];
    celdaJugador->pieza = NULL; 

    tablero_liberar(juego->t); 

    juego->nivel_actual++;
    int nuevoW = (juego->nivel_actual == 2) ? 8 : 6;
    int nuevoH = (juego->nivel_actual == 2) ? 8 : 6;

    juego->t = tablero_crear(nuevoW, nuevoH); 
    PoolNivel(juego);
            
    spawn_nivel(juego, juego->nivel_actual); 
    printf("====== Nivel %d ======", juego->nivel_actual);
}

int main(){
    Juego juego;
    
    printf("====== Nivel 1 ======\n");
    printf("¡El Rey ha aparecido!\n");
    printf("Moviemiento: WASD | Diagonales: QEZC \n");
    printf("Armas: [1]Escopeta  [2]Sniper  [3]Granada  [4]Especial\n");
    printf("X Salir\n ");

    srand(time(NULL));
    
    juego.t = tablero_crear(12, 12);
    juego.jugador = NULL;
    juego.nivel_actual = 1;
    juego.turno = 1;
    juego.turno_enemigos = 0;
    juego.vivos = 0; 
    juego.derrota = 0;
    juego.piezaJaque = ' ';
    juego.mensaje[0] = '\0';

    juego.arsenal.disparar[0] = escopeta;
    juego.arsenal.municion_actual[0] = 2;
    juego.arsenal.municion_maxima[0] = 2;

    juego.arsenal.disparar[1] = francotirador;
    juego.arsenal.municion_actual[1] = 1;
    juego.arsenal.municion_maxima[1] = 1;

    juego.arsenal.disparar[2] = granada;
    juego.arsenal.municion_actual[2] = 1;
    juego.arsenal.municion_maxima[2] = 1;

    juego.arsenal.disparar[3] = especial; 
    juego.arsenal.municion_actual[3] = 1;
    juego.arsenal.municion_maxima[3] = 1;


    PoolNivel(&juego);
    spawn_nivel(&juego, juego.nivel_actual);

    bool gameover= false;

    while (!gameover){
        
        tablero_imprimir(&juego);
        juego.mensaje[0] = '\0';

        char caracter;
        do{
            printf("Ingrese acción (Movimiento (WASD/QEZC) o Arma): ");
            scanf(" %c", &caracter);
            caracter = tolower(caracter);
            if (caracter != 'w' && caracter != 'a' && caracter != 's' && caracter != 'd' && caracter != 'q' && caracter != 'e' && caracter != 'z' && caracter != 'c' && caracter != '1' && caracter != '2' && caracter != '3' && caracter != '4' && caracter != 'x'){
                printf("Entrada inválida. Por favor ingrese 'w', 'a', 's', 'd', 'q', 'e', 'z', 'c', '1', '2', '3', '4' o 'X'\n");
            }
        }while (caracter != 'w' && caracter != 'a' && caracter != 's' && caracter != 'd' && caracter != 'q' && caracter != 'e' && caracter != 'z' && caracter != 'c' && caracter != '1' && caracter != '2' && caracter != '3' && caracter != '4' && caracter != 'x');

        // Para salir
        if (caracter == 'x'){
            break; 
        }

        bool accion = false;
        if (caracter >= '1' && caracter <= '4'){
            int arma_id = caracter - '1'; // '1'es id 0 (escopeta), '2'es id 1(sniper) etc
            bool disparo_exitoso = disparar_armas(&juego, arma_id);
            if (!disparo_exitoso){ 
                printf("DEBUG: El disparo FALLO (municion o direccion invalida)\n"); 
                continue; 
            }
            
            resolverDanos(&juego); 
            accion = true;

        }else{
            accion = mover_jugador(&juego, caracter);
            for (int i = 0; i < 4; i++){
                if (juego.arsenal.municion_actual[i] < juego.arsenal.municion_maxima[i]){
                    juego.arsenal.municion_actual[i]++;
                }
            }
            //Si ya esta en el borde y se mueve no avanza el turno 
            if (!accion){
                sprintf(juego.mensaje,"No puedes salirte del tablero\n");
                continue;
            }
        }
        if (accion){
            
            juego.turno_enemigos++;
            juego.turno++;
            mover_enemigos(&juego);
            
            if (verificar_estado_rey(&juego)) {
                juego.derrota = 1;
                
            }

            if (juego.derrota == 1){
                tablero_imprimir(&juego);
                printf("¡JAQUE MATE! El Rey ha sido capturado por %c.\n", juego.piezaJaque);
                gameover = true; 

            }else if (juego.vivos <= 0){
                if (juego.nivel_actual < 3){
                    
                    sgte_nivel(&juego);
                }else{
                    tablero_imprimir(&juego);
                    printf("\n¡VICTORY ROYALE!\n");
                    gameover = true;
                }
            }
            
        }
    }
    tablero_liberar(juego.t);
    return 0;
}