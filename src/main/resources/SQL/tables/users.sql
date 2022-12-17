create table public.users
(
    uid      integer generated always as identity
        constraint users_pk
            primary key,
    username varchar not null
        constraint users_pk2
            unique,
    password varchar,
    pid      integer not null
        constraint users_profiles_pid_fk
            references public.profiles
);

alter table public.users
    owner to postgres;

create unique index users_uid_uindex
    on public.users (uid);

create index users_username_index
    on public.users (username);

