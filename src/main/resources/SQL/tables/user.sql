create table "User"
(
    username varchar not null,
    uid      integer generated always as identity
        constraint "User_pk"
            primary key,
    password varchar not null,
    pid      integer not null
        constraint "User_Profile_pid_fk"
            references "Profile",
    role_id  integer not null
        constraint "User_Roles_role_id_fk"
            references "Roles"
);
