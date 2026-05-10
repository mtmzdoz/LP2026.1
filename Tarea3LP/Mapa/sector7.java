package Mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


import Componentes.Mejora;
import Entidades.Jugador;
import Entidades.Enemigo;
import Entidades.EnemigoSimulador;

public class Sector7 extends Zona{
    private List<Mejora> tiendaLocal;


    public Sector7() {
        super("Sector 7", 1, null); //Null por mientras pero deberia ser soldado común
        this.tiendaLocal = new ArrayList<>();
        
    }

    public void iniciarSimulador(Jugador Cloud){
        List<Enemigo> enemigosSimulados = EnemigoSimulador.generarSimulados();
        Scanner input = new Scanner(System.in);

        System.out.println("\n--- SIMULADOR DE COMBATE ---");

        while (Cloud.getStats().getHpActual() > 0 && enemigosVivos(enemigosSimulados)) {
            System.out.println("Debug: iniciar simulador en Sector7java");
            System.out.println("HP Cloud: " + Cloud.getStats().getHpActual());
            for (int i = 0; i < enemigosSimulados.size(); i++) {
                Enemigo enemigo = enemigosSimulados.get(i);
                if (enemigo.getStats().getHpActual() > 0) {
                    System.out.println("-" + enemigo.nombre + " HP: " + enemigo.getStats().getHpActual());
                }
            }
            System.out.println("1. Ataque Físico");
            System.out.print("Elige tu movimiento: ");

            String opcion = input.nextLine();

            if (opcion.equals("1")){
                Enemigo enemigoAtacar = null;
                for (Enemigo enemigo : enemigosSimulados) {
                    if (enemigo.getStats().getHpActual() > 0) {
                        enemigoAtacar = enemigo;
                        break; 
                    }
                }
                if (enemigoAtacar != null) {
                    Cloud.atacar(enemigoAtacar); // Aquí se aplica Fuerza * 1.25
                }

                for (Enemigo enemigo : enemigosSimulados) {
                     // Solo atacan si están vivos y si Cloud aún tiene > 1 HP
                    if (enemigo.getStats().getHpActual() > 0 && Cloud.getStats().getHpActual() > 1) {
                        enemigo.atacar(Cloud); // Llama al método que pusiste arriba
                    }
                }

            }else{
                System.out.println("Opción no válida, pierdes el turno.");
            }
        }

        // 4. Fin del combate
        if (Cloud.getStats().getHpActual() > 1) {
            System.out.println("\n¡Victoria! Simulacro exitoso.");

            int xpTotalCombate = 0;
            Random rnd = new Random();
            for (Enemigo enemigo : enemigosSimulados) {
                int xpEnemigo = rnd.nextInt(6) + 15; 
                xpTotalCombate += xpEnemigo;
            }
            Cloud.recibirXP(xpTotalCombate);
        }else{
            System.out.println("\nHas sido retirado del simulador.");
        }   

    }

    public void abrirTienda(Jugador Cloud){
        
    }

    @SuppressWarnings("resource") // Esto quita el aviso del IDE
    public void accionZona(Jugador Cloud){
        Scanner input = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- Menú Sector 7 ---");
            System.out.println("1. Entrar al simulador de combate");
            System.out.println("2. Abrir Tienda de Chatarra (Comprar mejoras)"); 
            System.out.println("3. Volver al menú de viaje");
        
            int opcion = -1;
            try {
                opcion = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                System.out.println("Entrada inválida. Usa solo números.");
                input.nextLine();
            }

            if (opcion == 1){
                iniciarSimulador(Cloud);
            }else if (opcion == 2){
                abrirTienda(Cloud);
            }else if (opcion == 3){
                salir = true;
            }else{
                System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    public boolean enemigosVivos(List<Enemigo> lista){
        for (Enemigo enemigo : lista){
            if (enemigo.getStats().getHpActual() > 0 ){
                return true;
            }
        }
        return false;
    }
}
