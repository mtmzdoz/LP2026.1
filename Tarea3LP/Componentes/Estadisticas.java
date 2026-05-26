package Componentes;

public class Estadisticas{
    private int hpActual;
    private int hpMaximo;
    private int mpActual;
    private int mpMaximo;
    private int fuerza;
    private int magia;

    /*
    * Constructor. Inicializa los valores máximos de HP, MP, fuerza y magia, 
    * estableciendo los valores actuales al nivel del máximo.
    * @param hpMaximo: int, mpMaximo: int, fuerza: int, magia: int
    * @return Ninguno
    */
    public Estadisticas(int hpMaximo, int mpMaximo, int fuerza, int magia){
        this.hpMaximo = hpMaximo;
        this.hpActual = hpMaximo;
        this.mpMaximo = mpMaximo;
        this.mpActual = mpMaximo;
        this.fuerza = fuerza;
        this.magia = magia;
    }

    /*
    * Reduce la vida de los enemigos basándose en un valor de daño recibido, 
    * asegurando que la vida no baje de cero.
    * @param valor: int
    * @return void
    */
    public void recibirDMG(int valor){
        this.hpActual = Math.max(0, this.hpActual - valor);
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
