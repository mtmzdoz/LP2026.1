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
        Scanner input = new Scanner(System.in); //para los inputs

        Sector7 sector7 = new Sector7();
        Gongaga gongaga = new Gongaga();
        NucleoPlaneta nucleoPlaneta = new NucleoPlaneta();
        Jugador cloud = new Jugador(sector7);
        Zona zonaActual = sector7; 

        sector7.setZonaAnterior(null);
        sector7.setZonaSiguiente(gongaga);
        
        gongaga.setZonaAnterior(sector7);
        gongaga.setZonaSiguiente(nucleoPlaneta);

        nucleoPlaneta.setZonaAnterior(gongaga);
        nucleoPlaneta.setZonaSiguiente(null);
        
        


        System.out.println("=== Inicio del juego ===");
        boolean Jugando = true;

        while (Jugando){
            System.out.println("\n--- Zona Actual: " + zonaActual.nombre + " ---");
            System.out.println("Nivel: " + cloud.getNivel() + " | XP: " + cloud.getXpActual() + "/" + (10 * cloud.getNivel()) + " | HP: " + cloud.getStats().getHpActual() + "/" + cloud.getStats().getHpMaximo() + " | MP: " + cloud.getStats().getMpActual() + "/" + cloud.getStats().getMpMaximo() + " | Fuerza: " + cloud.getStats().getFuerza() + " | Magia: " + cloud.getStats().getMagia() + " | Chatarra: " + cloud.getChatarra());
            System.out.println("1. Explorar zona (Acción de Zona)");
            System.out.println("2. Avanzar a zona: " + zonaActual.getZonaSiguiente().nombre);
            System.out.println("0. Salir del juego");
            System.out.print("Elige una opción: ");

            int opcion = -1;
            try{
                //Leer el input y lo convierte a int
                opcion = Integer.parseInt(input.nextLine());
            }catch (Exception e){
                System.out.println("Entrada inválida. Usa solo números.");
                input.nextLine();
                continue; 
            }

            switch (opcion){
                case 1:
                    cloud.getZonaActual().accionZona(cloud);
    
                    break;

                case 2:
                    Zona siguiente = cloud.getZonaActual().getZonaSiguiente();
                    if (siguiente != null) {
                    // Aquí aplicas la validación de nivel que querías
                        if (cloud.getNivel() < siguiente.getNivelRequerido()) {
                            System.out.println("\nNivel " + siguiente.getNivelRequerido() + " requerido.");
                            System.out.println("Nivel actual: " + cloud.getNivel());
                        } else {
                            cloud.setZonaActual(siguiente);
                            System.out.println("Viajando a " + siguiente.nombre + "...");
                        }
                    } else {
                        System.out.println("No hay más zonas");
                    }
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
        input.close();
    } 
}