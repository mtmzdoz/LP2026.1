Nombre: Matilde Vásquez
Rol: 202473652-3
Rut: 21.715.078-7
Version SWI-Prolog: 10.1.9

Instrucciones
-Abrir el código con SWI-Prolog y usar make. para compilar y que todo este bien
-Cuidado al usar  ´ o ’’ en vez de '', '' es lo correcto para las consultas


Para 4.2
Se decidio por hacerlo con una funcion auxiliar para separar la logica de verificar el semestre en especifico y 
si el alumno cumplia los prerrequisitos

Para 4.3
La descontruccion de la lista Solicitudes se hace manual mediante [Cabeza|Cola]
En el foro se comento sobre evitar "vacíos legales", por lo que si bien se podia hacer con el predicado auxiliar habilitado 
de la 4.2. Se decidio por hacer predicados auxiliares nuevos y de recursión lógica pura (cumple_requisitos, falta_requisito y pertenece), para no ocupar
funciones de nivel superior como se pide.