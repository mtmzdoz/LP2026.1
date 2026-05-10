package Mapa;

import java.util.List;
import Entidades.Enemigo;
import Entidades.Jugador;

public abstract class Zona {
    public String nombre;
    protected int nivelRequerido;
    protected List<Enemigo> enemigosDisponibles;
    //yo
    protected Zona zonaAnterior;
    protected Zona zonaSiguiente;

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

    //Getters
    public Zona getZonaSiguiente(){
        return zonaSiguiente; 
    }
    public Zona getZonaAAnterior(){
        return zonaAnterior; 
    }

    public int getNivelRequerido() {
        return nivelRequerido;
    }

    //Setters
    public void setZonaSiguiente(Zona ZonaSig){
        this.zonaSiguiente = ZonaSig;
    }
    public void setZonaAnterior(Zona ZonaAnt){
        this.zonaAnterior = ZonaAnt;
    }

    
}
