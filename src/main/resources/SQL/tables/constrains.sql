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

