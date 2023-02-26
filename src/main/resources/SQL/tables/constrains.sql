create table "Constrains"
(
    constraint_id  integer generated always as identity
        constraint constraint_id
            primary key,
    type_id        integer not null,
    uid            integer not null,
    data           integer,
    permanent_flag boolean not null,
    week_number    integer not null,
    start_date     integer not null,
    end_date       integer not null
);
alter table "Constrains"
    add constraint "Constrains_User_uid_fk"
        foreign key (uid) references "User";

alter table "Constrains"
    add constraint "Constrains___fk"
        foreign key (type_id) references "Constraint_type";

alter table "Constrains"
    rename constraint "Constrains___fk" to "Constrains_Constraint_type_type_id_fk";


