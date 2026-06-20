%asignatura(Orden, Sigla, SemestrePlan, Creditos).
%semestre1
asignatura(1, 'INF-129', 1, 7).
asignatura(2, 'MAT-070', 1, 6).
asignatura(3, 'MAT-060', 1, 6).
asignatura(4, 'FIS-100', 1, 6).
asignatura(5, 'HXW-006', 1, 3).
asignatura(6, 'EFI-200', 1, 2).

%semestre 2
asignatura(7,'IWG-400', 2, 7).
asignatura(8, 'MAT-071', 2, 6).
asignatura(9, 'MAT-061', 2, 6).
asignatura(10, 'FIS-110', 2, 6).
asignatura(11, 'HXW-007', 2, 3).
asignatura(12, 'EFI-201', 2, 2).

%semestre3
asignatura(13, 'INF-131', 3, 6).
asignatura(14, 'INF-132', 3, 5).
asignatura(15, 'MAT-081', 3, 6).
asignatura(16, 'FIS-112', 3, 6).
asignatura(17, 'HXW-008', 3, 3).
asignatura(18, 'AUX-200', 3, 4).

%semestre4
asignatura(19, 'INF-133', 4, 6).
asignatura(20, 'INF-135', 4, 6).
asignatura(21, 'MAT-053', 4, 5).
asignatura(22, 'FIS-114', 4, 6).
asignatura(23, 'HXW-009', 4, 3).
asignatura(24, 'AUX-201', 4, 4).

%semestre5
asignatura(25, 'INF-138', 5, 5).
asignatura(26, 'INF-140', 5, 5).
asignatura(27, 'INF-141', 5, 5).
asignatura(28, 'INF-143', 5, 5).
asignatura(29, 'INF-229', 5, 5).
asignatura(30, 'INF-142', 5, 5).

%semestre6
asignatura(31, 'INF-146', 6, 5).
asignatura(32, 'INF-145', 6, 5).
asignatura(33, 'INF-147', 6, 6).
asignatura(34, 'INF-144', 6, 6).
asignatura(35, 'INF-148', 6, 5).
asignatura(36, 'HXW-012', 6, 3).

%semestre7
asignatura(37, 'INF-156', 7, 5).
asignatura(38, 'INF-154', 7, 6).
asignatura(39, 'INF-157', 7, 6).
asignatura(40, 'INF-153', 7, 5).
asignatura(41, 'INF-158', 7, 5).
asignatura(42, 'AUX-202', 7, 3).

%semestre8
asignatura(43, 'INF-165', 8, 5).
asignatura(44, 'INF-164', 8, 5).
asignatura(45, 'AUX-219', 8, 5).
asignatura(46, 'INF-166', 8, 5).
asignatura(47, 'INF-167', 8, 5).
asignatura(48, 'INF-163', 8, 5).

%semestre9
asignatura(49, 'INF-302', 9, 5).
asignatura(50, 'INF-169', 9, 6).
asignatura(51, 'INF-170', 9, 6).
asignatura(52, 'INF-171', 9, 6).
asignatura(53, 'INF-168', 9, 7).

%semestre10
asignatura(54, 'INF-539', 10, 4).
asignatura(55, 'INF-540', 10, 6).
asignatura(56, 'INF-541', 10, 6).
asignatura(57, 'INF-538', 10, 14).


%dependencia(SiglaReq, SiglaAsignatura).
%semestre2
dependencia('MAT-070', 'MAT-071').
dependencia('MAT-060', 'MAT-061').
dependencia('FIS-100', 'FIS-110').
dependencia('HXW-006', 'HXW-007').
dependencia('EFI-200', 'EFI-201').

%semestre3
dependencia('INF-129', 'INF-131').
dependencia('MAT-070', 'INF-132').
dependencia('MAT-061', 'MAT-081').
dependencia('MAT-071', 'MAT-081').
dependencia('FIS-110', 'FIS-112').
dependencia('HXW-007', 'HXW-008').

%semestre4
dependencia('INF-131', 'INF-133').
dependencia('INF-129', 'INF-135').
dependencia('FIS-112', 'FIS-114').
dependencia('HXW-008', 'HXW-009').
dependencia('AUX-200', 'AUX-201').

%semestre5
dependencia('INF-133', 'INF-138').
dependencia('INF-133', 'INF-140').
dependencia('INF-133', 'INF-141').
dependencia('INF-132', 'INF-143'). 
dependencia('INF-133', 'INF-229').
dependencia('IWG-400', 'INF-142').

%semestre6
dependencia('INF-138', 'INF-146').
dependencia('INF-140', 'INF-145').
dependencia('INF-133', 'INF-147').
dependencia('INF-133', 'INF-144').
dependencia('INF-135', 'INF-148').
dependencia('HXW-009', 'HXW-012').

