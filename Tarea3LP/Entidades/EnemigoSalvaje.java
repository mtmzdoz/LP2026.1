    package Entidades;

    import java.util.List;
    import java.util.ArrayList;
    import java.util.Random;

    import Componentes.Elemento;
    import Componentes.Estadisticas;
    import Componentes.Vulnerable;


    public class EnemigoSalvaje extends Enemigo implements Vulnerable{
        private List<Elemento> debilidades;
        private List<Elemento> resistencias;
        private List<Elemento> inmunidades;

        public EnemigoSalvaje(String nombre, int xp, int fuerza) {
            super(nombre, new Random().nextInt(21) + 80, new Random().nextInt(26) + 50, new Estadisticas(xp, 0, fuerza, 0)); //Elige un numero del 0 al 5 y le suma 15 para el XP
            this.debilidades = new ArrayList<>();
            this.resistencias = new ArrayList<>();
            this.inmunidades = new ArrayList<>();
        }

        @Override
        public double evaluarDebilidad(Elemento elementoMagia){
            if(debilidades.contains(elementoMagia)){
                return 2.0;
            }else if(resistencias.contains(elementoMagia)){
                return 0.5;
            }else if(inmunidades.contains(elementoMagia)){
                return 0.0;
            }else {
                return 1.0;
            }
        }

        
        public void giveChatarraRecompensa(Jugador Cloud){

        }

        @Override
        public void atacar(Jugador Cloud){
            Random probabilidad = new Random();
            System.out.println("Debug: atacar en ENnemigoSlvaje.java");
            if (probabilidad.nextInt(100) < 85){

                int dano = this.getStats().getFuerza();
                int danoFinal = (int) (dano * 1.25);
                Cloud.getStats().recibirDMG(danoFinal);
                System.out.println("-> " + this.nombre + " ataca haciendo " + danoFinal + " de daño. HP restante: " + Cloud.getStats().getHpActual());
                
            }else{
                
                System.out.println("-> " + this.nombre + " intentó atacarte pero falló.");
            }
        }

        @Override
        public void giveXpRecompensa(Jugador Cloud) {
            int xp = new Random().nextInt(21) + 80;
            Cloud.recibirXP(xp);
        }

        
        public void adddebilidad(Elemento elemento){
            debilidades.add(elemento);
        }

        public void addresistencia(Elemento elemento){
            resistencias.add(elemento);
        }

        public void addinmunidad(Elemento elemento){
            inmunidades.add(elemento);
        }
    }

