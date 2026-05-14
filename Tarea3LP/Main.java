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
        Gongaga gongaga = new Gongaga(sector7);
        NucleoPlaneta nucleoPlaneta = new NucleoPlaneta(sector7);
        Jugador cloud = new Jugador(sector7);
        Zona zonaActual = sector7; 
        
        


        System.out.println("=== Inicio del juego ===");
        boolean Jugando = true;

        while (Jugando){
            zonaActual = cloud.getZonaActual();
            //int materiasActuales = cloud.getBusterSword().getMateriasEquipadas().size();
            System.out.println("================================");
            System.out.println("Zona Actual: " + zonaActual.nombre + " | Nivel: " + cloud.getNivel());
            System.out.println("HP: " + cloud.getStats().getHpActual() + "/" + cloud.getStats().getHpMaximo() + " | XP: " + cloud.getXpActual() + "/" + (10 * cloud.getNivel()) + "\nMP: " + cloud.getStats().getMpActual() + "/" + cloud.getStats().getMpMaximo() + " | Materias Equipadas: " + cloud.getBusterSword().getMateriasEquipadas().size()  + "/5");
            System.out.println("================================");
            System.out.println("1. Explorar zona (Acción de Zona)");
            System.out.println("2. Viajar a Gongaga");
            System.out.println("3. Viajar a Núcleo del Planeta");
            System.out.println("4. Ver estadísticas de " + cloud.nombre);
            System.out.println("5. Ver mochila");
            System.out.println("0. Salir del juego");
            System.out.print("Elige una opción: ");


            int opcion = -1;
            try{
                //Leer el input y lo convierte a int
                opcion = Integer.parseInt(input.nextLine());
            }catch (Exception e){
                System.out.println("Entrada inválida. Usa solo números porfavor.");
                continue; 
            }

            switch (opcion){
                case 1:
                    cloud.getZonaActual().accionZona(cloud);
    
                    break;

                case 2:
                    if (cloud.getNivel() < gongaga.getNivelRequerido()){
                        System.out.println("\nNivel " + gongaga.getNivelRequerido() + " requerido.");
                        System.out.println("Nivel actual: " + cloud.getNivel());
                    }else{
                        cloud.setZonaActual(gongaga);
                        System.out.println("Viajando a " + gongaga.nombre + "...");
                        cloud.getZonaActual().accionZona(cloud);
    
                    }
                
                    break;
                case 3:
                    if (cloud.getNivel() < nucleoPlaneta.getNivelRequerido()){
                        System.out.println("\nNivel " + nucleoPlaneta.getNivelRequerido() + " requerido.");
                        System.out.println("Nivel actual: " + cloud.getNivel());
                    }else{
                        cloud.setZonaActual(nucleoPlaneta);
                        System.out.println("Viajando a " + nucleoPlaneta.nombre + "...");
                        cloud.getZonaActual().accionZona(cloud);

                    }
                    break;
                case 4:
                    System.out.println("\n--- Estadísticas ---");
                    System.out.println("Fuerza: " + cloud.getStats().getFuerza() + " | Magia: " + cloud.getStats().getMagia());
                    System.out.println("Arma equipada: " + cloud.getBusterSword().nombre);
                    System.out.println("Presiona Enter para volver al menú principal.");
                    input.nextLine(); // Pausa para que el jugador pueda leer las estadísticas antes de volver al menú
                    
                    break;
                case 5:
                    cloud.verMochila(input);
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