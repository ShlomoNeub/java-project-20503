
create table public.user_shift
(
    id          integer default nextval('user_shifts_id_seq'::regclass) not null
        constraint user_shifts_pk
            primary key,
    "profileId" integer                                                 not null
        constraint user_shifts_profiles_pid_fk
            references public.profile,
    timestamp   integer                                                 not null,
    "shiftId"   integer
        constraint user_shifts_shifts_id_fk
            references public.shift
);

alter table public.user_shift
    owner to postgres;