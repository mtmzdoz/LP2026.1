package Componentes;

public class Estadisticas {
    private int hpActual;
    private int hpMaximo;
    private int mpActual;
    private int mpMaximo;
    private int fuerza;
    private int magia;

    //Constructor
    public Estadisticas(int hpMaximo, int mpMaximo, int fuerza, int magia){
        this.hpMaximo = hpMaximo;
        this.hpActual = hpMaximo;
        this.mpMaximo = mpMaximo;
        this.mpActual = mpMaximo;
        this.fuerza = fuerza;
        this.magia = magia;
    }

    public void recibirDMG(int valor){
        
    }

    //Getters 
    public int gethpActual(){
        return hpActual;
    }

    public int gethpMaximo(){
        return hpMaximo;
    }

    public int getmpActual(){
        return mpActual;
    }

    public int getmpMaximo(){
        return mpMaximo;
    }

    public int getfuerza(){
        return fuerza;
    }

    public int getmagia(){
        return magia;
    }

    //Setters
    public void sethpActual(int hpActual){
        this.hpActual = hpActual;
    }

    public void sethpMaximo(int hpMaximo){
        this.hpMaximo = hpMaximo;
    }

    public void setmpActual(int mpActual){
        this.mpActual = mpActual;
    }

    public void setmpMaximo(int mpMaximo){
        this.mpMaximo = mpMaximo;
    }

    public void setfuerza(int fuerza){
        this.fuerza = fuerza;
    }

    public void setmagia(int magia){
        this.magia = magia;
    }
}
