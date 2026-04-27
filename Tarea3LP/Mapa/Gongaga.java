package Mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Componentes.Elemento;
import Componentes.Materia;
import Entidades.Enemigo;
import Entidades.EnemigoSalvaje;

public class Gongaga extends Zona{
    private List<Materia> poolMaterias;

    //Constructor
    public Gongaga() {
        super("Gongaga", 5, null);
        this.poolMaterias = poolMaterias;
    }

    public List<Enemigo> generarGrupoEnemigo(){
        List<Enemigo> grupo = new ArrayList<>();
        Random random = new Random();

        //Ccuántos enemigos aparecen según las probabilidades 
        int Probabilidad = random.nextInt(100); // 0 a 99
        int CantidadEnemigos;
        if (Probabilidad < 60){
            CantidadEnemigos = 1;      // 60%
        }else if (Probabilidad < 90){
            CantidadEnemigos = 2; // 30%
        }else{
            CantidadEnemigos = 3;  // 10%
        }                     

        // 2. Bucle para crear la cantidad de enemigos definida
        for (int i = 0; i < CantidadEnemigos; i++) {
            int TipoEnemigo = random.nextInt(3); // Elige 0, 1 o 2
            EnemigoSalvaje NuevoEnemigo;
        
            switch (TipoEnemigo) {
                case 0:
                // Planta Carnívora: HP 80, Fuerza 15 [cite: 71, 72]
                    NuevoEnemigo = new EnemigoSalvaje("Planta Carnívora", 80, 15);
                    NuevoEnemigo.adddebilidad(Elemento.FUEGO);
                    NuevoEnemigo.adddebilidad(Elemento.HIELO);
                    NuevoEnemigo.addinmunidad(Elemento.RAYO);
                    break;
                case 1:
                    // Sapo de la Jungla: HP 60, Fuerza 12 [cite: 76, 77]
                    NuevoEnemigo = new EnemigoSalvaje("Sapo de la Jungla", 60, 12);
                    NuevoEnemigo.adddebilidad(Elemento.RAYO);
                    NuevoEnemigo.adddebilidad(Elemento.HIELO);
                    NuevoEnemigo.addresistencia(Elemento.FUEGO);
                    break;
                default:
                    // Robot Centinela: HP 100, Fuerza 20 [cite: 81, 82]
                    NuevoEnemigo = new EnemigoSalvaje("Robot Centinela", 100, 20);
                    NuevoEnemigo.adddebilidad(Elemento.RAYO);
                    NuevoEnemigo.addresistencia(Elemento.FISICO);
                    NuevoEnemigo.addresistencia(Elemento.HIELO);
                    break;
            }
            grupo.add(NuevoEnemigo);
        }
        return grupo;
    }
}
