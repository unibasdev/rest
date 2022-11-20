BEGIN TRANSACTION;

insert into utente (id, email, password) values (1, 'admin@unibas.it', 'admin');

insert into studente (id, cognome, matricola, nome, annoIscrizione) values (2, 'Rossi', 1234, 'Mario', 2022);
insert into studente (id, cognome, matricola, nome, annoIscrizione) values (3, 'Verdi', 4321, 'Giuseppe', 2022);

insert into esame (id, crediti, dataRegistrazione, insegnamento, lode, voto, studente_id) values (4, 12, '2021-05-26', 'POO2', true, 30, 2);
insert into esame (id, crediti, dataRegistrazione, insegnamento, lode, voto, studente_id) values (5, 6, '2020-10-10', 'Calcolo', false, 24, 2);
insert into esame (id, crediti, dataRegistrazione, insegnamento, lode, voto, studente_id) values (6, 3, '2020-10-20', 'Inglese', false, 26, 3);	

insert into hibernate_sequences values('default', 7);

COMMIT;