package Entidades;


import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import Componentes.Estadisticas;
import Componentes.Materia;
import Componentes.Elemento;
import Mapa.Zona;
import Componentes.Mejora;
import Componentes.TipoStat;

public class Jugador{
    public String nombre = "Cloud";
    private int nivel;
    private int xpActual;
    private int chatarra;
    private int limiteActual;
    private Estadisticas stats;
    private List<Materia> mochila;
    private Arma busterSword;
    private Zona zonaActual;

    /*
    * Constructor. Inicializa al jugador con sus valores base, estadísticas iniciales, 
    * una Buster Sword y lo sitúa en la zona de inicio.
    * @param zonaInicial: Zona
    * @return Ninguno
    */
    public Jugador(Zona zonaInicial){
        this.nivel = 1;
        this.xpActual = 0;
        this.chatarra = 0;
        this.limiteActual = 0;
        this.stats = new Estadisticas(200, 50, 15, 15);
        this.mochila = new ArrayList<>();
        this.busterSword = new Arma();
        this.zonaActual = zonaInicial;
    }

    
    public class Arma{
        public String nombre = "Buster Sword";
        private List<Materia> materiasEquipadas;
        public final int maxRanurasMateria = 5;

        /*
        * Inicializa el arma con una lista vacía de materias equipadas.
        * @param Ninguno
        * @return Ninguno
        */
        public Arma(){
            this.materiasEquipadas = new ArrayList<>();
        }

        /*
        * Calcula el daño mágico basado en la estadística de magia, la cantidad de 
        * materias del mismo elemento equipadas y las debilidades del enemigo.
        * @param elemento: Elemento, enemigo: Enemigo
        * @return int
        */
        public int calcularDanoMagico(Elemento elemento, Enemigo enemigo){
            int n = 0;
            for (Materia materia : materiasEquipadas){
                if (materia.getElemento() == elemento){
                    n++;
                }
            }
            int dano = (int) Math.floor(stats.getMagia() * (1.0 + (0.5 * n)));
            double multiplicador = enemigo.evaluarDebilidad(elemento);
            return (int) (dano * multiplicador);
        }
        
        /*
        * Calcula el daño físico base del arma aplicando un multiplicador a la fuerza.
        * @param Ninguno
        * @return int
        */
        public int calcularDanoFisico(){
            return (int) (stats.getFuerza() * 1.25);
        }

        /*
        * Retorna el daño del ataque límite (actualmente no implementado).
        * @param Ninguno
        * @return int
        */
        public int calcularDanoLimite(){
            return 0;
        }

        /*
        * Comprueba si el arma tiene espacios disponibles para más materias.
        * @param Ninguno
        * @return boolean
        */
        public boolean espacioRanuras(){
            return materiasEquipadas.size() < maxRanurasMateria;
        }

        /*
        * Obtiene la lista de materias actualmente equipadas en el arma.
        * @param Ninguno
        * @return List<Materia>
        */
        public List<Materia> getMateriasEquipadas(){
            return materiasEquipadas;
        }
        
        /*
        * Calcula el costo de MP para lanzar un hechizo según la cantidad de materias equipadas.
        * @param elemento: Elemento
        * @return int
        */
        public int costoMP(Elemento elemento){
            
            int n = 0;
            for (Materia materia : materiasEquipadas){
                if (materia.getElemento() == elemento){
                    n++;
                }
            }
            return 10 + (5 * n);
        }
    }
    
    /*
    * Gestiona la obtención o gasto de chatarra..
    * @param cantidad: int
    * @return void
    */
    public void recibirChatarra(int cantidad){
        this.chatarra += cantidad;
        if (cantidad > 0){
            System.out.println("+" + cantidad + " piezas de chatarra.");
        }else if (cantidad < 0){
            System.out.println("Gastaste " + Math.abs(cantidad) + " piezas de chatarra.");
        }
    }


    /*
    * Añade experiencia al jugador y verifica si se cumplen los requisitos para subir de nivel.
    * @param xp: int
    * @return void
    */
    public void recibirXP(int xp){
        this.xpActual += xp;
        System.out.println("+" + xp + " XP.");

        int xpNecesaria = 10 * this.nivel; // XPnecesaria = 10 * Nivelactual

        while (this.xpActual >= xpNecesaria){
            this.nivel++;
            this.xpActual -= xpNecesaria; // Restamos la XP usada para el nivel

            // Aumentos automáticos por nivel:
            this.stats.setHpMaximo(this.stats.getHpMaximo() + 10);
            this.stats.setMpMaximo(this.stats.getMpMaximo() + 5);
            this.stats.setFuerza(this.stats.getFuerza() + 4);
            this.stats.setMagia(this.stats.getMagia() + 6);
            
            System.out.println("SUBIDA DE NIVEL! Cloud ahora es nivel " + this.nivel + ".");
            System.out.println("+10 HP Máximo");
            System.out.println("+5 MP Máximo");
            System.out.println("+4 Fuerza");
            System.out.println("+6 Magia");
        
        }
    }

