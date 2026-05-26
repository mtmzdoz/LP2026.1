Nombre: Matilde Vásquez
Rol: 202473652-3
Versión Java: jkd-21.0.10 



Instrucciones
1. Introducir un número por input, ya que por ej si se introduce 001 tomara el 1. Para los otros casos como 200 no hay problema ya que tira un print de error
2. Luego de que derrotan a Cloud ya sea Sephiroth o los enemigos, este tiene que entrar a la posada para poder restaurar sus stats y asi poder volver a entrar a las batallas
3. ¡Una vez equipadas las materias en la espada NO se pueden sacar! Elegir bien cual equipar
4. Si bien aparece la opcion de ataque límite, este solo puede ser aplicado cuando esta al 100, igualmente sale un mensaje si se quiere usar antes de qe este cargada

Consideraciones
1. En EnemigoSalvaje no se hicieron get/set y se decidio por el add ya que era para inicializar, finalmente estos valores no se modifican durante el juego
2. En el pdf no se menciona pero en el caso de que en EnemigoSimulador aparezcan 2 soldados, estos atacan por turno al igual que EnemigosSalvaje
3. Cuando se pierde en el Simulador no se baja el MP a 0, solo el HP
4. A la mochila se agrega todo, ya que no hay limite. Por lo que las materias se peuden repetir
5. Se implementaron bloques try-catch con NumberFormatException en todos los menús (menos los de Sephiroth). Esto garantiza que el juego no se cierre si el usuario ingresa letras o caracteres especiales,
  y que todo se maneje con numeros. 
