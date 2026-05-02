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
    public int getHpActual(){
        return hpActual;
    }

    public int getHpMaximo(){
        return hpMaximo;
    }

    public int getMpActual(){
        return mpActual;
    }

    public int getMpMaximo(){
        return mpMaximo;
    }

    public int getFuerza(){
        return fuerza;
    }

    public int getMagia(){
        return magia;
    }

    //Setters
    public void setHpActual(int hpActual){
        this.hpActual = hpActual;
    }

    public void setHpMaximo(int hpMaximo){
        this.hpMaximo = hpMaximo;
    }

    public void setMpActual(int mpActual){
        this.mpActual = mpActual;
    }

    public void setMpMaximo(int mpMaximo){
        this.mpMaximo = mpMaximo;
    }

    public void setFuerza(int fuerza){
        this.fuerza = fuerza;
    }

    public void setMagia(int magia){
        this.magia = magia;
    }
}
