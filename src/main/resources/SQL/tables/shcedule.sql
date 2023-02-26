create table public."Schedule" (
                                   schedule_id integer primary key not null,
                                   week_number integer not null,
                                   request_id integer not null,
                                   foreign key (request_id) references public."Shifts_requests" ("Request_id")
                                       match simple on update no action on delete no action
);

