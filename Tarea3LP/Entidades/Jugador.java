package Entidades;


import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import Componentes.Estadisticas;
import Componentes.Materia;
import Componentes.Elemento;
import Mapa.Sector7;
import Mapa.Zona;
import Entidades.EnemigoSalvaje;

public class Jugador {
    public String nombre = "Cloud";
    private int nivel;
    private int xpActual;
    private int chatarra;
    private int limiteActual;
    private Estadisticas stats;
    private List<Materia> mochila;
    private Arma busterSword;

    private Zona zonaActual;

    //Constructor
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

    public class Arma {
        public String nombre = "Buster Sword";
        private List<Materia> materiasEquipadas;
        public final int maxRanurasMateria = 5;

        public Arma(){
            this.materiasEquipadas = new ArrayList<>();
        }
        public int calcularDanoMagico(Elemento elemento, Enemigo enemigo){
            int n = 0;
            for (Materia m : materiasEquipadas) {
                if (m.getElemento() == elemento) {
                    n++;
                }
            }
            int dano = (int) Math.floor(stats.getMagia() * (1.0 + (0.5 * n)));
            double multiplicador = enemigo.evaluarDebilidad(elemento);

            return (int) (dano * multiplicador);
        }
    
        public int calcularDanoFisico(){
            return (int) (stats.getFuerza() * 1.25);
        }

        public int calcularDanoLimite(){
            return 0;
        }
        public List<Materia> getMateriasEquipadas(){
            return materiasEquipadas;
        }
        public boolean espacioRanuras() {
            return materiasEquipadas.size() < maxRanurasMateria;
        }
        public int calcularCostoMP(Elemento elemento) {
            // 1. Contamos cuántas materias de ese elemento hay
            int n = 0;
            for (Materia m : materiasEquipadas) {
                if (m.getElemento() == elemento){
                    n++;
                }
            }
            // 2. Aplicamos tu fórmula: 10 + (5 * n)
            return 10 + (5 * n);
        }
    }
    
    
    public void recibirXP(int xp){
        this.xpActual += xp;
        System.out.println("+" + xp + " XP.");

        int xpNecesaria = 10 * this.nivel; // XPnecesaria = 10 * Nivelactual


        System.out.println("Debug: recibirXP en Jugador.java");
        while (this.xpActual >= xpNecesaria){
            this.nivel++;
            this.xpActual -= xpNecesaria; // Restamos la XP usada para el nivel

            // Aumentos automáticos por nivel:
            this.stats.setHpMaximo(this.stats.getHpMaximo() + 10);
            this.stats.setMpMaximo(this.stats.getMpMaximo() + 5);
            this.stats.setFuerza(this.stats.getFuerza() + 4);
            this.stats.setMagia(this.stats.getMagia() + 6);
            
            System.out.println("SUBIDA DE NIEVEL! Cloud ahora es nivel " + this.nivel + ".");
            System.out.println("+10 HP Máximo");
            System.out.println("+5 MP Máximo");
            System.out.println("+4 Fuerza");
            System.out.println("+6 Magia");
        
        }
    
    }

    public void atacar(Enemigo enemigo){
        int danoHecho = this.busterSword.calcularDanoFisico();
        enemigo.getStats().recibirDMG(danoHecho);
        System.out.println("Debug: atacar en jugador.java");
        System.out.println("\n¡Cloud ataca con la " + busterSword.nombre + "!");
        System.out.println("Causa " + danoHecho + " de daño a " + enemigo.nombre + ".");
    }

    public void verMochila(Scanner input){
        boolean salir = false;

        while (!salir){
            System.out.println("\n--- MOCHILA ---");
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
            } catch (Exception e){
                System.out.println("Entrada inválida. Usa solo números.");
                continue;
            }

            if (opcion == 0){
                salir = true;
                System.out.println("Debug: para probar la gestión de mochila.");
            }else if (opcion > 0 && opcion <= mochila.size()){
                System.out.println("\nMateria de " + mochila.get(opcion - 1).getElemento() + " seleccionada.");
                    System.out.println("1. Equipar en Buster Sword");
                    System.out.println("2. Cancelar");
                    
                    try{
                        int subOpcion = Integer.parseInt(input.nextLine());
                        if (subOpcion == 1) {
                            this.equiparMateriaArma(opcion - 1); 
                        }
                    } catch (Exception e){
                        System.out.println("Entrada no válida. Intenta de nuevo.");
                    }
            }else{
                System.out.println("Opción no válida.");
            }
        }
    }

    public void equiparMateriaArma(int indiceMochila){
        // 1. Verificamos si el arma tiene espacio (límite de 5)
        if (this.busterSword.getMateriasEquipadas().size() < 5){
        
            Materia materia = this.mochila.remove(indiceMochila);
            this.busterSword.getMateriasEquipadas().add(materia);
        
            System.out.println("\n[!] ¡Materia de " + materia.getElemento() + " engastada en la Buster Sword!");
        }else{
            System.out.println("\n[!] El arma no tiene más ranuras disponibles (5/5).");
        }
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
    public void setNivel(int nivel){
        this.nivel = nivel;
    }

    public void setXpActual(int xpActual){
        this.xpActual = xpActual;
    }

    public void setChatarra(int chatarra){
        this.chatarra = chatarra;
    }

    public void setLimiteActual(int limiteActual){
        this.limiteActual = limiteActual;
    }

    public void setStats(Estadisticas stats){
        this.stats = stats;
    }

    public void setMochila(List<Materia> mochila){
        this.mochila = mochila;
    }

    public void setBusterSword(Arma busterSword){
        this.busterSword = busterSword;
    }

    public void setZonaActual(Zona zonaActual){
        this.zonaActual = zonaActual;
    }
}   




