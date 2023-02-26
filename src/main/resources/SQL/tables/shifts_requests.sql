create table "Shifts_requests"
(
    "Request_id" integer generated always as identity
        constraint "Shifts_requests_pk"
            primary key,
    shift_id     integer   not null
        constraint "Shifts_requests_Available_shifts_shifts_id_fk"
            references "Available_shifts",
    uid          integer   not null
        constraint "Shifts_requests_User_uid_fk"
            references "User",
    time_stamp   timestamp not null
);

