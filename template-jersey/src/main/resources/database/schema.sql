
    create table Esame (
       id int8 not null,
        crediti int4 not null,
        dataRegistrazione date,
        insegnamento varchar(255),
        lode boolean not null,
        voto int4 not null,
        studente_id int8,
        primary key (id)
    );

    create table hibernate_sequences (
       sequence_name varchar(255) not null,
        next_val int8,
        primary key (sequence_name)
    );

    create table Studente (
       id int8 not null,
        annoIscrizione int4 not null,
        cognome varchar(255),
        matricola int4,
        nome varchar(255),
        primary key (id)
    );

    create table Utente (
       id int8 not null,
        email varchar(255),
        password varchar(255),
        primary key (id)
    );

    alter table Studente 
       add constraint UK_51larue3f216rej5kkkit7wdw unique (matricola);

    alter table Utente 
       add constraint UK_bt50wfqdv3s69nmx3u2r20suo unique (email);

    alter table Esame 
       add constraint FK45hllqrt7acnsiuo4vnaxj4ji 
       foreign key (studente_id) 
       references Studente;
