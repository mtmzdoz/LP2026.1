package Mapa;

import Entidades.Jugador;

public class NucleoPlaneta extends Zona{
    private int materiasMinimasRequeridas;

    //Constructor
    public NucleoPlaneta(Zona retorno) {
        super("Núcleo del Planeta", 20, null, retorno);
        this.materiasMinimasRequeridas = materiasMinimasRequeridas;
    }

    public void iniciarCombate(Jugador Cloud){
        
    }
}
