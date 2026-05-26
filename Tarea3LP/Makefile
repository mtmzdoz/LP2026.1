# Variables
JAVAC = javac
JAVA  = java
FLAGS = -Xlint:all 
# Compila todos los .java
all:
	$(JAVAC) $(FLAGS) Componentes/*.java Entidades/*.java Mapa/*.java Main.java

# Ejecutar Main
run: all
	$(JAVA) Main

# Limpiar .class
clean:
	find . -name "*.class" -type f -delete