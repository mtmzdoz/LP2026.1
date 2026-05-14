package Entidades;


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
