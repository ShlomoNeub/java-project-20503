create table public.profiles
(
    pid    integer generated always as identity
        constraint profiles_pk
            primary key,
    email  varchar not null
        constraint profiles_pk2
            unique,
    f_name varchar not null,
    l_name varchar not null
);

alter table public.profiles
    owner to postgres;

