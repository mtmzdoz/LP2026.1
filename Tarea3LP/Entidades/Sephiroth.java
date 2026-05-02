package Entidades;

import java.util.Random;

import Componentes.Estadisticas;
import Entidades.Jugador;
public class Sephiroth extends Enemigo {

    private int contadorSuperNova;

    public Sephiroth(){
        super("Sephiroth", 0, 0, new Estadisticas(500, 0, 40, 0)); 
        this.contadorSuperNova = 0;
    }

    public void lanzarSuperNova(){
        
    }
    
    @Override
    public void atacar(Jugador Cloud) {
        Random Probabilidad = new Random();
    
        if (Probabilidad.nextInt(100) < 90) {
            // 2. Calcular daño: Fuerza * 1.0
            int Dano = this.getStats().getFuerza();
        
            // 3. Aplicar daño a Cloud
            int HpActual = Cloud.getStats().getHpActual();
            Cloud.getStats().setHpActual(HpActual - Dano);
        
            System.out.println("-> " + this.nombre + " lanza un golpe y quita " + Dano + " de HP.");
        } else {
            System.out.println("-> " + this.nombre + " intentó atacarte pero falló.");
        }
    }
}
