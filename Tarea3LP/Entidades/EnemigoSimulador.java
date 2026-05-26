package Entidades;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import Componentes.Estadisticas;

public class EnemigoSimulador extends Enemigo{

    /*
    * Constructor. Inicializa un "Soldado Común" con estadísticas base 
    * y una recompensa de XP aleatoria entre 15 y 20.
    * @param Ninguno
    * @return Ninguno
    */
    public EnemigoSimulador(){
        super("Soldado Común", new Random().nextInt(6) + 15, 0, new Estadisticas(50, 0, 15, 5)); //Elige un numero del 0 al 5 y le suma 15 para el XP
    }

    /*
    * Valida si el daño recibido por el jugador le permite mantenerse con vida (mín 1 HP), 
    *para evitar que muera dentro de la simulación.
    * @param Cloud: Jugador, Dano: int
    * @return boolean
    */
    public boolean checkDanoSeguro(Jugador Cloud, int Dano){
        int hpCloud = Cloud.getStats().getHpActual();
        return (hpCloud - Dano) >= 1;
    }

    /*
    * Ejecuta la lógica de ataque del enemigo. Tiene un 85% de acierto y ajusta el daño 
    * final para asegurar que el jugador no baje de 1 HP.
    * @param Cloud: Jugador
    * @return void
    */
    @Override
    public void atacar(Jugador Cloud){
        Random probabilidad = new Random();
        if (probabilidad.nextInt(100) < 85){

            int dano = this.getStats().getFuerza();
            int danoFinal;
            if (checkDanoSeguro(Cloud, dano)){ 
                danoFinal = (int) (dano * 1.25);
            }else{
                
                danoFinal = Math.max(0, Cloud.getStats().getHpActual() - 1); 
            }
            Cloud.recibirAtaque(danoFinal);
            System.out.println("-> " + this.nombre + " ataca haciendo " + danoFinal + " de daño. HP restante: " + Cloud.getStats().getHpActual()+"\n");
    
        }else{
            
            System.out.println("-> " + this.nombre + " intentó atacarte pero falló.");
        }
    }

    /*
    * Calcula y otorga una recompensa de xp entre 15 y 20 al jugador al finalizar el combate.
    * @param Cloud: Jugador
    * @return void
    */
    @Override
    public void giveXpRecompensa(Jugador Cloud){
        int xp = new Random().nextInt(6) + 15;
        Cloud.recibirXP(xp);
    }   

    /*
    * Crea una lista que contiene entre 1 y 2 enemigos simulados 
    * para iniciar un encuentro.
    * @param Ninguno
    * @return List<Enemigo>
    */
    public static List<Enemigo> generarSimulados(){
        List<Enemigo> enemigosSimulados = new ArrayList<>();
        Random cantidad = new Random();

        int cantidadEnemigos = cantidad.nextInt(2) + 1;

        for(int i = 0; i < cantidadEnemigos; i++){
            enemigosSimulados.add(new EnemigoSimulador());
        }
        return enemigosSimulados;
    }
}
