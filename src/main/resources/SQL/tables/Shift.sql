create table public.shift
(
    id          integer not null
        constraint "Shifts_pk"
            primary key,
    day_number  integer not null,
    week_number integer not null,
    start_hour  integer not null,
    end_our     integer not null,
    emp_count   integer not null
);

alter table public.shift
    owner to postgres;


