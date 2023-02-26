create table "Roles"
(
    role_id    integer generated always as identity
        constraint "Roles_pk"
            primary key,
    role_name  varchar not null ,
    role_level integer not null
);

