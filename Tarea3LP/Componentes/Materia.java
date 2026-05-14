package Componentes;

import Componentes.Elemento;

public class Materia {
    private String nombre;
    private Elemento elemento;


    public Materia(Elemento tipo) {
        this.elemento = tipo;
        
    }

    public String getNombre() {
        return nombre;
    }

    public Elemento getElemento() {
        return elemento;
    }

}
