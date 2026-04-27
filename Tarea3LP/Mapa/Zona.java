package Mapa;

import java.util.List;
import Entidades.Enemigo;
import Entidades.Jugador;

public abstract class Zona {
    public String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;

    //Constructor
    public Zona(String nombre, int nivelRequerido, List<Enemigo> enemigosDisponibles) {
        this.nombre = nombre;
        this.nivelRequerido = nivelRequerido;
        this.enemigosDisponibles = enemigosDisponibles;
    }

    public void accionZona(Jugador Cloud){
        
    }

    public boolean validarAcceso(Jugador Cloud){
        return false;
    }
}
