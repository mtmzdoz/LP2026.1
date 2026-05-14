package Mapa;

import java.util.List;
import Entidades.Enemigo;
import Entidades.Jugador;

public abstract class Zona {
    public String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;
    //yo
    protected Zona zonaRetorno;

    //Constructor
    public Zona(String nombre, int nivelRequerido, List<Enemigo> enemigosDisponibles, Zona retorno) {
        this.nombre = nombre;
        this.nivelRequerido = nivelRequerido;
        this.enemigosDisponibles = enemigosDisponibles;
        this.zonaRetorno = retorno;
    }

    public void accionZona(Jugador Cloud){
        
    }

    public boolean validarAcceso(Jugador Cloud){
        return false;
    }

    //Getters
    public String getNombre() { return nombre; }
    public int getNivelRequerido() {
        return nivelRequerido;
    }
    public Zona getZonaRetorno() {
        return zonaRetorno;
    }

    //Setters
   

    
}
