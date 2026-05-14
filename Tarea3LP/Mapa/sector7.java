package Mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Componentes.Elemento;
import Componentes.Materia;
import Componentes.Mejora;
import Entidades.Jugador;
import Entidades.Enemigo;
import Entidades.EnemigoSimulador;

public class Sector7 extends Zona{
    private List<Mejora> tiendaLocal;


    public Sector7() {
        super("Sector 7", 1, null, null); //Null por mientras pero deberia ser soldado común
        this.tiendaLocal = new ArrayList<>();
        
    }

    public void iniciarSimulador(Jugador Cloud){
        List<Enemigo> enemigosSimulados = EnemigoSimulador.generarSimulados();
        Scanner input = new Scanner(System.in);

        System.out.println("\n--- SIMULADOR DE COMBATE ---");

        while (Cloud.getStats().getHpActual() > 1 && enemigosVivos(enemigosSimulados)) {
            System.out.println("Debug: iniciar simulador en Sector7java");
            System.out.println("HP Cloud: " + Cloud.getStats().getHpActual() + "/" + Cloud.getStats().getHpMaximo());
            for (int i = 0; i < enemigosSimulados.size(); i++) {
                Enemigo enemigo = enemigosSimulados.get(i);
                if (enemigo.getStats().getHpActual() > 0) {
                    System.out.println("-" + enemigo.nombre + " HP: " + enemigo.getStats().getHpActual());
                }
            }
            System.out.println("1. Ataque Físico");
            System.out.println("2. Ataque Mágico ");
            System.out.println("3. Retirarse del simulador");
            System.out.print("Elige tu movimiento: ");

            int opcion = -1;
            try{
                opcion = Integer.parseInt(input.nextLine());
            }catch (Exception e){
                System.out.println("Entrada inválida. Intenta de nuevo.");
                continue; 
            }

            if (opcion == 1){
                Enemigo enemigoAtacar = null;
                for (Enemigo enemigo : enemigosSimulados) {
                    if (enemigo.getStats().getHpActual() > 0) {
                        enemigoAtacar = enemigo;
                        break; 
                    }
                }
                if (enemigoAtacar != null) {
                    Cloud.atacar(enemigoAtacar); 
                }

                for (Enemigo enemigo : enemigosSimulados) {
                    if (enemigo.getStats().getHpActual() > 0 && Cloud.getStats().getHpActual() > 1) {
                        enemigo.atacar(Cloud); 
                    }
                }

                if (Cloud.getStats().getHpActual() <= 1) {
                    System.out.println("Has sido retirado del simulador.");
                    break;
                    // Aquí el bucle terminará porque la condición (HP > 1) ya no se cumple.
                }
            }else if (opcion == 2){
                List<Materia> materiasEquipadas = Cloud.getBusterSword().getMateriasEquipadas();
                if (materiasEquipadas.isEmpty()){
                    System.out.println("No tienes materias equipadas en " + Cloud.getBusterSword().nombre + ".");
                    continue;
                }
                System.out.println("Elige una materia para usar:");
                for (int i = 0; i < materiasEquipadas.size(); i++){
                    System.out.println((i + 1) + ". Materia de " + materiasEquipadas.get(i).getElemento());
                }
                System.out.println("0. Cancelar y volver");
                System.out.print("Selecciona tu hechizo: ");

                int subOpcion = -1;
                try{
                    subOpcion = Integer.parseInt(input.nextLine());
                        
                }catch (Exception e){
                    System.out.println("Entrada inválida. Intenta de nuevo.");
                    continue;
                }

                if (subOpcion == 0){
                    continue;
                }
                int indiceMateriaElegida = subOpcion - 1;
                if (indiceMateriaElegida >= 0 && indiceMateriaElegida < materiasEquipadas.size()){
                    Materia materiaUsada = materiasEquipadas.get(indiceMateriaElegida);
                    Elemento elemento = materiaUsada.getElemento();

                    Enemigo objetivo = null;
                    for (Enemigo enemigo : enemigosSimulados) {
                        if (enemigo.getStats().getHpActual() > 0) {
                            objetivo = enemigo;
                            break;
                        }
                    }
                    if (objetivo != null) {
                        // 2. Calculamos daño y costo según tus fórmulas
                        int costoMP = Cloud.getBusterSword().calcularCostoMP(elemento);

                        if (Cloud.getStats().getMpActual() < costoMP) {
                            System.out.println("¡MP Insuficiente! Necesitas " + costoMP + " MP.");
                            continue;
                        }

                        int potencia = Cloud.getBusterSword().calcularDanoMagico(elemento, objetivo);

                        if (elemento == Elemento.CURA) {
                            int hpAntes = Cloud.getStats().getHpActual();
                            int hpMax = Cloud.getStats().getHpMaximo();
                            Cloud.getStats().setHpActual(Math.min(hpMax, hpAntes + potencia));
                            System.out.println("\n¡Cloud usa CURA y recupera " + (Cloud.getStats().getHpActual() - hpAntes) + " HP!");
                        } else {
                            objetivo.getStats().recibirDMG(potencia);
                            System.out.println("\n¡Cloud lanza " + elemento + " y causa " + potencia + " de daño a " + objetivo.nombre + "!");
                        }
                        Cloud.getStats().setMpActual(Cloud.getStats().getMpActual() - costoMP);

                     // 5. Los enemigos responden
                        for (Enemigo enemigo : enemigosSimulados) {
                            if (enemigo.getStats().getHpActual() > 0 && Cloud.getStats().getHpActual() > 1) {
                                enemigo.atacar(Cloud);
                            }
                        }
                    

                    }

                    if (Cloud.getStats().getHpActual() <= 1) {
                        System.out.println("Has sido retirado del simulador.");
                        break;
                    }
                    
                }

            }else if (opcion == 3){
                System.out.println("Te retiras de la simulación...");
                break; 
            }else{
                System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
      
        boolean enemigoDerrotado = false;
        for (Enemigo enemigo : enemigosSimulados) {
            if (enemigo.getStats().getHpActual() <= 0) {
                System.out.println("Recompensa de " + enemigo.nombre + ":");
                enemigo.giveXpRecompensa(Cloud);
                enemigoDerrotado = true;
            }
        }
           
        if (!enemigoDerrotado){
            System.out.println("\nNo has derrotado a ningún enemigo. No hay XP.");
        }
    }

    public void abrirTienda(Jugador Cloud){
        
    }

    @SuppressWarnings("resource") // Esto quita el aviso del input
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
                System.out.println("Entrada inválida. Intenta de nuevo.");
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
