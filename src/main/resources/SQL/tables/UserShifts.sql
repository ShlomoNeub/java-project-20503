create table public.user_shift
(
    id          integer
        constraint user_shift_pk
            primary key,
    "profileId" integer
        constraint user_shift_profile_pid_fk
            references public.profile,
    timestamp   integer,
    "shiftId"   integer
        constraint user_shift_shift_id_fk
            references public.shift
);

create index user_shift_timestamp_index
    on public.user_shift (timestamp);