package Componentes;

public interface Vulnerable{
    /*
    * Calcula y devuelve un multiplicador de daño basado en la relación entre 
    * el elemento de la magia recibida y las debilidades del receptor.
    * @param elementoMagia: Elemento
    * @return double
    */
    double evaluarDebilidad(Elemento elementoMagia);
} 
