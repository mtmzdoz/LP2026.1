package Componentes;


public class Materia{
    private String nombre;
    private Elemento elemento;


    /*
    * Constructor. Inicializa una nueva instancia de Materia 
    * asignándole un tipo de elemento específico.
    * @param tipo: Elemento
    * @return Ninguno
    */
    public Materia(Elemento tipo){
        this.elemento = tipo;
        
    }

    //Getters
    public String getNombre(){
        return nombre;
    }

    public Elemento getElemento(){
        return elemento;
    }

}
