Nombre: Matilde Vásquez
Rol: 202473652-3
Rut: 21.715.078-7
Version SWI-Prolog: 10.1.9

1ra ppt 23

% antepasado(X, Z) :- progenitor(X, Z).
%es_requisito(X, Z) :- dependencia(X, Z).

% antepasado(X, Z) :- progenitor(X, Y), antepasado(Y, Z).
%es_requisito(X, Z) :- dependencia(X, Y), es_requisito(Y, Z).

Consulta es_requisito('FIS-100', 'FIS-112').
R: true


2da


?- ramos_inscribibles([], 2, Disponibles).
R: Disponibles = [’IWG-400’].

?- ramos_inscribibles(['INF-129', 'MAT-070'], 3, Disponibles).
R: Disponibles = [’INF-131’, ’INF-132’, ’AUX-200’].

evaluar_inscripcion(['INF-129', 'MAT-070'], ['INF-131', 'MAT-071', 'FIS-110'], Inscritos, Rechazados).
R: Inscritos = [’INF-131’, ’MAT-071’],
   Rechazados = [’FIS-110’].
