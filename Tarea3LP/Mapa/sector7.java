package Mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Componentes.Elemento;
import Componentes.Materia;
import Componentes.Mejora;
import Componentes.TipoStat;
import Entidades.Jugador;
import Entidades.Enemigo;
import Entidades.EnemigoSimulador;

public class Sector7 extends Zona{
    private List<Mejora> tiendaLocal;

    /*
    * Constructor. Inicializa el nombre, nivel requerido, enemigos disponibles y zona de retorno
    * y carga los artículos disponibles en la tienda de chatarra.
    * @param Ninguno
    * @return Ninguno
    */
    public Sector7(){
        super("Sector 7", 1, null, null); // Null por mientras pero deberia ser soldado común
        this.tiendaLocal = new ArrayList<>();
        this.tiendaLocal.add(new Mejora("Mejora de Vitalidad", TipoStat.HP_MAX, 20, 100));
        this.tiendaLocal.add(new Mejora("Mejora de Éter", TipoStat.MP_MAX, 10, 120));
        this.tiendaLocal.add(new Mejora("Mejora Física", TipoStat.FUERZA, 10, 150));
    }

    /*
    * Verifica si el jugador cumple con los requisitos para ingresar a la zona.
    * @param Cloud: Jugador
    * @return boolean
    */
    @Override
    public boolean validarAcceso(Jugador Cloud){
        return true;
    }

