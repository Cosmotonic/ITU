-- init data data
create table user(
  username string primary key,
  photo string unique,
  password string
);

create table forum(
  post_id int primary key,
  post_time datetime not null,
  message string not null,
  username string not null references user(username)
);

create table country(
  cid int primary key,
  cname string not NULL 
);

create table game(
  gid int primary key,
  country_a int not null references country(cid),
  country_b int not null references country(cid),
  kickoff_time datetime not null,
  goals_a int,
  goals_b int
);

create table bet(
  username string not null references user(username),
  gid int not null references game(gid),
  bet_a int,
  bet_b int,
  primary key(username, gid)
);

insert into user values
  ('Alice', 'alice.jpg', 'pass123'),
  ('Bob', 'bob.png', 'qwerty'),
  ('Charlie', 'charlie.png', 'hunter2'),
  ('Diana', 'diana.jpg', 'abc123'),
  ('Ethan', 'ethan.png', 'letmein'),
  ('Fiona', 'fiona.png', 'secure1'),
  ('George', 'george.jpg', 'football'),
  ('Hannah', 'hannah.png', 'pw12345'),
  ('Ivan', 'ivan.jpg', 'europe'),
  ('Julia', 'julia.png', 'soccer2025');

insert into forum values
  (1, '2025-08-28 10:15:00', 'Spain looks strong this year.', 'Ethan'),
  (2, '2025-08-28 11:00:00', 'Go Netherlands!', 'Alice'),
  (3, '2025-08-28 12:30:00', 'Germany will crush it.', 'Bob'),
  (4, '2025-08-29 13:45:00', 'Italy always surprises.', 'Julia'),
  (5, '2025-08-29 14:20:00', 'France has amazing depth.', 'Fiona'),
  (6, '2025-08-30 09:00:00', 'Portugal might upset.', 'Fiona'),
  (7, '2025-08-30 09:45:00', 'England could finally win.', 'Bob'),
  (8, '2025-08-30 10:10:00', 'Belgium underrated as always.', 'Fiona'),
  (9, '2025-08-30 11:00:00', 'Excited for the matches!', 'Bob'),
  (10, '2025-08-30 11:30:00', 'Defense wins championships.', 'Julia'),
  (11, '2025-08-31 10:00:00', 'Germany vs Italy is classic.', 'Bob'),
  (12, '2025-08-31 11:00:00', 'Hope to see fair play.', 'Fiona');

insert into country values
  (1, 'France'),
  (2, 'Germany'),
  (3, 'Italy'),
  (4, 'Spain'),
  (5, 'Portugal'),
  (6, 'Netherlands'),
  (7, 'England'),
  (8, 'Denmark'),
  (9, 'Norway'),
  (10, 'Sweden');

insert into game values
  (1, 1, 2, '2025-08-01 18:00:00', 2, 1),
  (2, 3, 4, '2025-08-01 21:00:00', 0, 0),
  (3, 5, 6, '2025-08-02 18:00:00', 1, 2),
  (4, 7, 8, '2025-08-02 21:00:00', 2, 2),
  (5, 1, 3, '2025-08-03 18:00:00', 3, 1),
  (6, 2, 4, '2025-08-03 21:00:00', 1, 3),
  (7, 5, 7, '2025-08-04 18:00:00', 0, 1),
  (8, 6, 8, '2025-08-04 21:00:00', 2, 0),
  (9, 1, 4, '2025-09-05 18:00:00', 2, 2),
  (10, 2, 5, '2025-09-05 21:00:00', 0, 1),
  (11, 3, 6, '2025-09-06 18:00:00', 1, 1),
  (12, 4, 7, '2025-09-06 21:00:00', 3, 0),
  (13, 8, 1, '2025-09-07 18:00:00', 1, 2),
  (14, 2, 6, '2025-09-07 21:00:00', 2, 0),
  (15, 3, 7, '2025-09-08 18:00:00', 0, 2),
  (16, 4, 8, '2025-09-08 21:00:00', 1, 1),
  (17, 5, 1, '2025-09-09 18:00:00', 2, 3),
  (18, 6, 2, '2025-09-09 21:00:00', 0, 0),
  (19, 7, 3, '2025-09-10 18:00:00', 1, 2),
  (20, 8, 4, '2025-09-10 21:00:00', 0, 1),
  (21, 1, 5, '2025-10-15 18:00:00', NULL, NULL),
  (22, 4, 7, '2025-10-15 21:00:00', NULL, NULL),
  (23, 5, 9, '2025-10-16 18:00:00', NULL, NULL),
  (24, 4, 9, '2025-10-16 21:00:00', NULL, NULL),
  (25, 9, 5, '2025-10-17 18:00:00', NULL, NULL);

insert into bet values
  ('Alice', 1, 2, 1),
  ('Alice', 2, 1, 0),
  ('Alice', 3, 1, 1),
  ('Alice', 5, 2, 1),
  ('Alice', 10, 1, 0),
  ('Bob', 4, 2, 1),
  ('Bob', 6, 1, 2),
  ('Bob', 7, 0, 1),
  ('Bob', 8, 2, 0),
  ('Charlie', 9, 1, 0),
  ('Charlie', 11, 1, 1),
  ('Charlie', 12, 2, 1),
  ('Diana', 13, 0, 1),
  ('Diana', 14, 2, 0),
  ('Diana', 15, 1, 1),
  ('Ethan', 16, 1, 2),
  ('Ethan', 17, 2, 2),
  ('Fiona', 18, 1, 0),
  ('Fiona', 19, 1, 1),
  ('George', 20, 0, 0),
  ('George', 1, 1, 1),
  ('Hannah', 5, 3, 1),
  ('Hannah', 6, 0, 1),
  ('Hannah', 12, 3, 0),
  ('Hannah', 14, 2, 1),
  ('Ivan', 8, 2, 0),
  ('Ivan', 9, 2, 1),
  ('Ivan', 13, 1, 3);