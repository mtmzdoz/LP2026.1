package Mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Entidades.Enemigo;
import Entidades.Jugador;

public abstract class Zona{
    public String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;
    protected Zona zonaRetorno;

    /*
    * Constructor. Inicializa los atributos básicos para cada zona, incluyendo el nombre, el nivel requerido para acceder, la lista de enemigos disponibles
    * y la zona a la que se retorna.
    * @param nombre: String, nivelRequerido: int, enemigosDisponibles: List<Enemigo>, retorno: Zona
    * @return Ninguno
    */
    public Zona(String nombre, int nivelRequerido, List<Enemigo> enemigosDisponibles, Zona retorno){
        this.nombre = nombre;
        this.nivelRequerido = nivelRequerido;
        this.enemigosDisponibles = enemigosDisponibles;
        this.zonaRetorno = retorno;
    }

    /*
    * Define el comportamiento y las interacciones principales del jugador dentro de la zona.
    * @param Cloud: Jugador
    * @return void
    */
    public void accionZona(Jugador Cloud){
        
    }

    /*
    * Comprueba si el jugador posee los requisitos para acceder a la zona.
    * @param Cloud: Jugador
    * @return boolean
    */
    public boolean validarAcceso(Jugador Cloud){
        return false;
    }

    /*
    * Ejecuta la lógica del turno de los enemigos, gestionando la probabilidad de 
    * ataques individuales o ataques conjuntos coordinados.
    * @param Grupo: List<Enemigo>, Cloud: Jugador
    * @return void
    */
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
            Enemigo enemigoAtacante = enemigoQueAtaca(Grupo);
            enemigoAtacante.atacar(Cloud);
        }
    }

    /*
    * Selecciona de manera aleatoria a un enemigo que se encuentre con vida 
    * para realizar un ataque.
    * @param Grupo: List<Enemigo>
    * @return Enemigo
    */
    private Enemigo enemigoQueAtaca(List<Enemigo> Grupo){
        List<Enemigo> vivos = new ArrayList<>();
        Random random = new Random();
        for (Enemigo enemigo : Grupo) {
            if (enemigo.getStats().getHpActual() > 0){
                vivos.add(enemigo);
            }
        }
        if (vivos.isEmpty()){
            return null;
        } 
        return vivos.get(random.nextInt(vivos.size()));
    }

    public boolean enemigosVivos(List<Enemigo> lista){
        for (Enemigo enemigo : lista){
            if (enemigo.getStats().getHpActual() > 0 ){
                return true;
            }
        }
        return false;
    }
    
    /*
    * Verifica si existe al menos un enemigo en la lista que aún tenga vida.
    * @param lista: List<Enemigo>
    * @return boolean
    */
    protected Enemigo seleccionarEnemigo(List<Enemigo> lista, Scanner input){
        List<Enemigo> vivos = new ArrayList<>();
        for (Enemigo enemigo : lista){
            if (enemigo.getStats().getHpActual() > 0) vivos.add(enemigo);
        }

        if (vivos.isEmpty()){
            return null;
        }

        if (vivos.size() == 1){
            System.out.println("-> Objetivo fijado: " + vivos.get(0).nombre);
            return vivos.get(0);
        }

        System.out.println("\n¿A qué enemigo quieres atacar?");
        for (int i = 0; i < vivos.size(); i++){
            System.out.println((i + 1) + ". " + vivos.get(i).nombre + " (HP: " + vivos.get(i).getStats().getHpActual() + ")");
        }
        System.out.println("0. Cancelar y cambiar acción");

        int opcion = -1;
        try{
            opcion = Integer.parseInt(input.nextLine()) - 1;
            
        }catch (NumberFormatException e) { 
            System.out.println("Entrada inválida. Intenta de nuevo.");
            return null;
        }

        if (opcion == -1){
            return null; 
        }
        if (opcion >= 0 && opcion < vivos.size()){
            return vivos.get(opcion);
        }

        System.out.println("Objetivo no válido.");
        return null;
    }

    //Getters
    public String getNombre(){
        return nombre;
    }
    public int getNivelRequerido(){
        return nivelRequerido;
    }
    public Zona getZonaRetorno(){
        return zonaRetorno;
    }
    
}