    /*
    * Gestiona el bucle principal de interacción en el Sector 7, mostrando el menú 
    * de opciones que incluye el simulador de combate y la tienda.
    * @param Cloud: Jugador
    * @return void
    */
    @SuppressWarnings("resource") // Esto quita el aviso del input
    public void accionZona(Jugador Cloud){
        Scanner input = new Scanner(System.in);
        boolean salir = false;

        while (!salir){
            System.out.println("\n==== Menú de Sector 7 ==== ");
            System.out.println("Nivel: " + Cloud.getNivel() + " | Limite: " + Cloud.getLimiteActual() + "/100");
            System.out.println("HP: " + Cloud.getStats().getHpActual() + "/" + Cloud.getStats().getHpMaximo() + " | XP: " + Cloud.getXpActual() + "/" + (10 * Cloud.getNivel()) + "\nMP: " + Cloud.getStats().getMpActual() + "/" + Cloud.getStats().getMpMaximo() + " | Materias Equipadas: " + Cloud.getBusterSword().getMateriasEquipadas().size()  + "/5");
            System.out.println("================================");
            System.out.println("1. Entrar al simulador de combate");
            System.out.println("2. Abrir Tienda de Chatarra (Comprar mejoras)");
            System.out.println("3. Volver al menú de viaje");

            int opcion = -1;
            try{
                opcion = Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Entrada inválida. Intenta de nuevo.");
                continue;
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

    /*
    * Ejecuta la lógica del simulador de combate, permite al jugador enfrentarse 
    * a los enemigos simulados para obtener xp.
    * @param Cloud: Jugador
    * @return void
    */
    public void iniciarSimulador(Jugador Cloud){
        List<Enemigo> enemigosSimulados = EnemigoSimulador.generarSimulados();
        Scanner input = new Scanner(System.in);

        System.out.println("\n--- SIMULADOR DE COMBATE ---");

        while (Cloud.getStats().getHpActual() > 1 && enemigosVivos(enemigosSimulados)){
            boolean turnoCloud = false;
            while (!turnoCloud){
                System.out.println("--- Turno Cloud ---");
                System.out.println("HP Cloud: " + Cloud.getStats().getHpActual() + "/" + Cloud.getStats().getHpMaximo());

                for (int i = 0; i < enemigosSimulados.size(); i++){
                    Enemigo enemigo = enemigosSimulados.get(i);
                    if (enemigo.getStats().getHpActual() > 0){
                        System.out.println("-" + enemigo.nombre + " HP: " + enemigo.getStats().getHpActual());
                    }
                }

                System.out.println("1. Ataque Físico");
                System.out.println("2. Ataque Mágico ");
                System.out.println("3. Ataque Límite");
                System.out.println("0. Retirarse del simulador");
                System.out.print("Elige tu movimiento: ");

                int opcion = -1;
                try{
                    opcion = Integer.parseInt(input.nextLine());
                }catch (NumberFormatException e){
                    System.out.println("Entrada inválida. Intenta de nuevo.");
                    continue;
                }
                switch (opcion){
                    case 1:
                        Enemigo objetivo = seleccionarEnemigo(enemigosSimulados, input);
                        if (objetivo != null){
                            Cloud.atacar(objetivo);
                            turnoCloud = true;
                        }
                        break;

                    case 2:
                        List<Materia> materiasEquipadas = Cloud.getBusterSword().getMateriasEquipadas();
                        if (materiasEquipadas.isEmpty()){
                            System.out.println("No tienes materias equipadas.");
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
                        }catch (NumberFormatException e){
                            System.out.println("Entrada inválida. Intenta de nuevo.");
                            continue;
                        }

                        if (subOpcion == 0){
                            continue;
                        }

                        int indiceMateria = subOpcion - 1;
                        if (indiceMateria >= 0 && indiceMateria < materiasEquipadas.size()){
                            Elemento elemento = materiasEquipadas.get(indiceMateria).getElemento();
                            if (elemento == Elemento.CURA){
                                Cloud.atacarMagia(elemento, null);
                                turnoCloud = true;
                            }else{
                                Enemigo enemigo = seleccionarEnemigo(enemigosSimulados, input);
                                if (enemigo != null){
                                    Cloud.atacarMagia(elemento, enemigo);
                                    turnoCloud = true;
                                }
                            }
                        }
                        break;
                    case 3:
                        if (Cloud.getLimiteActual() >= 100) {
                            Enemigo objetivoLim = seleccionarEnemigo(enemigosSimulados, input);
                            if (objetivoLim != null) {
                                Cloud.ataqueLimite(objetivoLim);
                                turnoCloud = true;
                            }
                        } else {
                            System.out.println("¡Límite no listo! (" + Cloud.getLimiteActual() + "/100)\n");
                        }
                        break;
                    case 0:
                        Random probabilidad = new Random();
                        if (probabilidad.nextInt(100) < 50){
                            System.out.println("Intentas retirarte del simulador, pero un enemigo te ataca mientras lo haces...");
                            turnoCloud = true;
                        }else{
                            System.out.println("Te retiras del simulador...");
                            return;
                        }
                        break;
                    default:
                        System.out.println("Opción no válida. Intenta de nuevo.");
                        break;
                }
            }

            if (Cloud.getStats().getHpActual() > 1 && enemigosVivos(enemigosSimulados)){
                System.out.println("\n--- Turno enemigo ---");
                turnoEnemigos(enemigosSimulados, Cloud);
            }
            
            if (Cloud.getStats().getHpActual() <= 1){
                System.out.println("Has sido retirado del simulador.");
                break;
            }

        }

        boolean enemigoDerrotado = false;
        for (Enemigo enemigo : enemigosSimulados){
            if (enemigo.getStats().getHpActual() <= 0){
                System.out.println("Recompensa de " + enemigo.nombre + ":");
                enemigo.giveXpRecompensa(Cloud);
                enemigoDerrotado = true;
            }
        }

        if (!enemigoDerrotado){
            System.out.println("\nNo has derrotado a ningún enemigo. No hay XP.");
        }
    }


    /*
    * Abre la interfaz de la tienda y permite al jugador gastar su chatarra en mejoras de estadísticas.
    * @param Cloud: Jugador
    * @return void
    */
    public void abrirTienda(Jugador Cloud){
        Scanner input = new Scanner(System.in);
        boolean salir = false;
        while (!salir){
            System.out.println("\n--- Tienda de mejoras ---");
            System.out.println("Chatarra disponible: " + Cloud.getChatarra());
            for (int i = 0; i < tiendaLocal.size(); i++){
                Mejora m = tiendaLocal.get(i);
                System.out.println((i + 1) + ". " + m.getNombre() + " (+" + m.getValorBono() + "): " + m.getCostoChatarra() + " chatarra");
            }
            System.out.println("0. Salir");
            System.out.print("¿En qué quieres gastar chatarra?: ");

            int opcion = -1;
            try{
                opcion = Integer.parseInt(input.nextLine());
            }catch (Exception e){
                System.out.println("Entrada inválida. Intenta de nuevo.");
                continue;
            }
            if (opcion == 0){
                salir = true;
            }else if (opcion > 0 && opcion <= tiendaLocal.size()) {
                Mejora seleccionada = tiendaLocal.get(opcion - 1);

                if (Cloud.getChatarra() >= seleccionada.getCostoChatarra()){
                    Cloud.recibirChatarra(-seleccionada.getCostoChatarra());
                    Cloud.aplicarMejora(seleccionada);
                }else{
                    System.out.println("No tienes suficiente chatarra.");
                }
            }else{
                System.out.println("Opción no válida.");
            }
        }
    }
}