%semestre7
dependencia('INF-146', 'INF-156').
dependencia('INF-140', 'INF-154').
dependencia('INF-147', 'INF-157').
dependencia('MAT-053', 'INF-153').
dependencia('INF-135', 'INF-158').
dependencia('AUX-201', 'AUX-202').

%semestre8
dependencia('INF-146', 'INF-165').
dependencia('INF-145', 'INF-164').
dependencia('INF-157', 'INF-166').
dependencia('INF-142', 'INF-167').
dependencia('INF-144', 'INF-163').

%semestre9
dependencia('AUX-219', 'INF-302').
dependencia('AUX-202', 'INF-168').

%semestre10
dependencia('INF-168', 'INF-539').
dependencia('INF-164', 'INF-538').

% -------------------------------------------------------------------
%diapo23
%es_requisito(+SiglaOrigen, +SiglaDestino)
%Mediante recursión relacional se comprueba si SiglaOrigen es un prerequisito directo o indirecto de SiglaDestino.
es_requisito(SiglaOrigen, SiglaDestino) :- dependencia(SiglaOrigen, SiglaDestino).
es_requisito(SiglaOrigen, SiglaDestino) :- dependencia(SiglaOrigen, Requisito), es_requisito(Requisito, SiglaDestino).


% -------------------------------------------------------------------
%habilitado(+Aprobados, +Sigla)
%predicado auxiliar que verifica si una asignatura en especifica no ha sido cursada aún y si el alumno 
%cumple con todos sus prerrequisitos.
habilitado(Aprobados, Sigla) :-
    not(member(Sigla, Aprobados)), %se verifica que el ramo no este ya en la lista de aprobados
    findall(Requisito, dependencia(Requisito, Sigla), ListaRequisitos), %vemos prerequisitos del ramo
    subset(ListaRequisitos, Aprobados). %se verifica que la lista de requisitos este dentro de los aprobados

%ramos_inscribibles(+Aprobados, +Semestre, -Disponibles)
%Devuelve la lista Disponible, en la que se encuentran los ramos de un semestre en especifico
%que un alumno puede tomar si es que esta habilitado.
ramos_inscribibles(Aprobados, Semestre, Disponibles) :-
    findall(Sigla, asignatura(_, Sigla, Semestre, _), RamosSemestre), %tomar todos los ramos del semestre especifico
    include(habilitado(Aprobados), RamosSemestre, Disponibles). %filtrar con auxiliar


% -------------------------------------------------------------------
%pertenece(+X, +Lista)
%predicado auxiliar para verificar si un elemento X pertenece a una lista dada, implementado de forma recursiva
pertenece(X, [X|_]).
pertenece(X, [_|L]) :- pertenece(X, L).

%falta_requisito(+Aprobados, +Sigla)
%predicado auxiliar que falla si el alumno cumple todo, en caso contrario, retorna true si encuentra 
% al menos un prerrequisito de la asignatura que no esté en la lista de aprobados
falta_requisito(Aprobados, Sigla) :-
    dependencia(Requisito, Sigla),
    not(pertenece(Requisito, Aprobados)).

%cumple_requisitos(+Aprobados, +Sigla)
%predicado auxiliar que verifica que el ramo no esté aprobado y que cumpla sus requisitos
cumple_requisitos(Aprobados, Sigla) :-
    not(pertenece(Sigla, Aprobados)), %Si el ramo esta aprobado, se rechaza
    not(falta_requisito(Aprobados, Sigla)). %Se aprueba solo si no le falta ningun requ

%evaluar_inscripcion(+Aprobados, +Solicitudes, -Inscritos, -Rechazados)
%Procesa una lista de solicitudes manualmente mediante recursión estructural cola.
%Si un ramo cumple los requisitos, lo añade a inscritos, en caso contrario, a rechazados.
evaluar_inscripcion(_, [], [], []). %no hay mas solicitudes, se cierran los acumuladores

%Se deconstruye la lista y el ramo se agrega a inscritos
evaluar_inscripcion(Aprobados, [Ramo | RestoSolicitudes], [Ramo | RestoInscritos], Rechazados) :-
    cumple_requisitos(Aprobados, Ramo),
    evaluar_inscripcion(Aprobados, RestoSolicitudes, RestoInscritos, Rechazados).


%Se deconstruye la lista y el ramo se agrega a rechazados
evaluar_inscripcion(Aprobados, [Ramo | RestoSolicitudes], Inscritos, [Ramo | RestoRechazados]) :-
    not(cumple_requisitos(Aprobados, Ramo)),
    evaluar_inscripcion(Aprobados, RestoSolicitudes, Inscritos, RestoRechazados).