import re

#EBNF
# == Básicas == #
digito = r"[0-9]"
letra_min = r"[a-z]"
letra_may = r"[A-Z]"
letra = rf"(?:{letra_min}|{letra_may})"
palabra = rf"(?:{letra}(?:{letra}|{digito})+)"

# == Tipos de datos y operaciones == #
operacion = r"(?:\+|\-|\/|\*|==|=|<|>)"
booleano = r"(?:true|false)"
cadena_texto = rf'(?:"{palabra}")'
caracter = rf"(?:'{letra}')"
valor = rf"(?:{digito}+|{booleano}|{cadena_texto}|{caracter})"
tipo_dato = r"(?:int|bool|char|string|void)"

# == Estilos == #
snake_case = rf"(?:{letra_min}+(?:_{letra_min}+)+)"
camelCase = rf"(?:{letra_min}+(?:{letra_may}{letra_min}*)+)"
PascalCase = rf"(?:{letra_may}{letra_min}*)+"
desconocido = r"[a-zA-Z_]+"
nombre_valido= rf"(?:{snake_case}|{camelCase}|{PascalCase}|{desconocido})"

# == Estructuras avanzadas == #
condicion = rf"\(\s*{nombre_valido}(?:\s*{operacion}\s*{valor})?\s*\)"
estructura_control = rf"(?:if|while)\s*{condicion}\s*\{{\s*"
cierre_bloque = r"\s*\}\s*"
parametros = rf"(?:{tipo_dato}\s+{nombre_valido}(?:\s*,\s*{tipo_dato}\s+{nombre_valido})*)?"
declaracion_funcion = rf"{tipo_dato}\s+({nombre_valido})\s*\(\s*(?:{parametros})?\s*\)\s*\{{"
declaracion_variable = rf"{tipo_dato}\s+{nombre_valido}\s*{operacion}\s*{valor}\s*;"
retorno = rf"return\s+({nombre_valido})\s*;"
comentario_one_line = r"//[^\n]*" 
comentario_multi_line = r"/\*[\s\S]*?\*/"

Firmafuncion = rf"{tipo_dato}\s+([a-zA-Z_]+)\s*(?:\(|\)|\{{)"
IntentoEstructura = rf"(?:\s*)(?:if|while)\b"
IntentoVariable = rf"{tipo_dato}\s+({nombre_valido})"
IntentoFallidoVar = rf"({tipo_dato})[a-zA-Z_]"
IntentoRetorno  = rf"return\s+({nombre_valido})"
Comentarios = rf"{comentario_multi_line}|{comentario_one_line}"



"""
***
Parametro 1 : nombre_archivo (str)
***
Tipo de Retorno: Lista
***
Lee el archivo de texto original, aplica el EBNF de comentarios para 
eliminarlos y retorna una lista con las líneas limpias.
"""
def LimpiarArchivo(nombre_archivo):
    with open(nombre_archivo, "r", encoding="utf-8") as archivo:
        Contenido = archivo.read()
        
    LineaLimpia = re.sub(Comentarios, "", Contenido)
    ContenidoLimpio = LineaLimpia.splitlines()
    
    return ContenidoLimpio

"""
    ***
    Parametro 1 : Lista (list)
    ***
    Tipo de Retorno: Lista de Lista
    ***
    Recorre las líneas del archivo, lee la firma y agrupa el código completo en una lista. 
    Cada lista dentro de la lista representa una funcion completa
    """
def LeerArchivo(ListaLineas):
    Funciones = []
    FuncionActual= []

    for linea in ListaLineas:
        linea = linea.rstrip()

        if not linea:
            continue

        if re.search(Firmafuncion, linea):
        
            if FuncionActual:
                Funciones.append(FuncionActual) # [ [ ] ]
            FuncionActual = [linea + "\n"] 
        else:
            FuncionActual.append(linea + "\n")   
    
    if FuncionActual:
        Funciones.append(FuncionActual)
    
    return Funciones

"""
    ***
    Parametro 1 : Lista (list)
    ***
    Tipo de Retorno: None
    ***
    Escribe cada función en el txt correspondiente según el 
    estilo de su nombre (snake, camel, pascal o desconocido).
    """
