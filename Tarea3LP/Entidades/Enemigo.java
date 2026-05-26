package Entidades;


import Componentes.Elemento;
import Componentes.Estadisticas;

public abstract class Enemigo{
    public String nombre;
    protected int xpRecompensa;
    protected int chatarraRecompensa;
    protected Estadisticas stats;
    
    /*
    * Constructor. Inicializa los atributos básicos de cualquier 
    * oponente, incluyendo recompensas y estadísticas de combate.
    * @param nombre: String, xpRecompensa: int, chatarraRecompensa: int, stats: Estadisticas
    * @return Ninguno
    */
    public Enemigo(String nombre, int xpRecompensa, int chatarraRecompensa, Estadisticas stats){
        this.nombre = nombre;
        this.xpRecompensa = xpRecompensa;
        this.chatarraRecompensa = chatarraRecompensa;
        this.stats = stats;
    }

    /*
    * Define la acción de ataque del enemigo hacia el jugador. 
    * Es un método pensado para ser sobreescrito por las subclases.
    * @param Cloud: Jugador
    * @return void
    */
    public void atacar(Jugador Cloud){
    }

    /*
    * Gestiona la entrega de puntos de experiencia al jugador tras derrotar al enemigo.
    * @param Cloud: Jugador
    * @return void
    */
    public void giveXpRecompensa(Jugador Cloud){   
    }   

    /*
    * Gestiona la entrega de chatarra al jugador tras derrotar al enemigo.
    * @param cloud: Jugador
    * @return void
    */
    public void giveChatarraRecompensa(Jugador cloud){
    }

    /*
    * Evalúa si el enemigo es vulnerable a un elemento mágico específico. 
    * Por defecto retorna un multiplicador de daño neutro (1.0).
    * @param elemento: Elemento
    * @return double
    */
    public double evaluarDebilidad(Elemento elemento){
        return 1.0; 
    }

    //Getters
    public Estadisticas getStats(){
        return stats;
    }

    //Setters
    public void setStats(Estadisticas stats){
        this.stats = stats;
    }


}
