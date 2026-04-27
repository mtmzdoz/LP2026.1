package Entidades;


import java.util.List;
import java.util.ArrayList;
import Componentes.Estadisticas;
import Componentes.Materia;
import Componentes.Elemento;

public class Jugador {
    public String nombre = "Cloud";
    private int nivel;
    private int xpActual;
    private int chatarra;
    private int limiteActual;
    private Estadisticas stats;
    private List<Materia> mochila;
    private Arma busterSword;

    //Constructor
    public Jugador(){
        this.nivel = 1;
        this.xpActual = 0;
        this.chatarra = 0;
        this.limiteActual = 0;
        this.stats = new Estadisticas(200, 50, 15, 15);
        this.mochila = new ArrayList<>();
        this.busterSword = new Arma();
    }

    public class Arma {
        public String nombre = "Buster Sword";
        private List<Materia> materiasEquipadas;

        public int calcularDanoMagico(Elemento elemento){
            return 0;
        }
    
        public int calcularDanoFisico(){
            return 0;
        }

        public int calcularDanoLimite(){
            return 0;
        }
    }
    
    
    public void recibirXP(int xp){
    
    
    }



    //Getters
    public int getnivel(){
        return nivel;
    }

    public int getxpActual(){
        return xpActual;
    }

    public int getchatarra(){
        return chatarra;
    }
    
    public int getlimiteActual(){
        return limiteActual;
    }

    public Estadisticas getstats(){
        return stats;
    }

    public List<Materia> getmochila(){
        return mochila;
    }

    public Arma getbusterSword(){
        return busterSword;
    }

    //Setters
    public void setnivel(int nivel){
        this.nivel = nivel;
    }

    public void setxpActual(int xpActual){
        this.xpActual = xpActual;
    }

    public void setchatarra(int chatarra){
        this.chatarra = chatarra;
    }

    public void setlimiteActual(int limiteActual){
        this.limiteActual = limiteActual;
    }

    public void setstats(Estadisticas stats){
        this.stats = stats;
    }

    public void setmochila(List<Materia> mochila){
        this.mochila = mochila;
    }

    public void setbusterSword(Arma busterSword){
        this.busterSword = busterSword;
    }

}




