import Entidades.Jugador;

import Mapa.Sector7;
import Mapa.Gongaga;
import Mapa.NucleoPlaneta;
import Mapa.Zona;

import java.util.Scanner;

public class Main{
    /*
    * Programa principal. Inicia el juego, crea las zonas, el jugador  y controla el flujo del menú principal.
    * @param args: String[] 
    * @return void
    */
    public static void main(String[] args){
        Scanner input = new Scanner(System.in); 

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
            System.out.println("Limite: " + cloud.getLimiteActual() + "/100");
            System.out.println("================================");
            System.out.println("1. Explorar zona");
            System.out.println("2. Viajar a Gongaga");
            System.out.println("3. Viajar a Núcleo del Planeta");
            System.out.println("4. Ver estadísticas de " + cloud.nombre);
            System.out.println("5. Ver mochila");
            System.out.println("6. Descansar en la posada (Recuperar HP y MP)");
            System.out.println("0. Salir del juego");
            System.out.print("Elige una opción: ");

            int opcion = -1;
            try{
                //Leer el input y lo convierte a int
                opcion = Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Entrada inválida. Intenta de nuevo.");
                continue; 
            }

            switch (opcion){
                case 1:
                    cloud.getZonaActual().accionZona(cloud);
                    break;

                case 2:
                    if (gongaga.validarAcceso(cloud)){
                        cloud.setZonaActual(gongaga);
                        System.out.println("Viajando a " + gongaga.nombre + "...\n");
                        cloud.getZonaActual().accionZona(cloud);
                    }
                    break;

                case 3:
                    if (nucleoPlaneta.validarAcceso(cloud)){
                        cloud.setZonaActual(nucleoPlaneta);
                        System.out.println("Viajando a " + nucleoPlaneta.nombre + "...\n");
                        cloud.getZonaActual().accionZona(cloud);
                    }
                    break;

                case 4:
                    System.out.println("\n--- Estadísticas ---");
                    System.out.println("Fuerza: " + cloud.getStats().getFuerza() + " | Magia: " + cloud.getStats().getMagia());
                    System.out.println("Arma equipada: " + cloud.getBusterSword().nombre);
                    System.out.println("Presiona Enter para volver al menú principal.");
                    input.nextLine(); // Pausa
                    break;

                case 5:
                    cloud.verMochila(input);
                    break;

                case 6:
                    cloud.getStats().setHpActual(cloud.getStats().getHpMaximo());
                    cloud.getStats().setMpActual(cloud.getStats().getMpMaximo());
                    System.out.println("\nZzz... Cloud descansa en la posada.");
                    System.out.println("HP y MP restaurados al máximo.");
                    System.out.println("\nPresiona Enter para continuar");
                    input.nextLine();
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