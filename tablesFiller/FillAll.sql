/*Clear all tables*/
TRUNCATE ONLY role,users,constraints,shifts_requests,json_web_token,schedule,profile,available_shifts
    RESTART IDENTITY;

/*ROLE FILLER*/
INSERT INTO role (role_level, role_name)
VALUES (0,'BASIC'),
       (1,'ADMIN');
/*PROFILE FILLER*/
INSERT INTO profile (id, email, first_name, last_name, phone_number)
VALUES (DEFAULT, 'dani@gmail.com', 'Dani', 'Cohen', '052-1234567'),
       (DEFAULT, 'Ron@gmail.com', 'Ron', 'Menashe', '052-3313245'),
       (DEFAULT, 'liat@gmail.com', 'liat', 'evron', '050-1283434'),
       (DEFAULT, 'michal.k@gmail.com', 'michal', 'katan', '052-6565987'),
       (DEFAULT, 'yuval123@gmail.com', 'yuval', 'maron', '052-5342676'),
       (DEFAULT, 'agam@gmail.com', 'agam', 'michaeli', '052-6345987'),
       (DEFAULT, 'osher@gmail.com', 'osher', 'azulai', '052-6565667'),
       (DEFAULT, 'almog12@gmail.com', 'almog', 'ginat', '052-6562227'),
       (DEFAULT, 'geva.ben@gmail.com', 'geva', 'ben baruch', '052-8723987'),
       (DEFAULT, 'danit22@gmail.com', 'danit', 'yoav', '050-4467223');
/*USERS FILLER*/
insert into users (id, password, pid, role_id, username)
values (DEFAULT, '12345678', 1, 2, 'Dani'),
       (DEFAULT, '12345678', 2, 1, 'Ron'),
       (DEFAULT, '12345678', 3, 1, 'liat'),
       (DEFAULT, '12345678', 4, 1, 'michal'),
       (DEFAULT, '12345678', 5, 1, 'yuval'),
       (DEFAULT, '12345678', 6, 1, 'agam'),
       (DEFAULT, '12345678', 7, 1, 'osher'),
       (DEFAULT, '12345678', 8, 1, 'almog'),
       (DEFAULT, '12345678', 9, 1, 'geva'),
       (DEFAULT, '12345678', 10, 1, 'danit');
/*AVAILABLE_SHIFTS FILLER*/
insert into available_shifts (id, day_number, duration, employee_count, start_hour, week_number, year)
values (DEFAULT, 99, 4, 4, 7, 14, 2023),
       (DEFAULT, 99, 4, 4, 11, 14, 2023),
       (DEFAULT, 100, 6, 5, 9, 15, 2023),
       (DEFAULT, 101, 3, 3, 7, 15, 2023),
       (DEFAULT, 101, 3, 3, 10, 15, 2023),
       (DEFAULT, 101, 3, 3, 13, 15, 2023),
       (DEFAULT, 102, 6, 5, 9, 15, 2023),
       (DEFAULT, 103, 6, 5, 9, 15, 2023),
       (DEFAULT, 104, 6, 5, 9, 15, 2023),
       (DEFAULT, 105, 6, 5, 9, 15, 2023);
/*CONSTRAINT_TYPE FILLER*/
insert into constraint_type (id, constraint_level, description)
values (DEFAULT, 1, 'PERSONAL REASON'),
       (DEFAULT, 2, 'VACATION'),
       (DEFAULT, 3, 'SICKNESS');
/*CONSTRAINTS FILLER*/
insert into constraints (id, data, end_date, is_permanent, start_date, type_id, user_id, week_number)
values(DEFAULT, 'I am on vacation at this days', '2023-04-30', false, '2023-04-25', 2, 8, 17);
/*SHIFTS_REQUESTS FILLER*/
INSERT INTO shifts_requests (id, shift_id, timestamp, uid)
VALUES (DEFAULT, 1, DEFAULT, 3),
       (DEFAULT, 4, DEFAULT, 3),
       (DEFAULT, 2, DEFAULT, 2),
       (DEFAULT, 6, DEFAULT, 4),
       (DEFAULT, 10, DEFAULT, 6),
       (DEFAULT, 8, DEFAULT, 7),
       (DEFAULT, 3, DEFAULT, 5),
       (DEFAULT, 9, DEFAULT, 8),
       (DEFAULT, 3, DEFAULT, 9),
       (DEFAULT, 3, DEFAULT, 10);
/*SCHEDULE FILLER*/
INSERT INTO schedule (id, request_id, week_number)
VALUES (DEFAULT, 1, 14),
       (DEFAULT, 2, 15),
       (DEFAULT, 3, 14),
       (DEFAULT, 4, 15),
       (DEFAULT, 5, 15),
       (DEFAULT, 6, 15),
       (DEFAULT, 7, 15),
       (DEFAULT, 8, 15),
       (DEFAULT, 9, 15),
       (DEFAULT, 10, 15);