package Entidades;

import java.util.Random;

import Componentes.Estadisticas;

public abstract class Enemigo {
    public String nombre;
    protected int xpRecompensa;
    protected int chatarraRecompensa;
    protected Estadisticas stats;
    

    public Enemigo(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats){
        this.nombre = nombre;
        this.xpRecompensa = xpRecompensa;
        this.chatarraRecompensa = chatarraRecompensa;
        this.stats = stats;
    }

    public void atacar(Jugador Cloud){
        Random probabilidad = new Random();
    
        if (probabilidad.nextInt(100) < 85) {
            // 2. Calcular daño: Fuerza * 1.0
            int dano = this.getStats().getFuerza();
        
            // 3. Aplicar daño a Cloud
            int hpActual = Cloud.getStats().getHpActual();
            Cloud.getStats().setHpActual(hpActual - dano);
        
            System.out.println("-> " + this.nombre + " lanza un golpe y quita " + dano + " de HP.");
        } else {
            System.out.println("-> " + this.nombre + " intentó atacarte pero falló.");
        }
    }

    public void giveXpRecompensa(Jugador Cloud){
        
    }   


    public Estadisticas getStats(){
        return stats;
    }

    public void setStats(Estadisticas stats){
        this.stats = stats;
    }


}
