package Entidades;

import java.util.Random;

import Componentes.Estadisticas;

public class Sephiroth extends Enemigo{
    private int contadorSuperNova;

    /*
    * Constructor. Inicializa a Sephiroth con estadísticas base.
    * @param Ninguno
    * @return Ninguno
    */
    public Sephiroth(){
        super("Sephiroth", 0, 0, new Estadisticas(500, 0, 40, 0));
        this.contadorSuperNova = 0;
    }

    /*
    * Ejecuta el ataque definitivo "Supernova", el cual "desintegra" al 
    * jugador devolviendolo al inicio de todo.
    * @param Cloud: Jugador
    * @return void
    */
    public void lanzarSuperNova(Jugador Cloud){
        System.out.println("\n" + "!".repeat(10));
        System.out.println("Sephiroth invoca la Supernova...");
        System.out.println("¡Cloud ha sido desintegrado!");
        System.out.println("!".repeat(10) + "\n");

        Cloud.getStats().setHpActual(0);
    }

    /*
    * Ejecuta el ataque normal de Sephiroth, el cual puede lanzar un golpe o fallar.
    * @param Cloud: Jugador
    * @return void
    */
    @Override
    public void atacar(Jugador Cloud){
        this.contadorSuperNova++;
        Random Probabilidad = new Random();
        System.out.println("\n--- Turno de Sephiroth ---");

        if (this.contadorSuperNova == 10){
            lanzarSuperNova(Cloud);
            contadorSuperNova = 0;
        }else if (Probabilidad.nextInt(100) < 90){
            int dano = this.getStats().getFuerza();
            int danoFinal = (int) (dano * 1.25);

            Cloud.recibirAtaque(danoFinal);
            System.out.println("-> " + this.nombre + " lanza un golpe y quita " + danoFinal + " de HP.");
        }else{
            System.out.println("-> " + this.nombre + " intentó atacarte pero falló.");
        }
    }

    //Getters
    public int getContadorSuperNova(){
        return this.contadorSuperNova;
    }
    //Setters
    public void setContadorSuperNova(int valor){
        this.contadorSuperNova = valor;
    }
}
