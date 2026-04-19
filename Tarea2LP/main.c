#include <stdio.h>
#include <stdlib.h>  
#include <stdbool.h>
#include <ctype.h>
#include <time.h>    //Para el time del srand 
#include "main.h"
#include "tablero.h"


bool mover_jugador(Juego *juego, char direccion){ 
    int W = juego->t->W;
    int H = juego->t->H;
    int posx = juego->jugador->x;
    int posy = juego->jugador->y;
    


    direccion = tolower(direccion);
    if(direccion == 'w' && posy < H-1){
        posy++;
    }else if(direccion == 's' && posy > 0){
        posy--;
    }else if(direccion == 'a' && posx > 0){
        posx--;
    }else if(direccion == 'd' && posx < W - 1){
        posx++;
    }else if(direccion == 'q' && posx > 0 && posy < H - 1){
        posx--;
        posy++;
    }else if(direccion == 'e' && posx < W - 1 && posy < H - 1){
        posx++;
        posy++; 
    }else if(direccion == 'z' && posx > 0 && posy > 0){
        posx--;
        posy--;
    }else if(direccion == 'c' && posx < W - 1 &&  posy > 0){
        posx++;
        posy--; 
    }

    // 2. Comprobar si las coordenadas realmente cambiaron 
    // (Si intentó ir contra una pared o apretó una tecla inválida, posx y posy no cambian)
    if (posx == juego->jugador->x && posy == juego->jugador->y) {
        return false;
    }

    // 3. Validar que la nueva celda no esté ocupada por un enemigo
    Celda *nueva_celda = (Celda*)juego->t->celdas[posy][posx];
    if (nueva_celda->pieza != NULL) {
        return false; 
    }

    // 4. ACTUALIZAR LA MATRIZ BIDIOMENSIONAL (Fundamental)
    Celda *vieja_celda = (Celda*)juego->t->celdas[juego->jugador->y][juego->jugador->x];
    vieja_celda->pieza = NULL;             // Dejamos la casilla anterior vacía
    nueva_celda->pieza = juego->jugador;   // Ponemos el puntero del Rey en la nueva casilla

    // 5. ACTUALIZAR LAS COORDENADAS INTERNAS DEL REY
    juego->jugador->x = posx;
    juego->jugador->y = posy;

    return true;
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
    juego.nivel_actual = 1;
    juego.turno = 1;
    juego.vivos = 0; // Muy importante empezar en 0 antes de contar a los enemigos
    juego.derrota = 0;

    PoolNivel(&juego);
    spawn_nivel(&juego, juego.nivel_actual);

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

    bool gameover= false;

    
    while (!gameover){
        
        tablero_imprimir(&juego);
 
        char caracter;
        do{
            printf("Ingrese acción (Movimiento (WASD/QEZC) o Arma): ");
            scanf(" %c", &caracter);
            caracter = tolower(caracter);
            if (caracter != 'w' && caracter != 'a' && caracter != 's' && caracter != 'd' && caracter != 'q' && caracter != 'e' && caracter != 'z' && caracter != 'c' && caracter != '1' && caracter != '2' && caracter != '3' && caracter != '4' && caracter != 'x'){
                printf("Entrada inválida. Por favor ingrese 'w', 'a', 's', 'd', 'q', 'e', 'z', 'c', '1', '2', '3', '4' o 'X'\n");
            }
        }while (caracter != 'w' && caracter != 'a' && caracter != 's' && caracter != 'd' && caracter != 'q' && caracter != 'e' && caracter != 'z' && caracter != 'c' && caracter != '1' && caracter != '2' && caracter != '3' && caracter != '4' && caracter != 'x');

        // termina juego
        if (caracter == 'x'){
            break; 
        }

        bool accion = false;
        if (caracter >= '1' && caracter <= '4'){
            int arma_id = caracter - '1';
            printf("DEBUG: Entrando a disparar_armas con ID %d\n", arma_id); // LOG 1

            bool disparo_exitoso = disparar_armas(&juego, arma_id);
            if (!disparo_exitoso) { 
                printf("DEBUG: El disparo FALLO (municion o direccion invalida)\n"); // LOG 2
                continue; 
            }
            
            // Magia encapsulada: El main no sabe CÓMO se resuelve el daño, solo pide que se haga.
            printf("DEBUG: Disparo exitoso, llamando a resolver_danos\n"); // LOG 3
            resolver_danos(&juego); 
            
            accion = true;
        }else{
            accion = mover_jugador(&juego, caracter);
            if (accion && juego.arsenal.municion_actual[0] < juego.arsenal.municion_maxima[0]) {
                juego.arsenal.municion_actual[0]++;
                sprintf(juego.mensaje, "¡Movimiento! Municion recargada (+1).");
            }
            //Si ya esta en el borde y se mueve no avanza el turno 
            if (!accion){
                continue;
            }
        }
        if (accion) {
            mover_enemigos(&juego);
    
            // AQUÍ REVISAMOS LA DERROTA
            if (juego.derrota == 1) {
                tablero_imprimir(&juego);
                printf("\n¡JAQUE MATE! El Rey ha sido capturado.\n");
                gameover = true; // Esto romperá el bucle
            }
        } else {
            printf("Acción inválida, no consumes turno.\n");
        }
      
    }
    tablero_liberar(juego.t);
    return 0;
    
}