    /*
    * Realiza un ataque físico contra un enemigo y aumenta la barra de límite.
    * @param enemigo: Enemigo
    * @return void
    */
    public void atacar(Enemigo enemigo){
        int danoHecho = this.busterSword.calcularDanoFisico();
        enemigo.getStats().recibirDMG(danoHecho);
        int carga = danoHecho / 5;
        this.limiteActual = Math.min(100, this.limiteActual + carga);
        System.out.println("\n¡Cloud ataca con la " + busterSword.nombre + "!");
        System.out.println("-> Causa " + danoHecho + " de daño a " + enemigo.nombre + ".");
    }

    /*
    * Ejecuta un ataque mágico consumiendo MP, ya sea para curar al jugador o atacar a un enemigo.
    * @param elemento: Elemento, enemigo: Enemigo
    * @return void
    */
    public void atacarMagia(Elemento elemento, Enemigo enemigo){
        int costoMP = this.busterSword.costoMP(elemento);
        if (this.getStats().getMpActual() < costoMP){
            System.out.println("¡MP Insuficiente! Necesitas " + costoMP + " MP.");
            return;
        }

        this.getStats().setMpActual(this.getStats().getMpActual() - costoMP);
        if (elemento == Elemento.CURA){
                int n = 0;
                for (Materia materia : busterSword.getMateriasEquipadas()){
                    if (materia.getElemento() == Elemento.CURA){
                        n++;
                    }
                }
                
                int magiaCura = (int) Math.floor(this.getStats().getMagia() * (1.0 + (0.5 * n)));
                int hpActual = this.getStats().getHpActual();
                int hpMax = this.getStats().getHpMaximo();
                int nuevaHp = Math.min(hpMax, hpActual + magiaCura);
                this.getStats().setHpActual(nuevaHp);
                System.out.println("\n¡Cloud usa CURA y recupera " + (nuevaHp - hpActual) + " HP!");
    
        }else if (enemigo != null){
            int dano = this.busterSword.calcularDanoMagico(elemento, enemigo);
            enemigo.getStats().recibirDMG(dano);
            System.out.println("\n¡Cloud lanza " + elemento + " y causa " + dano + " de daño a " + enemigo.nombre + "!");
        }
        

    }

    /*
    * Realiza el ataque límite del jugador si la barra está llena.
    * @param enemigo: Enemigo
    * @return void
    */
    public void ataqueLimite(Enemigo enemigo){
        if (this.limiteActual < 100){
            System.out.println("¡La barra de límite aún no está lista!");
            return;
        }
        int dano = this.stats.getFuerza() * 5;
    
        System.out.println("\n--- ¡Ataque Límite! ---");
        enemigo.getStats().recibirDMG(dano);
    
        System.out.println("Cloud desata un gran ataque. Daño total: " + dano);
        this.limiteActual = 0;
    }

    /*
    * Procesa el daño recibido por el jugador y carga la barra de límite.
    * @param dano: int
    * @return void
    */
    public void recibirAtaque(int dano){
        this.stats.recibirDMG(dano);
        int carga = dano/2;
        this.limiteActual = Math.min(100, this.limiteActual + carga);

        System.out.println("Cloud recibe " + dano + " de daño.");
        System.out.println("Carga de Límite: +" + carga + " (Total: " + this.limiteActual + "/100)");
    }