def EscribirTXT(Funciones):
    #Para q cuando corra el codigo los archivos esten en blanco ya que el open de abajo ocupa a (append) y no se limpia el
    #TXT cada  vez que se corre 
    TXTbase = ["snake.txt", "camel.txt", "pascal.txt", "desconocido.txt"]
    for archivo in TXTbase:
        with open(archivo, "w", encoding="utf-8") as texiste:
            pass

    for funcion in Funciones: 
        firma = funcion[0].strip()
        Funcion = re.search(Firmafuncion, firma)

        if Funcion:
            nombreFuncion = Funcion.group(1)

            if re.fullmatch(snake_case, nombreFuncion):
                nombreArch= "snake.txt" 
            elif re.fullmatch(camelCase, nombreFuncion):
                nombreArch = "camel.txt"
            elif re.fullmatch(PascalCase, nombreFuncion):
                nombreArch = "pascal.txt"
            else:
                nombreArch = "desconocido.txt"
            
            with open(nombreArch, "a", encoding="utf-8") as TXT:
                TXT.writelines(funcion)
            

"""
    ***
    Parametro 1 : Lista (list)
    ***
    Tipo de Retorno: Diccionario (dict)
    ***
    Analiza el interior de cada función buscando variables, estructuras de control y retornos. 
    Valida la sintaxis, el balance de llaves y genera un reporte detallado por practicante.
    """
def AnalizarFunciones(Funciones):
    ReportePracticantes = { "Snake": { "Funciones": [], "VariablesTotal": 0, "EstiloDif": [], "ErrorSintax": [] },
                            "Camel": { "Funciones": [], "VariablesTotal": 0, "EstiloDif": [], "ErrorSintax": [] },
                            "Pascal":{ "Funciones": [], "VariablesTotal": 0, "EstiloDif": [], "ErrorSintax": [] },
                            "Desconocido":{ "Funciones": [], "VariablesTotal": 0, "EstiloDif": [], "ErrorSintax": [] }
                        }

    for funcion in Funciones: 
        firma = funcion[0].strip()
        FuncionDeclarada = re.search(Firmafuncion, firma)

        #Para poder leer los q e stan en una linea
        if "{" in firma:
            Info = firma.split("{", 1) #queda " funcion , lo q esta dentro de las llaves = 1"
            firma = Info[0] + "{" #reconstruir 
            
            InfoDentroLLaves = Info[1].strip()
            if InfoDentroLLaves:
                funcion.insert(1, InfoDentroLLaves) #
        else:
            firma = firma 
        
        if not FuncionDeclarada:
            continue
        
        NombreFuncion = FuncionDeclarada.group(1)
    
        if re.fullmatch(snake_case, NombreFuncion):
            Estilo= "Snake"
        elif re.fullmatch(camelCase, NombreFuncion): 
            Estilo= "Camel"
        elif re.fullmatch(PascalCase, NombreFuncion): 
            Estilo= "Pascal"
        else:
            Estilo= "Desconocido"

        ReportePracticantes[Estilo]["Funciones"].append(NombreFuncion)

        LlavesAbiertas = firma.count('{')
        LlavesCerradas = 0
        if re.search(cierre_bloque, firma):
            LlavesCerradas = firma.count('}')

        Coma = re.search(r",\s*\)", firma) #por si hay más de 1 arg y ver casos tipo int funcion(arg,){}
        Funcion = re.fullmatch(declaracion_funcion, firma) 
        if not Funcion or Coma:
            ErFuncion = f"Error en '{NombreFuncion}': Sintaxis incorrecta en la firma (faltan '()', '{{' o parámetros mal escritos) en la linea '{firma}'"
            ReportePracticantes[Estilo]["ErrorSintax"].append(ErFuncion)


        #Interior Funcion
        for linea in funcion[1:]:
            linea = linea.strip()
            
            if not linea:
                continue

            if re.fullmatch(snake_case, NombreFuncion):
                Estilo= "Snake"
            elif re.fullmatch(camelCase, NombreFuncion): 
                Estilo= "Camel"
            elif re.fullmatch(PascalCase, NombreFuncion): 
                Estilo= "Pascal"
            else:
                Estilo= "Desconocido"

            ComillasDobles = False
            ComillasSimples = False
            
            #Para ignorar el contenido de los string
            for caracter in linea:
                if caracter == '"' and not ComillasSimples:
                    ComillasDobles = not ComillasDobles
                    continue 
                
                if caracter == "'" and not ComillasDobles:
                    ComillasSimples = not ComillasSimples
                    continue
                
                if not ComillasDobles and not ComillasSimples:
                    if caracter == '{':
                        LlavesAbiertas += 1
                    elif caracter == '}':
                        LlavesCerradas += 1

            VariableDeclarada = re.search(IntentoVariable, linea)

            if VariableDeclarada:
                ReportePracticantes[Estilo]["VariablesTotal"] += 1

                NombreVariable = VariableDeclarada.group(1)

                if re.fullmatch(snake_case, NombreVariable):
                    EstiloVariable = "Snake"
                elif re.fullmatch(camelCase, NombreVariable): 
                    EstiloVariable = "Camel"
                elif re.fullmatch(PascalCase, NombreVariable): 
                    EstiloVariable = "Pascal"
                else:
                    EstiloVariable = "Desconocido"

                if EstiloVariable != Estilo and Estilo != "Desconocido":
                    VariableDifEstilo = f""
                    ReportePracticantes[Estilo]["EstiloDif"].append(VariableDifEstilo)

                if re.search(IntentoVariable, linea):
                    if not re.fullmatch(declaracion_variable, linea):
                        ErrorPYC = f"Error en '{NombreFuncion}': Falta ';' en la línea '{linea}'"
                        ReportePracticantes[Estilo]["ErrorSintax"].append(ErrorPYC)
                        
            else:
                
                if re.search(IntentoFallidoVar, linea):
                    ErrorVariable = f"Error en '{NombreFuncion}': Falta espacio entre el tipo y el nombre en '{linea}'"
                    ReportePracticantes[Estilo]["ErrorSintax"].append(ErrorVariable)

            if re.search(IntentoEstructura, linea):
                if not re.fullmatch(estructura_control,linea):
                    ErrorCondicion = f"Error en '{NombreFuncion}': Condición mal escrita o faltan paréntesis '()' en la línea '{linea}'"
                    ReportePracticantes[Estilo]["ErrorSintax"].append(ErrorCondicion) 

                if "{" not in linea:
                    LlavesAbiertas += 1
                    ErrorLlave = f"Error en '{NombreFuncion}': Falta '{{' en la línea '{linea}'"
                    ReportePracticantes[Estilo]["ErrorSintax"].append(ErrorLlave)

            if re.search(IntentoRetorno, linea) :
                if not re.search(retorno, linea):
                    ErrorPYC = f"Error en '{NombreFuncion}': Falta ';' en la línea '{linea}'"
                    ReportePracticantes[Estilo]["ErrorSintax"].append(ErrorPYC)
                    
    
        if LlavesAbiertas > LlavesCerradas:
            LLavesFaltantes = LlavesAbiertas - LlavesCerradas
            ErrorLlaves = f"Error en '{NombreFuncion}': Bloque sin cerrar. Faltan {LLavesFaltantes} llaves '}}' de cierre."
            ReportePracticantes[Estilo]["ErrorSintax"].append(ErrorLlaves)
            
        elif LlavesCerradas > LlavesAbiertas:
            Sobrantes = LlavesCerradas - LlavesAbiertas
            ErrorLlaves = f"Error en '{NombreFuncion}': Llaves desbalanceadas. Sobran {Sobrantes} '}}'. Llaves Abiertas: {LlavesAbiertas}, Llaves Cerradas: {LlavesCerradas}"
            ReportePracticantes[Estilo]["ErrorSintax"].append(ErrorLlaves)
        
    return ReportePracticantes
        

