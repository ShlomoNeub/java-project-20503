create table "Available_shifts"
(
    week_number    integer not null,
    day_number     integer not null,
    start_hour     varchar not null,
    end_hour       varchar not null,
    empolyee_count integer not null,
    manager_count  integer not null,
    shifts_id      integer generated always as identity
        constraint "Available_shifts_pk"
            primary key
);

