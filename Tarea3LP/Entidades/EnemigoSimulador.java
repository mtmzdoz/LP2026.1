package Entidades;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import Componentes.Estadisticas;

public class EnemigoSimulador extends Enemigo{


    public EnemigoSimulador(){
        super("Soldado Común", new Random().nextInt(6) + 15, 0, new Estadisticas(50, 0, 15, 5)); //Elige un numero del 0 al 5 y le suma 15 para el XP
    }

    public boolean checkDanoSeguro(Jugador Cloud, int Dano){
        int hpCloud = Cloud.getStats().getHpActual();
        return (hpCloud - Dano) >= 1;
    }

    @Override
    public void atacar(Jugador Cloud){
        Random probabilidad = new Random();
        System.out.println("Debug: atacar en EnemigoSimulador.java");
        if (probabilidad.nextInt(100) < 85){

            int dano = this.getStats().getFuerza();
            int danoFinal;
            if (checkDanoSeguro(Cloud, dano)){ 
                danoFinal = (int) (dano * 1.25);
            }else{
                
                danoFinal = Math.max(0, Cloud.getStats().getHpActual() - 1); // Solo hace el daño necesario para dejar a Cloud con 1 HP
            }
            Cloud.getStats().recibirDMG(danoFinal);
            System.out.println("-> " + this.nombre + " ataca haciendo " + danoFinal + " de daño. HP restante: " + Cloud.getStats().getHpActual());
            
        }else{
            
            System.out.println("-> " + this.nombre + " intentó atacarte pero falló.");
        }
    }

    @Override
    public void giveXpRecompensa(Jugador Cloud) {
        int xp = new Random().nextInt(6) + 15;
        Cloud.recibirXP(xp);
    }

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