"""
    ***
    Parametro 1 : Diccionario (dict)
    ***
    Tipo de Retorno: None
    ***
    Procesa el diccionario con los resultados del analisis y genera una salida visual por consola con las 
    estadísticas de cada practicante y los errores encontrados.
    """
def ImprimirReporte(ReporteFinal):
    print("=== REPORTE DE EVALUACIÓN DE PRACTICANTES ===")
    
    for Estilo in ["Snake", "Camel", "Pascal"]:
        Datos = ReporteFinal[Estilo]

        print(f"PRACTICANTE: {Estilo}")
        CantidadFunciones = len(Datos["Funciones"])
        NombresFunciones = ", ".join(Datos["Funciones"])
        print(f"- Funciones creadas: {CantidadFunciones} ({NombresFunciones})")
        print(f"- Variables declaradas: {Datos['VariablesTotal']}")
        
        # Estilos Dif
        CantidadEstilosDif = len(Datos["EstiloDif"])
        print(f"- Diferencias de Estilo: {CantidadEstilosDif}", end="")
        if CantidadEstilosDif > 0:
            print(f"")
        else:
            print()
            
        # Errores Sintax
        CantidadErSintax = len(Datos["ErrorSintax"])
        print(f"- Errores de Sintaxis: {CantidadErSintax}")
        for error in Datos["ErrorSintax"]:
            print(f"- {error}")
 


SinComentarios = LimpiarArchivo("Programa.txt")
FuncionesSeparadas = LeerArchivo(SinComentarios)
EscribirTXT(FuncionesSeparadas)
ContenidoAnalizado = AnalizarFunciones(FuncionesSeparadas)
ImprimirReporte(ContenidoAnalizado)


