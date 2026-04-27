package Mapa;

import java.util.List;
import java.util.Scanner;

import Componentes.Mejora;
import Entidades.Jugador;

public class Sector7 extends Zona{
    private List<Mejora> tiendaLocal;


    public Sector7() {
        super("Sector 7", 1, null); //Null por mientras pero deberia ser soldado común
        this.tiendaLocal = tiendaLocal;
    }

    public void iniciarSimulador(Jugador Cloud){
        
    }

    public void abrirTienda(Jugador Cloud){
        
    }


    public void accionZona(Jugador Cloud){
        Scanner Input = new Scanner(System.in);
        System.out.println("\n--- BIENVENIDO AL SECTOR 7 ---");
        System.out.println("1. Entrar al simulador de combate");
        System.out.println("2. Abrir Tienda de Chatarra (Comprar mejoras)"); 
        System.out.println("3. Volver al menú de viaje");
        
        int opcion = Input.nextInt();
        if (opcion == 1) iniciarSimulador(Cloud);
        else if (opcion == 2) abrirTienda(Cloud);
    }


}
