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


        /*
        * Constructor. Inicializa el nombre, genera recompensas aleatorias 
        * y prepara las listas de afinidades elementales.
        * @param nombre: String, xp: int, fuerza: int
        * @return Ninguno
        */
        public EnemigoSalvaje(String nombre, int xp, int fuerza){
            super(nombre, new Random().nextInt(21) + 80, new Random().nextInt(26) + 50, new Estadisticas(xp, 0, fuerza, 0)); //Elige un numero del 0 al 5 y le suma 15 para el XP
            this.debilidades = new ArrayList<>();
            this.resistencias = new ArrayList<>();
            this.inmunidades = new ArrayList<>();
        }

        /*
        * Compara un elemento mágico contra las afinidades del enemigo para devolver 
        * el multiplicador de daño correspondiente (2.0, 0.5, 0.0 o 1.0).
        * @param elementoMagia: Elemento
        * @return double
        */
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

        /*
        * Realiza un ataque físico contra el jugador según probabilidad, y 
        * aplicando un multiplicador al daño base si el ataque sucede.
        * @param Cloud: Jugador
        * @return void
        */
        @Override
        public void atacar(Jugador Cloud){
            Random probabilidad = new Random();
            if (probabilidad.nextInt(100) < 85){

                int dano = this.getStats().getFuerza();
                int danoFinal = (int) (dano * 1.25);
                Cloud.recibirAtaque(danoFinal);
                System.out.println("-> " + this.nombre + " ataca haciendo " + danoFinal + " de daño. HP restante: " + Cloud.getStats().getHpActual());
                
            }else{
                
                System.out.println("-> " + this.nombre + " intentó atacarte pero falló.");
            }
        }

        /*
        * Genera una cantidad aleatoria de puntos de xp de recompensa entre 80 y 100, y los asigna al jugador.
        * @param Cloud: Jugador
        * @return void
        */
        @Override
        public void giveXpRecompensa(Jugador Cloud){
            int xp = new Random().nextInt(21) + 80;
            Cloud.recibirXP(xp);
        }

        /*
        * Calcula una cantidad aleatoria de chatarra entre 50 y 75, y la asigna al inventario del jugador.
        * @param Cloud: Jugador
        * @return void
        */
        @Override
        public void giveChatarraRecompensa(Jugador Cloud){
            int chatarra = new Random().nextInt(26) + 50;
            Cloud.recibirChatarra(chatarra);
        }

        /*
        * Añade un elemento a la lista de debilidades para aumentar el daño recibido de dicho tipo.
        * @param elemento: Elemento
        * @return void
        */
        public void adddebilidad(Elemento elemento){
            debilidades.add(elemento);
        }

        /*
        * Añade un elemento a la lista de resistencias para reducir el daño recibido de dicho tipo.
        * @param elemento: Elemento
        * @return void
        */
        public void addresistencia(Elemento elemento){
            resistencias.add(elemento);
        }

        /*
        * Añade un elemento a la lista de inmunidades para anular completamente el daño de dicho tipo.
        * @param elemento: Elemento
        * @return void
        */
        public void addinmunidad(Elemento elemento){
            inmunidades.add(elemento);
        }
    }

