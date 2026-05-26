package Mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Componentes.Elemento;
import Componentes.Materia;
import Entidades.Enemigo;
import Entidades.EnemigoSalvaje;
import Entidades.Jugador;

public class Gongaga extends Zona{
    private List<Materia> poolMaterias;

    /*
    * Constructor. Inicializa el nombre, nivel requerido, enemigos disponibles y zona de retorno
    * y carga los artículos disponibles en la tienda de chatarra.
    * @param Ninguno
    * @return Ninguno
    */
    public Gongaga(Zona retorno){
        super("Gongaga", 5, null, retorno);
        this.poolMaterias = new ArrayList<>();
        this.poolMaterias.add(new Materia(Elemento.FUEGO));
        this.poolMaterias.add(new Materia(Elemento.HIELO));
        this.poolMaterias.add(new Materia(Elemento.RAYO));
        this.poolMaterias.add(new Materia(Elemento.CURA));
    }

    /*
    * Genera aleatoriamente un grupo de enemigos (1 a 3) basándose en 
    * probabilidades y asigna tipos de enemigos con sus debilidades.
    * @param Ninguno
    * @return List<Enemigo>
    */
    public List<Enemigo> generarGrupoEnemigo(){
        List<Enemigo> grupoEnemigos = new ArrayList<>();
        Random random = new Random();

        int probabilidad = random.nextInt(100); 
        int cantidadEnemigos;
        if (probabilidad < 60){
            cantidadEnemigos = 1;      // 60%
        }else if (probabilidad < 90){
            cantidadEnemigos = 2; // 30%
        }else{
            cantidadEnemigos = 3;  // 10%
        }                     

        for (int i = 0; i < cantidadEnemigos; i++){
            int TipoEnemigo = random.nextInt(3);
            EnemigoSalvaje NuevoEnemigo;
            
            switch (TipoEnemigo){
                case 0:
                    NuevoEnemigo = new EnemigoSalvaje("Planta Carnívora", 80, 15);
                    NuevoEnemigo.adddebilidad(Elemento.FUEGO);
                    NuevoEnemigo.adddebilidad(Elemento.HIELO);
                    NuevoEnemigo.addinmunidad(Elemento.RAYO);
                    break;

                case 1:
                    NuevoEnemigo = new EnemigoSalvaje("Sapo de la Jungla", 60, 12);
                    NuevoEnemigo.adddebilidad(Elemento.RAYO);
                    NuevoEnemigo.adddebilidad(Elemento.HIELO);
                    NuevoEnemigo.addresistencia(Elemento.FUEGO);
                    break;

                default:
                    NuevoEnemigo = new EnemigoSalvaje("Robot Centinela", 100, 20);
                    NuevoEnemigo.adddebilidad(Elemento.RAYO);
                    NuevoEnemigo.addresistencia(Elemento.FISICO);
                    NuevoEnemigo.addresistencia(Elemento.HIELO);
                    break;
            }
            grupoEnemigos.add(NuevoEnemigo);
        }
        return grupoEnemigos;
    }

    /*
    * Ejecuta la lógica del combate final por turnos contra Sephiroth. Gestiona los
    * ataques del jugador y las condiciones de victoria o derrota.
    * @param Cloud: Jugador
    * @return void
    */
    @Override
    public boolean validarAcceso(Jugador Cloud){
        boolean nivelSuficiente = Cloud.getNivel() >= this.nivelRequerido;
        if (!nivelSuficiente){
            System.out.println("Requisitos para " + this.nombre + ": Nivel " + this.nivelRequerido + ".\n");
            return false;
        }
        return true;
    }

    /*
    * Gestiona el bucle de interacción de la zona, permitiendo al jugador explorar
    * para encontrar materias, luchar contra enemigos o salir de la zona.
    * @param Cloud: Jugador
    * @return void
    */
    @Override
    public void accionZona(Jugador Cloud){
        Random random = new Random();
        Scanner input = new Scanner(System.in);
        boolean salir = false;

        while (!salir && Cloud.getStats().getHpActual() > 0 && Cloud.getZonaActual() == this){
            System.out.println("==== Menú de Gongaga ==== ");
            System.out.println("Nivel: " + Cloud.getNivel() + " | Limite: " + Cloud.getLimiteActual() + "/100");
            System.out.println("HP: " + Cloud.getStats().getHpActual() + "/" + Cloud.getStats().getHpMaximo() + " | XP: " + Cloud.getXpActual() + "/" + (10 * Cloud.getNivel()) + "\nMP: " + Cloud.getStats().getMpActual() + "/" + Cloud.getStats().getMpMaximo() + " | Materias Equipadas: " + Cloud.getBusterSword().getMateriasEquipadas().size()  + "/5");
            System.out.println("================================");
            System.out.println("1. Explorar la jungla (Acción de Zona)");
            System.out.println("2. Volver a Sector 7");

            int opcion = -1;
            try{
                opcion = Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Entrada inválida. Intenta de nuevo.");
                continue;
            }
            if (opcion == 1){
                double evento = random.nextDouble(); 
                if (evento < 0.30){
                    buscarMateria(Cloud);
                }else{
                    ataqueEnemigo(Cloud);
                }
                
            }else if (opcion == 2){
                System.out.println("Regresando a Sector 7...\n");
                Cloud.setZonaActual(this.zonaRetorno);
                salir = true;
            }else{
                System.out.println("Opción no válida. Intenta de nuevo.");
            }

            
        }
    }

