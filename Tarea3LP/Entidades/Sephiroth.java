package Entidades;

import java.util.Random;

import Componentes.Estadisticas;

public class Sephiroth extends Enemigo {

    private int contadorSuperNova;

    public Sephiroth(){
        super("Sephiroth", 0, 0, new Estadisticas(500, 0, 40, 0)); 
        this.contadorSuperNova = 0;
    }

    public void lanzarSuperNova(){
        
    }
    
}
