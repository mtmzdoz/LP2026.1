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

    //Constructor
    public Gongaga(Zona retorno) {
        super("Gongaga", 1, null, retorno);
        this.poolMaterias = new ArrayList<>(); 
    }

    public List<Enemigo> generarGrupoEnemigo(){
        List<Enemigo> grupoEnemigos = new ArrayList<>();
        Random random = new Random();

        //Ccuántos enemigos aparecen según las probabilidades 
        int probabilidad = random.nextInt(100); 
        int cantidadEnemigos;
        if (probabilidad < 60){
            cantidadEnemigos = 1;      // 60%
        }else if (probabilidad < 90){
            cantidadEnemigos = 2; // 30%
        }else{
            cantidadEnemigos = 3;  // 10%
        }                     

        // 2. Bucle para crear la cantidad de enemigos definida
        for (int i = 0; i < cantidadEnemigos; i++){
            int TipoEnemigo = random.nextInt(3);
            EnemigoSalvaje NuevoEnemigo;
            
            switch (TipoEnemigo){
                case 0:
                // Planta Carnívora: HP 80, Fuerza 15 [cite: 71, 72]
                    NuevoEnemigo = new EnemigoSalvaje("Planta Carnívora", 80, 15);
                    NuevoEnemigo.adddebilidad(Elemento.FUEGO);
                    NuevoEnemigo.adddebilidad(Elemento.HIELO);
                    NuevoEnemigo.addinmunidad(Elemento.RAYO);
                    break;
                case 1:
                    // Sapo de la Jungla: HP 60, Fuerza 12 [cite: 76, 77]
                    NuevoEnemigo = new EnemigoSalvaje("Sapo de la Jungla", 60, 12);
                    NuevoEnemigo.adddebilidad(Elemento.RAYO);
                    NuevoEnemigo.adddebilidad(Elemento.HIELO);
                    NuevoEnemigo.addresistencia(Elemento.FUEGO);
                    break;
                default:
                    // Robot Centinela: HP 100, Fuerza 20 [cite: 81, 82]
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

    

    @Override
    public void accionZona(Jugador Cloud){
        System.out.println("\n--- EXPLORANDO LA JUNGLA DE GONGAGA ---");
        Random random = new Random();
       
        Scanner input = new Scanner(System.in);
        boolean salir = false;

        while (!salir && Cloud.getStats().getHpActual() > 0) {
            System.out.println("\n--- Menú Gongaga---");
            System.out.println("1. Explorar la jungla (Acción de Zona)");
            System.out.println("2. Volver a Sector 7");

            int opcion = -1;
            try {
                opcion = Integer.parseInt(input.nextLine());

                
            } catch (Exception e) {
                System.out.println("Entrada inválida. Intenta de nuevo.");
            }
            if (opcion == 1){
                double evento = random.nextDouble(); 
                if (evento < 0.30){
                    buscarMateria(Cloud);
                }else{
                    ataqueEnemigo(Cloud);
                }
                
            }else if (opcion == 2){
                System.out.println("Regresando a Sector 7...");
                Cloud.setZonaActual(this.zonaRetorno);
                salir = true;
            }else{
                System.out.println("Opción no válida. Intenta de nuevo.");
            }

            
        }
    }

    
    public void ataqueEnemigo(Jugador Cloud) {
        List<Enemigo> enemigos = generarGrupoEnemigo();
        Scanner input = new Scanner(System.in);
        
        if (enemigos.size() == 1){
            System.out.println("¡EMBOSCADA! Un " + enemigos.size() + " ha aparecido.");
        }else{
            System.out.println("¡EMBOSCADA! Han aparecido " + enemigos.size() + " enemigos.");
        }

       
        while (Cloud.getStats().getHpActual() > 0 && enemigosVivos(enemigos)){
            boolean atacarObjetivo = false;
            // 1. TURNO DE CLOUD
            while (!atacarObjetivo){
                System.out.println("\n--- Turno Cloud ---");
                System.out.println("HP actual: " + Cloud.getStats().getHpActual() + "/" + Cloud.getStats().getHpMaximo());
                System.out.println("Elige a qué enemigo atacar:");

                for (int i = 0; i < enemigos.size(); i++) {
                    Enemigo enemigo = enemigos.get(i);
                    if (enemigo.getStats().getHpActual() > 0) {
                        System.out.println((i+1) + ". " + enemigo.nombre + " (HP: " + enemigo.getStats().getHpActual() + ")");
                    }
                }
                int objetivo = -1;
                try{
                    objetivo = Integer.parseInt(input.nextLine()) -1; 
                }catch (Exception e){
                    System.out.println("Entrada inválida. Intenta de nuevo.");
                }

               // Restamos 1 para que el índice coincida con la lista
                if (objetivo >= 0 && objetivo < enemigos.size() && enemigos.get(objetivo).getStats().getHpActual() > 0){
                    Cloud.atacar(enemigos.get(objetivo));
                    atacarObjetivo = true;
                }else{
                    System.out.println("Objetivo no válido, elige otro.");
                }
            }
             // 2. TURNO DE LOS ENEMIGOS (Coordinado)
            if (enemigosVivos(enemigos) && Cloud.getStats().getHpActual() > 0){
                System.out.println("\n--- TURNO ENEMIGO ---");
                turnoEnemigos(enemigos, Cloud);
            }
        }

       
        if (Cloud.getStats().getHpActual() <= 0) {
            System.out.println("Cloud ha caído en la jungla de Gongaga...");
            System.out.println("Vuelves al inicio de todo");
            Cloud.setZonaActual(this.zonaRetorno);
            
        } else if (Cloud.getStats().getHpActual() > 0){ // Solo si Cloud no murió
            System.out.println("\n--- RECOMPENSAS DE BATALLA ---");
            boolean enemigoDerrotado = false;

            for (Enemigo enemigo : enemigos) {
                if (enemigo.getStats().getHpActual() <= 0) {
                    // 1. Imprimimos el encabezado que querías
                    System.out.println("Recompensa de " + enemigo.nombre + ":");
                    enemigo.giveXpRecompensa(Cloud);
                
                    enemigoDerrotado = true;
                    System.out.println(); // Espacio entre enemigos
                }
            }

            if (!enemigoDerrotado) {
                System.out.println("No lograste derrotar a ningún enemigo.");
            }
        }
    }

    public void turnoEnemigos(List<Enemigo> Grupo, Jugador Cloud){
        Random random = new Random();
        int vivos = 0;
        
        for (Enemigo enemigo : Grupo){
            if (enemigo.getStats().getHpActual() > 0){
                vivos++;
            }
        }

        if (vivos <= 0){
            return;
        }

        boolean ataqueConjunto = false;
        int chance = random.nextInt(100);

        // Aplicamos las reglas de probabilidad que mencionaste
        if (vivos == 2 && chance < 50){
            ataqueConjunto = true;
        }else if (vivos == 3 && chance < 33){
            ataqueConjunto = true;
        }

        if (ataqueConjunto){
            System.out.println("¡Los enemigos se coordinan para un ataque !");
            for (Enemigo enemigo : Grupo){
                if (enemigo.getStats().getHpActual() > 0){
                    enemigo.atacar(Cloud);
                } 
            }
        }else{
            // Solo ataca uno al azar si no hubo coordinación
            Enemigo enemigoAtacante = enemigoQueAtaca(Grupo);
            enemigoAtacante.atacar(Cloud);
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

    private Enemigo enemigoQueAtaca(List<Enemigo> Grupo) {
        List<Enemigo> vivos = new ArrayList<>();
        Random random = new Random();
        // Filtramos a los que siguen de pie
        for (Enemigo enemigo : Grupo) {
            if (enemigo.getStats().getHpActual() > 0) {
                vivos.add(enemigo);
            }
        }

        // Si no hay nadie (por seguridad), retornamos null
        if (vivos.isEmpty()) return null;

        // Elegimos de los que REALMENTE pueden atacar
        return vivos.get(random.nextInt(vivos.size()));
    }

    private void buscarMateria(Jugador Cloud) {
        Random rand = new Random();
        Elemento[] elementos = {Elemento.FUEGO, Elemento.HIELO, Elemento.RAYO, Elemento.CURA};
        
        // Elegimos uno al azar
        Elemento elemElegido = elementos[rand.nextInt(elementos.length)];
        Materia nuevaMateria = new Materia(elemElegido);
        
        // Suponiendo que tu Jugador tiene una lista llamada inventarioMaterias
        Cloud.getMochila().add(nuevaMateria);
        
        System.out.println("¡Explorando la maleza has encontrado una Materia de " + elemElegido + "!");
        System.out.println("Se ha guardado en tu mochila.");
    }
}
