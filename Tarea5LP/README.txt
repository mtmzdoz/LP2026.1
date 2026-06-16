Nombre: Matilde Vásquez
Rol: 202473652-3
Rut: 21.715.078-7
Version SWI-Prolog: 10.1.9

1ra ppt 23

% antepasado(X, Z) :- progenitor(X, Z).
%es_requisito(X, Z) :- dependencia(X, Z).

% antepasado(X, Z) :- progenitor(X, Y), antepasado(Y, Z).
%es_requisito(X, Z) :- dependencia(X, Y), es_requisito(Y, Z).


2da

