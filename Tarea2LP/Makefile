CC = gcc #Compilador, en c++ era g++  
CFLAGS = -Wall -std=c11 -g #Opciones para compilar

OBJS = main.o tablero.o piezas.o armas.o # Lista de archivos

TARGET = Ajedrez # Nombre del ejecutable
all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJS) $(LDFLAGS)
# Reglas de compilación para cada archivo objeto
main.o: main.c main.h tablero.h piezas.h armas.h
	$(CC) $(CFLAGS) -c main.c

tablero.o: tablero.c tablero.h piezas.h
	$(CC) $(CFLAGS) -c tablero.c

piezas.o: piezas.c piezas.h
	$(CC) $(CFLAGS) -c piezas.c

armas.o: armas.c armas.h
	$(CC) $(CFLAGS) -c armas.c

#Limpia los archivos compilados, make clean
clean:
	rm -f $(OBJS) $(TARGET)

# Recompilar desde cero
rebuild: clean all

# Ejecutar programa
run: $(TARGET)
	./$(TARGET)

# Depurar con valgrind
valgrind: $(TARGET)
	valgrind --leak-check=full --show-leak-kinds=all ./$(TARGET)

.PHONY: all clean rebuild run valgrind