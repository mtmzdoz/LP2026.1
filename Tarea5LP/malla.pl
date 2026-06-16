%asignatura(Orden, Sigla, SemestrePlan, Creditos).
%semestre1.
asignatura(1, 'INF-129', 1, 7).
asignatura(2, 'MAT-070', 1, 6).
asignatura(3, 'MAT-060', 1, 6).
asignatura(4, 'FIS-100', 1, 6).
asignatura(5, 'HXW-006', 1, 3).
asignatura(6, 'EFI-200', 1, 2).

%semestre 2.
asignatura(7,'IWG-400', 2, 7).
asignatura(8, 'MAT-071', 2, 6).
asignatura(9, 'MAT-061', 2, 6).
asignatura(10, 'FIS-110', 2, 6).
asignatura(11, 'HXW-007', 2, 3).
asignatura(12, 'EFI-201', 2, 2).

%semestre3.
asignatura(13, 'INF-131', 3, 6).
asignatura(14, 'INF-132', 3, 5).
asignatura(15, 'MAT-081', 3, 6).
asignatura(16, 'FIS-112', 3, 6).
asignatura(17, 'HXW-008', 3, 3).
asignatura(18, 'AUX-200', 3, 4).

%semestre4.
asignatura(19, 'INF-133', 4, 6).
asignatura(20, 'INF-135', 4, 6).
asignatura(21, 'MAT-053', 4, 5).
asignatura(22, 'FIS-114', 4, 6).
asignatura(23, 'HXW-009', 4, 3).
asignatura(24, 'AUX-201', 4, 3).

%semestre5.
asignatura(25, 'INF-138', 5, 5).
asignatura(26, 'INF-140', 5, 5).
asignatura(27, 'INF-141', 5, 5).
asignatura(28, 'INF-143', 5, 5).
asignatura(29, 'INF-229', 5, 5).
asignatura(30, 'INF-142', 5, 5).

%semestre6.
asignatura(31, 'INF-146', 6, 5).
asignatura(32, 'INF-145', 6, 5).
asignatura(33, 'INF-147', 6, 6).
asignatura(34, 'INF-144', 6, 6).
asignatura(35, 'INF-148', 6, 5).
asignatura(36, 'HXW-012', 6, 3).

%semestre7.
asignatura(37, 'INF-156', 7, 5).
asignatura(38, 'INF-154', 7, 6).
asignatura(39, 'INF-157', 7, 6).
asignatura(40, 'INF-153', 7, 5).
asignatura(41, 'INF-158', 7, 5).
asignatura(42, 'AUX-202', 7, 3).

%semestre8.
asignatura(43, 'INF-165', 8, 5).
asignatura(44, 'INF-164', 8, 5).
asignatura(45, 'AUX-219', 8, 5).
asignatura(46, 'INF-166', 8, 5).
asignatura(47, 'INF-167', 8, 5).
asignatura(48, 'INF-163', 8, 5).

%semestre9.
asignatura(49, 'INF-302', 9, 5).
asignatura(50, 'INF-169', 9, 6).
asignatura(51, 'INF-170', 9, 6).
asignatura(52, 'INF-171', 9, 6).
asignatura(53, 'INF-168', 9, 6).

%semestre10.
asignatura(54, 'INF-539', 10, 4).
asignatura(55, 'INF-540', 10, 6).
asignatura(56, 'INF-541', 10, 6).
asignatura(57, 'INF-538', 10, 14).


%dependencia(SiglaReq, SiglaAsignatura).
%semestre1.
dependencias('', 'INF-129').
dependencias('', 'MAT-070').    
dependencias('', 'MAT-060').
dependencias('', 'FIS-100').
dependencias('', 'HXW-006').
dependencias('', 'EFI-200').

%semestre2.
dependencias('', 'IWG-400').
dependencias('MAT-070', 'MAT-071').
dependencias('MAT-060', 'MAT-061').
dependencias('FIS-100', 'FIS-110').
dependencias('HXW-006', 'HXW-007').
dependencias('EFI-200', 'EFI-201').

%semestre3.
dependencias('INF-129', 'INF-131').
dependencias('MAT-071', 'INF-132').
dependencias('MAT-061', 'MAT-081').
dependencias('MAT-071', 'MAT-081').
dependencias('FIS-110', 'FIS-112').
dependencias('HXW-007', 'HXW-008').
dependencias('', 'AUX-200').

%semestre4.
dependencias('INF-131', 'INF-133').
dependencias('INF-129', 'INF-135').
dependencias('', 'MAT-053').
dependencias('FIS-112', 'FIS-114').
dependencias('HXW-008', 'HXW-009').
dependencias('AUX-200', 'AUX-201').

%semestre5.
dependencias('INF-133', 'INF-138').
dependencias('INF-133', 'INF-140').
dependencias('INF-133', 'INF-141').
dependencias('INF-132', 'INF-143'). 
dependencias('INF-133', 'INF-229').
dependencias('IWG-400', 'INF-142').

%semestre6.
dependencias('INF-138', 'INF-146').
dependencias('INF-140', 'INF-145').
dependencias('INF-133', 'INF-147').
dependencias('INF-133', 'INF-144').
dependencias('INF-135', 'INF-148').
dependencias('HXW-009', 'HXW-012').

%semestre7.
dependencias('INF-146', 'INF-156').
dependencias('INF-140', 'INF-154').
dependencias('INF-147', 'INF-157').
dependencias('MAT-053', 'INF-153').
dependencias('INF-135', 'INF-158').
dependencias('AUX-201', 'AUX-202').

%semestre8.
dependencias('INF-146', 'INF-165').
dependencias('INF-145', 'INF-164').
dependencias('', 'AUX-219').
dependencias('INF-157', 'INF-166').
dependencias('INF-142', 'INF-167').
dependencias('INF-144', 'INF-163').

%semestre9.
dependencias('AUX-219', 'INF-302').
dependencias('', 'INF-169').
dependencias('', 'INF-170').
dependencias('', 'INF-171').
dependencias('AUX-202', 'INF-168').

%semestre10.
dependencias('INF-168', 'INF-539').
dependencias('', 'INF-540').
dependencias('', 'INF-541').
dependencias('INF-164', 'INF-538').




es_requisito(SiglaOrigen, SiglaDestino) :- dependencias(SiglaOrigen, SiglaDestino).
es_requisito(SiglaOrigen, SiglaDestino) :- dependencias(SiglaOrigen, Requisito), es_requisito(Requisito, SiglaDestino).

