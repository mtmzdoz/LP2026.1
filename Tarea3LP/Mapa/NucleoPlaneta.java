package Mapa;

import java.util.List;
import java.util.Scanner;

import Componentes.Elemento;
import Componentes.Materia;

import Entidades.Jugador;
import Entidades.Sephiroth;

public class NucleoPlaneta extends Zona{
    private int materiasMinimasRequeridas;

    /*
    * Constructor. Inicializa el nombre, nivel requerido, enemigos disponibles y zona de retorno
    * y carga los artículos disponibles en la tienda de chatarra.
    * @param Ninguno
    * @return Ninguno
    */
    public NucleoPlaneta(Zona retorno){
        super("Núcleo del Planeta", 20, null, retorno);
        this.materiasMinimasRequeridas = 2;
    }

    /*
    * Verifica si el jugador cumple con los requisitos para ingresar a la zona.
    * @param Cloud: Jugador
    * @return boolean
    */
    @Override
    public boolean validarAcceso(Jugador Cloud){
        boolean nivelSuficiente = Cloud.getNivel() >= this.nivelRequerido;
        boolean materiasSuficientes = Cloud.getBusterSword().getMateriasEquipadas().size() >= this.materiasMinimasRequeridas;

        if (!nivelSuficiente || !materiasSuficientes){
            
            System.out.println("Requisitos para "+ this.nombre + ": Nivel " + this.nivelRequerido + " y " + this.materiasMinimasRequeridas + " Materias equipadas.");
            return false;
        }
        return true;
    }

    /*
    * Punto de entrada principal cuando el jugador entra al Núcleo. 
    * Muestra el encabezado de la zona e inicia el combate.
    * @param Cloud: Jugador
    * @return void
    */
    public void accionZona(Jugador Cloud){
        System.out.println("\n==== Núcleo del Planeta====");
        iniciarCombate(Cloud);
    }

    /*
    * Ejecuta la lógica del combate final por turnos contra Sephiroth. Gestiona los
    * ataques del jugador y las condiciones de victoria o derrota.
    * @param Cloud: Jugador
    * @return void
    */
    public void iniciarCombate(Jugador Cloud){
        Scanner input = new Scanner(System.in);
        Sephiroth jefeFinal = new Sephiroth();

        while (Cloud.getStats().getHpActual() > 0 && jefeFinal.getStats().getHpActual() > 0){
            boolean turnoCloud = false;

            while (!turnoCloud){
                System.out.println("==================================");
                System.out.println("Cloud HP: " + Cloud.getStats().getHpActual() + " | MP: " + Cloud.getStats().getMpActual() + " | Limite: " + Cloud.getLimiteActual() + "/100");
                System.out.println("Sephiroth HP: " + jefeFinal.getStats().getHpActual());
                System.out.println("Turno " + jefeFinal.getContadorSuperNova() + "/10 para Supernova");
                System.out.println("==================================");
                System.out.println("1. Ataque Físico");
                System.out.println("2. Ataque Mágico");
                System.out.println("3. Ataque Límite");
                System.out.print("¿Qué harás?: ");

                int opcion = -1;
                try{
                    opcion = Integer.parseInt(input.nextLine());
                }catch (NumberFormatException e){
                    System.out.println("Entrada inválida. Intenta de nuevo.");
                    continue;
                }

                switch (opcion){
                    case 1:
                        Cloud.atacar(jefeFinal);
                        turnoCloud = true;
                        break;
                    case 2:
                        List<Materia> materiasEquipadas = Cloud.getBusterSword().getMateriasEquipadas();
                        if (materiasEquipadas.isEmpty()){
                            System.out.println("No tienes materias equipadas.");
                            continue;
                        }

                        System.out.println("Elige una materia para usar:");
                        for (int i = 0; i < materiasEquipadas.size(); i++){
                            System.out.println((i + 1) + ". Materia de " + materiasEquipadas.get(i).getElemento());
                        }

                        System.out.println("0. Cancelar y volver");
                        System.out.print("Selecciona tu hechizo: ");

                        int subOpcion = -1;
                        try{
                            subOpcion = Integer.parseInt(input.nextLine());

                        }catch (NumberFormatException e){
                            System.out.println("Entrada inválida. Intenta de nuevo.");
                            continue;
                        }

                        if (subOpcion == 0){
                            break;
                        }

                        int indiceMateria = subOpcion - 1;
                        if (indiceMateria >= 0 && indiceMateria < materiasEquipadas.size()){
                            Elemento elemento = materiasEquipadas.get(indiceMateria).getElemento();
                            if (elemento == Elemento.CURA){
                                Cloud.atacarMagia(elemento, null);
                                turnoCloud = true;
                            }else{
                                Cloud.atacarMagia(elemento, jefeFinal);  
                            }
                        }
                        turnoCloud = true;
                        break;

                    case 3:
                        if (Cloud.getLimiteActual() >= 100){
                            Cloud.ataqueLimite(jefeFinal);
                            System.out.println("¡El ataque límite detiene la Supernova!");
                            jefeFinal.setContadorSuperNova(0); 
                            turnoCloud = true;
                        }else{
                            System.out.println("¡Barra de Límite insuficiente! (" + Cloud.getLimiteActual() + "/100)");
                        }
                        break;
                }
            }

            if (jefeFinal.getStats().getHpActual() > 0){
                jefeFinal.atacar(Cloud);
            }
        }

        if (Cloud.getStats().getHpActual() <= 0){
            System.out.println("Sephiroth te ha derrotado.");
            Cloud.derrota(this.zonaRetorno);
        }else{
            try{
                Thread.sleep(1500); 
            }catch (InterruptedException e){
            }
            System.out.println("\n--- ¡VICTORIA FINAL! ---");
            System.out.println("Has derrotado a Sephiroth y salvado el planeta.");
            System.out.println("https://youtu.be/oPE-Ivy9LLg?si=9tNzcM1EkXFcB2tr");
            try{
                Thread.sleep(1000); 
            }catch (InterruptedException e){
            }
            System.exit(0); 
        }
    }
}
