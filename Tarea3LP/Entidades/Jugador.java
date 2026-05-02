package Entidades;


import java.util.List;
import java.util.ArrayList;
import Componentes.Estadisticas;
import Componentes.Materia;
import Componentes.Elemento;
import Mapa.Sector7;
import Mapa.Zona;

public class Jugador {
    public String nombre = "Cloud";
    private int nivel;
    private int xpActual;
    private int chatarra;
    private int limiteActual;
    private Estadisticas stats;
    private List<Materia> mochila;
    private Arma busterSword;

    private Zona zonaActual;

    //Constructor
    public Jugador(Zona zonaInicial){
        this.nivel = 1;
        this.xpActual = 0;
        this.chatarra = 0;
        this.limiteActual = 0;
        this.stats = new Estadisticas(200, 50, 15, 15);
        this.mochila = new ArrayList<>();
        this.busterSword = new Arma();
        this.zonaActual = zonaInicial;


    }

    public class Arma {
        public String nombre = "Buster Sword";
        private List<Materia> materiasEquipadas;

        public int calcularDanoMagico(Elemento elemento){
            return stats.getMagia();
        }
    
        public int calcularDanoFisico(){
            return (int) (stats.getFuerza() * 1.25);
        }

        public int calcularDanoLimite(){
            return 0;
        }
    }
    
    
    public void recibirXP(int xp){
        this.xpActual += xp;
        int xpNecesaria = 10 * this.nivel; // XPnecesaria = 10 * Nivelactual

        if (this.xpActual >= xpNecesaria) {
            this.nivel++;
            this.xpActual -= xpNecesaria; // Restamos la XP usada para el nivel

            // Aumentos automáticos por nivel:
            this.stats.setHpMaximo(this.stats.getHpMaximo() + 10);
            this.stats.setMpMaximo(this.stats.getMpMaximo() + 5);
            this.stats.setFuerza(this.stats.getFuerza() + 4);
            this.stats.setMagia(this.stats.getMagia() + 6);
        
            System.out.println("SUBIDA DE NIEVEL! Cloud ahora es nivel " + this.nivel + ".");
        }else{
            System.out.println("Has ganado " + xp + " XP. XP actual: " + this.xpActual + "/" + xpNecesaria);
        }
    
    }

    public void atacar(Enemigo enemigo){
        int danoHecho = this.busterSword.calcularDanoFisico();
        int hpEnemigo = enemigo.getStats().getHpActual() - danoHecho;
        enemigo.getStats().setHpActual(hpEnemigo);

        System.out.println("¡Cloud ataca con la " + busterSword.nombre + "!");
        System.out.println("Causa " + danoHecho + " de daño a " + enemigo.nombre + ".");
    }


    //Getters
    public int getNivel(){
        return nivel;
    }

    public int getXpActual(){
        return xpActual;
    }

    public int getChatarra(){
        return chatarra;
    }
    
    public int getLimiteActual(){
        return limiteActual;
    }

    public Estadisticas getStats(){
        return stats;
    }

    public List<Materia> getMochila(){
        return mochila;
    }

    public Arma getBusterSword(){
        return busterSword;
    }

    public Zona getZonaActual(){
        return zonaActual;
    }

    //Setters
    public void setNivel(int nivel){
        this.nivel = nivel;
    }

    public void setXpActual(int xpActual){
        this.xpActual = xpActual;
    }

    public void setChatarra(int chatarra){
        this.chatarra = chatarra;
    }

    public void setLimiteActual(int limiteActual){
        this.limiteActual = limiteActual;
    }

    public void setStats(Estadisticas stats){
        this.stats = stats;
    }

    public void setMochila(List<Materia> mochila){
        this.mochila = mochila;
    }

    public void setBusterSword(Arma busterSword){
        this.busterSword = busterSword;
    }

    public void setZonaActual(Zona zonaActual){
        this.zonaActual = zonaActual;
    }
}   