    /*
    * Muestra el menú de la mochila, permitiendo ver la chatarra y equipar materias.
    * @param input: Scanner
    * @return void
    */
    public void verMochila(Scanner input){
        boolean salir = false;

        while (!salir){
            System.out.println("\n--- Mochila ---");
            System.out.println("Chatarra: " + this.chatarra);
            if (mochila.isEmpty()){
                System.out.println("Tu mochila está vacía.");
            }else{
                System.out.println("Materias obtenidas para poder usar:");
                for (int i = 0; i < mochila.size(); i++){
                    System.out.println((i + 1) + ". Materia de " + this.mochila.get(i).getElemento());
                }
            }
            System.out.println("0. Salir de la mochila");
            System.out.print("Selecciona una materia para equipar o 0 para salir: ");

            int opcion = -1;
            try{
                opcion = Integer.parseInt(input.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Entrada inválida. Usa solo números.");
                continue;
            }

            if (opcion == 0){
                salir = true;

            }else if (opcion > 0 && opcion <= mochila.size()){
                System.out.println("\nMateria de " + mochila.get(opcion - 1).getElemento() + " seleccionada.");
                    System.out.println("1. Equipar en Buster Sword");
                    System.out.println("2. Cancelar");
                    
                    try{
                        int subOpcion = Integer.parseInt(input.nextLine());
                        if (subOpcion == 1){
                            this.equiparMateriaArma(opcion - 1); 
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Entrada no válida. Intenta de nuevo.");
                    }
            }else{
                System.out.println("Opción no válida.");
            }
        }
    }

    /*
    * Aplica de forma permanente una mejora a las estadísticas máximas del jugador.
    * @param mejora: Mejora
    * @return void
    */
    public void aplicarMejora(Mejora mejora){
        TipoStat tipo = mejora.getTipoStat();
        int bono = mejora.getValorBono();

        switch (tipo){
            case HP_MAX:
                int nuevoMaxHP = this.getStats().getHpMaximo() + bono;
                this.getStats().setHpMaximo(nuevoMaxHP);
                this.getStats().setHpActual(this.getStats().getHpActual() + bono);
                System.out.println("¡" + mejora.getNombre() + " aplicada! HP máximo ahora es: " + nuevoMaxHP);
                break;

            case MP_MAX:
                int nuevoMaxMP = this.getStats().getMpMaximo() + bono;
                this.getStats().setMpMaximo(nuevoMaxMP);
                this.getStats().setMpActual(this.getStats().getMpActual() + bono);
                System.out.println("¡" + mejora.getNombre() + " aplicada! MP máximo ahora es: " + nuevoMaxMP);
                break;

            case FUERZA:
                int nuevaFuerza = this.getStats().getFuerza() + bono;
                this.getStats().setFuerza(nuevaFuerza);
                System.out.println("¡" + mejora.getNombre() + " aplicada! Tu fuerza ahora es: " + nuevaFuerza);
                break;
            
            default:
                System.out.println("Tipo de mejora no reconocido.");
                break;
        }

    }

    /*
    * Transfiere una materia de la mochila a una ranura libre del arma.
    * @param indiceMochila: int
    * @return void
    */
    public void equiparMateriaArma(int indiceMochila){
        if (this.busterSword.getMateriasEquipadas().size() < 5){
        
            Materia materia = this.mochila.remove(indiceMochila);
            this.busterSword.getMateriasEquipadas().add(materia);
        
            System.out.println("\n¡Materia de " + materia.getElemento() + " equipada en  " + this.busterSword.nombre + "!");
        }else{
            System.out.println("\n El arma no tiene más ranuras disponibles"+ this.busterSword.getMateriasEquipadas().size() + "/" + this.busterSword.maxRanurasMateria);
        }
    }

    /*
    * Resetea el estado del jugador tras ser derrotado, limpiando inventario y reubicándolo.
    * @param sector7: Zona
    * @return void
    */
    public void derrota(Zona sector7){
        this.setZonaActual(sector7);
        this.getStats().setHpActual(1);
        this.getStats().setMpActual(0);
        this.setChatarra(0);
        this.mochila.clear(); 

        System.out.println("\nCloud ha sido derrotado y rescatado.");
        System.out.println("Has despertado en el Sector 7.");
        System.out.println("Has perdido toda tu chatarra y los objetos de tu mochila.\n");
    }  

    //Getters
    public int getNivel(){
        return nivel;
    }

    public int getXpActual(){
        return xpActual;
    }

    public int getChatarra(){
        return chatarra;
    }
    
    public int getLimiteActual(){
        return limiteActual;
    }

    public Estadisticas getStats(){
        return stats;
    }

    public List<Materia> getMochila(){
        return mochila;
    }

    public Arma getBusterSword(){
        return busterSword;
    }

    public Zona getZonaActual(){
        return zonaActual;
    }

    //Setters
    public void setChatarra(int chatarra){
        this.chatarra = chatarra;
    }

    public void setStats(Estadisticas stats){
        this.stats = stats;
    }

    public void setBusterSword(Arma busterSword){
        this.busterSword = busterSword;
    }

    public void setZonaActual(Zona zonaActual){
        this.zonaActual = zonaActual;
    }
}   




