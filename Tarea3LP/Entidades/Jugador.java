package Entidades;

import Componentes.Estadisticas;

public class Jugador {
    public String nombre;
    private int nivel;
    private int xpActual;
    private int chatarra;
    private int limiteActual;
    private Estadisticas stats;
    private List<Material> mochila;
    private Arma busterSword;
}

public void recibirXP(int xp) {
    this.xpActual += xp;
    if (this.xpActual >= this.limiteActual) {
        subirNivel();
    }
}