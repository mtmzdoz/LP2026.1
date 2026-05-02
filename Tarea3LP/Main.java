import Entidades.Jugador;

import Mapa.Sector7;
import Mapa.Gongaga;
import Mapa.NucleoPlaneta;
import Mapa.Zona;

import java.util.Scanner;

public class Main{
    /*
    * Programa principal.
    * Inicia el juego, crea las zonas, el jugador, la nave y controla el flujo del menú principal.
    * @param args: String[] - argumentos de línea de comando (no utilizados).
    * @return void
    */
    public static void main(String[] args){
        Scanner Scan = new Scanner(System.in); //para los inputs

        Sector7 sector7 = new Sector7();
        Gongaga gongaga = new Gongaga();
        NucleoPlaneta nucleoPlaneta = new NucleoPlaneta();
        Jugador cloud = new Jugador(sector7);
        Zona zonaActual = sector7; 

        System.out.println("=== Inicio del juego ===");
        boolean Jugando = true;

        while (Jugando){
            System.out.println("\n--- Zona Actual: " + zonaActual.nombre + " ---");
            System.out.println("1. Explorar zona (Acción de Zona)");
            System.out.println("2. Ver estadísticas");
            System.out.println("0. Salir del juego");
            System.out.print("Elige una opción: ");

            int opcion = -1;
            try{
                //Leer el input y lo convierte a int
                opcion = Integer.parseInt(Scan.next());
            }catch (Exception e){
                System.out.println("Entrada inválida. Usa solo números.");
                continue; 
            }

            switch (opcion){
                case 1:
                    cloud.getZonaActual().accionZona(cloud);
    
                    break;

                case 2:
                    System.out.println("\n--- Estadísticas de " + cloud.nombre + " ---");
                    System.out.println("Nivel: " + cloud.getNivel());
                    break;

                case 0:
                    System.out.println("Saliendo del juego...");
                    Jugando = false;
                    break;

                default:
                    System.out.println("Opción inválida. Intenta de nuevo.");
                    break;
            }
        }     
        Scan.close();
    } 
}