    /*
    * Controla el sistema de combate por turnos cuando ocurre una emboscada,
    * permitiendo ataques, uso de magia y otorgando recompensas al finalizar.
    * @param Cloud: Jugador
    * @return void
    */
    public void ataqueEnemigo(Jugador Cloud){
        List<Enemigo> enemigos = generarGrupoEnemigo();
        Scanner input = new Scanner(System.in);
        System.out.println("\n--- Combate en Gongaga ---");
        if (enemigos.size() == 1){

            System.out.println("¡EMBOSCADA! Un ha aparecido.");
        }else{
            System.out.println("¡EMBOSCADA! Han aparecido " + enemigos.size() + " enemigos.");
        }

        while (Cloud.getStats().getHpActual() > 0 && enemigosVivos(enemigos)){
            boolean turnoCloud = false;

            while (!turnoCloud){
                System.out.println("\n--- Turno Cloud ---");
                System.out.println("HP actual: " + Cloud.getStats().getHpActual() + "/" + Cloud.getStats().getHpMaximo());
                for (int i = 0; i < enemigos.size(); i++) {
                    Enemigo enemigo = enemigos.get(i);
                    if (enemigo.getStats().getHpActual() > 0) {
                        System.out.println("-" + enemigo.nombre + " (HP: " + enemigo.getStats().getHpActual() + ")");
                    }
                }
            
                System.out.println("1. Ataque Físico");
                System.out.println("2. Ataque Mágico");
                System.out.println("3. Ataque Límite");
                System.out.println("0. Huir del combate");
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
                        Enemigo enemigoATKFIS = seleccionarEnemigo(enemigos, input);
                        if (enemigoATKFIS != null) {
                            Cloud.atacar(enemigoATKFIS);
                            turnoCloud = true;
                        }
                        break;

                    case 2:
                        List<Materia> materias = Cloud.getBusterSword().getMateriasEquipadas();
                        if (materias.isEmpty()) {
                            System.out.println("No tienes materias equipadas.");
                            continue; 
                        }

                        System.out.println("\n--- Selecciona Materia ---");
                        for (int i = 0; i < materias.size(); i++) {
                        System.out.println((i + 1) + ". " + materias.get(i).getElemento());
                        }

                        System.out.println("0. Volver");
                        int materiaElegida = -1;

                        try{ 
                            materiaElegida = Integer.parseInt(input.nextLine()) - 1;
                        }catch (NumberFormatException e){ 
                            System.out.println("Entrada inválida. Intenta de nuevo.");
                        }

                        if (materiaElegida == -1){
                            continue;
                        } 

                        if (materiaElegida >= 0 && materiaElegida < materias.size()){
                            Elemento elemento = materias.get(materiaElegida).getElemento();
                            if (elemento == Elemento.CURA){
                                Cloud.atacarMagia(elemento, null);
                                turnoCloud = true;
                            }else{
                                Enemigo enemigo = seleccionarEnemigo(enemigos, input);
                                if (enemigo != null){
                                    Cloud.atacarMagia(elemento, enemigo);
                                    turnoCloud = true;
                                }
                            }
                        }
                        break;
                    case 3:
                        if (Cloud.getLimiteActual() >= 100){
                            Enemigo objetivoLim = seleccionarEnemigo(enemigos, input);
                            if (objetivoLim != null){
                                Cloud.ataqueLimite(objetivoLim);
                                turnoCloud = true;
                            }
                        }else{
                            System.out.println("¡Límite no listo! (" + Cloud.getLimiteActual() + "/100)\n");
                        }
                        break;

                    case 0:
                        Random probabilidad = new Random();
                        if (probabilidad.nextInt(100) < 50){
                            System.out.println("Intentas retirarte del simulador, pero un enemigo te ataca mientras lo haces...");
                            turnoCloud = true;
                        }else{
                            System.out.println("Huyes del comnbate...");
                            return;
                        }
                        break;

                    default:
                        System.out.println("Opción no válida. Intenta de nuevo.");
                        break; 
                        
                }       
            }       
           
            if (enemigosVivos(enemigos) && Cloud.getStats().getHpActual() > 0){
                System.out.println("\n--- TURNO ENEMIGO ---");
                turnoEnemigos(enemigos, Cloud);
            }
        }
       
        if (Cloud.getStats().getHpActual() <= 0){
            System.out.println("Cloud ha caído en la jungla de Gongaga...");
            System.out.println("Vuelves al inicio de todo");
            Cloud.derrota(this.zonaRetorno);
            return;
            
        }else if (Cloud.getStats().getHpActual() > 0){ 
            System.out.println("\n--- RECOMPENSAS DE LA BATALLA ---");
            boolean enemigoDerrotado = false;

            for (Enemigo enemigo : enemigos){
                if (enemigo.getStats().getHpActual() <= 0){
                    System.out.println("Recompensa de " + enemigo.nombre + ":");
                    enemigo.giveXpRecompensa(Cloud);
                    enemigo.giveChatarraRecompensa(Cloud);
                
                    enemigoDerrotado = true;
                    System.out.println(); 
                }
            }

            if (!enemigoDerrotado){
                System.out.println("No lograste derrotar a ningún enemigo.");
            }
        }
    }

    /*
    * Intenta obtener una materia aleatoria del pozo de materias local y la añade al inventario (mochila) del jugador.
    * @param Cloud: Jugador
    * @return void
    */
    private void buscarMateria(Jugador Cloud){
        if (poolMaterias.isEmpty()){
            System.out.println("Exploras, pero no encuentras nada esta vez.");
            return;
        }

        Random rand = new Random();
        int elemElegido = rand.nextInt(poolMaterias.size());

        Materia materiaEncontrada = poolMaterias.get(elemElegido);
        Materia nuevaMateria = new Materia(materiaEncontrada.getElemento());
        Cloud.getMochila().add(nuevaMateria);
    
        System.out.println("\n¡Explorando la maleza has encontrado una Materia de " + nuevaMateria.getElemento() + "!");
        System.out.println("Se ha guardado en tu mochila.\n");
    }
}
