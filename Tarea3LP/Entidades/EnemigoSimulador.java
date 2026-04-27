package Entidades;

import java.util.Random;

import Componentes.Estadisticas;

public class EnemigoSimulador extends Enemigo{


    public EnemigoSimulador(){
        super("Soldado Común", new Random().nextInt(6) + 15, 0, new Estadisticas(50, 0, 15, 5)); //Elige un numero del 0 al 5 y le suma 15 para el XP
    }

    public boolean checkDanoSeguro(Jugador Cloud){
        return false;
    }


    
}
