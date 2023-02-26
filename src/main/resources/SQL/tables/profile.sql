create table "Profile"
(
    email        varchar not null,
    first_name   varchar not null,
    last_name    varchar not null,
    phone_number varchar not null,
    pid          integer generated always as identity
        constraint "Profile_pk"
            primary key
);