#include <stdio.h>
#include <stdlib.h>  
#include <stdbool.h>
#include <ctype.h>
#include <time.h>    //Para el time del srand 
#include "main.h"
#include "tablero.h"

int main(){
    Juego juego;
    
    printf("====== Nivel 1 ======\n");
    printf("¡El Rey ha aparecido!\n");
    printf("Moviemiento: WASD | Diagonales: QEZC \n");
    printf("Armas: [1]Escopeta  [2]Sniper  [3]Granada  [4]Especial\n");
    printf("X Salir\n ");
    
    juego.t = tablero_crear(12, 12);
  

    

    bool gameover= false;

    
    while (!gameover){
        
        tablero_imprimir(&juego);
 
        char caracter;
        do{
            printf("INgrese acción (Movimiento o Arma): ");
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
        
        
        if (juego.derrota == 1){
            tablero_imprimir(&juego); 
            printf("¡Derrota! Un alien llegó a la base. \n");
            gameover= true;
            
        }else{
            tablero_imprimir(&juego);
            printf("¡VICTORY ROYALE!\n");
            gameover= true;
        }
     
    }
    tablero_liberar(juego.t);
    return 0;
    
}