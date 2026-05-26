package Componentes;

public class Mejora {
    private String nombre;
    private int costoChatarra;
    private TipoStat statAfectado;
    private int valorBono;
    
    /*
    * Constructor. Inicializa los atributos de la mejora, el nombre, 
    * el tipo de estadística que afecta, el valor del bono y su costo en chatarra.
    * @param nombre: String, tipo: TipoStat, bono: int, costo: int
    * @return Ninguno
    */
    public Mejora(String nombre, TipoStat tipo, int bono, int costo){
        this.nombre = nombre;
        this.costoChatarra = costo;
        this.statAfectado = tipo;
        this.valorBono = bono;
        
    }

    // Getters
    public String getNombre(){
        return nombre; 
    }
    public int getCostoChatarra(){ 
        return costoChatarra;
    }
    public TipoStat getTipoStat(){ 
        return statAfectado; 
    }
    public int getValorBono(){ 
        return valorBono; 
    }
    
}
