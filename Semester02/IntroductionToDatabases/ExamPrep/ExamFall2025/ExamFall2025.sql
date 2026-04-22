CREATE TABLE runner (
    runner_id INTEGER PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL
);
CREATE TABLE race (
    race_id INTEGER PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    length DECIMAL(6, 2) NOT NULL
);
CREATE TABLE competes (
    race_id int references race (race_id) NOT NULL,
    runner_id int references runner (runner_id) NOT NULL,
    finished BOOLEAN NOT NULL,
    bip_number INTEGER NOT NULL,
    PRIMARY KEY (runner_id, race_id)
);
CREATE TABLE medal (
    race_id INT references race(race_id), 
    serial_number INTEGER NOT NULL,
    awardedRunnerId int references runner (runner_id),
    TYPE VARCHAR(10) CHECK (TYPE IN ('gold', 'silver', 'bronze')) NOT NULL, 
    PRIMARY KEY (serial_number, race_id)
);

-- duckdb does not have enums. 

-- QUESTION 5
Hvad er primærnøglen i denne relation?
Finisher(Runner, T-Shirt-Size, Race, Location(City, Country), FinalPosition)
med disse funktionelle afhængigheder:

Runner → T-Shirt-Size
Race → City, Country
Runner, Race → FinalPosition

SVAR: Fordi location indeholder flere ting er det ikke engang 1NF. 
** identificer altid primary key først og følg så reglerne:

// Normal form (NF)
1NF: Ingen celle må indeholde flere ting.
2NF: Ingen attribut må afhænge af kun en del af nøglen.
3NF: Ingen attribut må afhænge af en anden ikke-nøgle attribut.

-- QUESTION 6 
Vi ved at relationen ikke er i 1NF fordi Location er sammensat. Første skridt er at fladlægge den, så vi får:
Finisher(Runner, Race, T-Shirt-Size, City, Country, FinalPosition)
Nu er primærnøglen (Runner, Race). Hvad er problemerne nu?

Nu re race_id og runner_id PK 

SVAR: 
RaceInfo(Race, City, Country) -- city og country er kun afhg af race. 
RunnerInfo(Runner, T-Shirt-Size) -- tshirt er kun afhg af runner. 
RaceFinal(Runner, Race, FinalPosition) -- final position er afhæg af race/runner

-- GAME NIGHT 
-- https://learnit.itu.dk/pluginfile.php/445160/mod_resource/content/1/game_nights.sql

-- Question 7 & 8 (5 points) 
-- What is the average age of players over 30?

SELECT AVG(age) from players
where age > 30

-- Question 9 & 10 (5 points) 
-- How many board games have a rating above 7.5 and contain the letter "a" in their title or description? 

-- solution 1. 
SELECT title, description, rating from boardgames
WHERE (rating > 7.5 AND LOWER(description) LIKE '%a%') 
OR (LOWER(title) LIKE '%a%')

-- solution 2. 
SELECT title, description, rating  from boardgames
WHERE REGEXP_MATCHES(description, '(?i)a') -- (?i) used to avoid case-sensitivity
OR REGEXP_MATCHES(title, '(?i)a') 


-- Question 11 & 12 (5 points) 
-- What is the average rating of the (unique) games played during the fourth game night (night_number = 4)? 

-- Solution 1.
SELECT AVG(rating) 
FROM (
    SELECT DISTINCT title, rating 
    FROM boardgames 
    JOIN gamenights ON title = game_title 
    WHERE night_number = 4
);

-- Solution 2. 
WITH uniqueGamesOnDay4 AS (
    SELECT DISTINCT game_title FROM gamenights
    WHERE night_number = 4
)
SELECT AVG(boardgames.rating) FROM uniqueGamesOnDay4
JOIN boardgames ON boardgames.title = uniqueGamesOnDay4.game_title



-- Question 13 & 14 (5 points) 
-- What is the highest number of different games played in a single game night? 
select count(distinct game_title) as GamesPerNight from gamenights
GROUP BY night_number
order by gamesPerNight desc; 


-- Question 15 & 16 (5 points) -- JOIN wont work as it only take matches. LEFT JOIN KEEPS left columns without a match. 
-- Which player(s) (by first name) have never participated in any game night?
SELECT p.first_name
FROM players p
LEFT JOIN gamenights g ON p.battle_name = g.battle_name
WHERE g.battle_name IS NULL


-- Question 17 & 18 (5 points) 
-- Which player(s) (by first name) finished in last place most frequently in games played with the maximum 
-- number of players allowed? Exclude games designed for one player only. 

-- SOlution 1. (my own)
with gamesForFriends as (
  select g.game_title, g.battle_name, g.player_position 
  from gamenights g
  join boardgames b ON g.game_title = b.title
  where g.player_position = b.max_players and b.max_players > 1)
select p.first_name, count(gff.battle_name) as lostGames from gamesForFriends gff
join players p on gff.battle_name = p.battle_name
group by p.first_name 
order by lostgames desc

-- Solution 2. (from exam solution)
with last_positions as ( 
    select battle_name, count(*) as cnt 
    from gamenights join boardgames 
    on game_title = title 
    where player_position = max_players 
    and max_players > 1 
    group by battle_name 
) 
select first_name from last_positions 
join players on last_positions.battle_name = players.battle_name 
where cnt = (select max(cnt) from last_positions); 


--Question 19 & 20 (5 points) 
--What is the longest gap in days between two consecutive game nights?  
-- Hint: Use DuckDB's date_diff() function. 

-- Finde alle de unikke datoer, hvor der er blevet spillet.
-- Sortere dem kronologisk.
-- Måle afstanden (i dage) fra én dato til den næste.
-- Identificere den maksimale afststand blandt alle disse målinger.

with unique_dates as (
  select distinct date from gamenights
),
prevous_add as (
  select date, lag(date) over (order by date) as previousDay 
  from unique_dates
), 
date_diff_cte as ( 
  select *, date_diff('day', previousDay, date) as difference 
  from prevous_add
)
select max(difference) as longest_gap
from date_diff_cte;


-- Question 21 & 22 (5 points) 
-- Which game(s) were played in a streak of three or more consecutive game nights? 


with unique_nights as (
    select date, 
           dense_rank() over (order by date) as night_id
    from gamenights
    group by date
), game_occurrences as (
    select distinct gn.game_title, un.night_id
    from gamenights gn
    join unique_nights un on gn.date = un.date
), streak_groups as (
    select game_title,
           night_id,
           night_id - row_number() over (partition by game_title order by night_id) as streak_id
    from game_occurrences
)

select game_title, count(*) as streak_length
from streak_groups
group by game_title, streak_id
having count(*) >= 3;



-- JSON and JSON Pointer (10 points) 
-- Solve the following tasks using the JSON document prize.json available on LearnIT.  


-- Question 23 & 24 (2 points) 
-- Create a JSON Pointer expression that accesses the year of the 500th prize in the JSON document prize.json. 

-- RESULT 
-- year	category	person_id	firstname	surname
-- 1935	peace	      500	      Carl	    von Ossietzky

WITH laureates_cleaned AS (
    SELECT 
        prize.year, 
        prize.category, 
        UNNEST(prize.laureates) AS laureate -- Vi pakker laureates ud, så vi tjekker alle personer
    FROM (
        SELECT UNNEST(prizes) AS prize 
        FROM read_json_auto('C:\ITU\Semester02\IntroductionToDatabases\ExamPrep\prize.json')
    )
)
SELECT 
    year, 
    category, 
    laureate.id AS person_id,
    laureate.firstname,
    laureate.surname
FROM laureates_cleaned
WHERE laureate.id = '500';


--Question 25 & 26 (2 points) 
--Create a JSON Pointer expression that accesses the last name (surname) of the only recipient of the 100th 
--prize in the JSON document prize.json. 

-- Correct Answer: 
-- '/prizes/99/laureates/0/surname'
-- Name: Obama 

-- correct because it only returns the value. 
SELECT prizes[100].laureates[1].surname
FROM read_json_auto('C:/ITU/Semester02/IntroductionToDatabases/ExamPrep/prize.json');

-- partly wrong because it returns a collumn. 
select read_json_auto->'/prizes/99/laureates/0/surname'
FROM read_json_auto('C:\ITU\Semester02\IntroductionToDatabases\ExamPrep\prize.json')


-- Question 27 & 28 (3 points) 
-- Create a SQL query that answers how many prizes exist in the document. That is, count the length of the "prizes" array. 

-- Answer: 682
SELECT count(*) 
FROM (
    SELECT UNNEST(prizes) FROM read_json_auto('C:/ITU/Semester02/IntroductionToDatabases/ExamPrep/prize.json')
);


-- Question 29 & 30 (3 points) 
-- Create a SQL query that answers how many prizes with the category "peace" exist in the document.

-- Answer: 125

SELECT count(*)
FROM (
    SELECT UNNEST(prizes) AS prize 
    FROM read_json_auto('C:\ITU\Semester02\IntroductionToDatabases\ExamPrep\prize.json')
)
WHERE prize.category = 'peace';

-- alternative solution. 
SELECT list_count(list_filter(prizes, x -> x.category = 'peace'))
FROM read_json_auto('C:\ITU\Semester02\IntroductionToDatabases\ExamPrep\prize.json');




--  Role-Based Access Control (10 points) 
/*
Given five users (Barbara, David, Elizabeth, Jennifer, Robert) and three tables (players, boardgames, and 
gamenights), along with the SQL commands below, which define an RBAC setup following the Snowflake 
syntax as shown in Lecture 7, select all statements that apply in the following questions. 
For completeness, note that all usage rights on databases, schemas, and warehouses exist for all roles 
and all users. 
*/ 
create role GameLead; 
create role GameMaster; 
create role Player; 

grant role GameMaster to user Barbara; 
grant role GameLead to user David; 
grant role Player to user Jennifer; 
grant role Player to user Robert; 

grant role GameLead to role GameMaster; 
grant select, delete, update on table gamenights to role GameMaster; 
grant select, insert on table gamenights to role GameLead; 
grant select on table players to role GameLead; 
grant select on table players to role Player; 

--Question 31 (2 points) 
-- Which users can insert new results in the gamenights table? 
-- Answer, Barbara & David. 

-- Question 32 (2 points) 
-- Which users can join the players and gamenights table to find who played some games already?
-- Answer, Barbara & David. 

-- Question 33 (2 points) 
-- Which users can count the number of players in the players table?
-- Barbara, David, Jennifer og Robert

-- Question 34 (2 points) 
-- Which users can delete results from the gamenights table in case they detect an error? 
-- Barbara

-- Role-Based Access Control (Rollebaseret adgangskontrol).
-- RBAC helps to limit the impact of prompt injection attacks. 
-- RBAC is often used in large corporations because of its great scalability. 


-- Question 36 (2 points) 
-- Which files does the database minimally need to access to answer the following SQL query given the 
-- min/max summaries in Minmax.metadata? 

select * 
from product 
where name = 'Espresso'; 

-- Answer: 
-- Product2.data        Because "espresso is between C-M"
-- Product3.data        Because "espresso is between C-J"
-- Minmax.metadata      Always needs to open meta data file. 

-- Question 37 (2 points) 
-- Which files does the database minimally need to access to answer the following SQL query given the 
-- min/max summaries in Minmax.metadata? 

select * 
from product 
where name < 'Jasmin' 
and price > 35; 

-- Answer: 
-- Product1.data        Because "Jasmin is between F-T"
-- Product3.data        Because "Jasmin is between C-J"
-- Minmax.metadata      Always needs to open meta data file. 

-- Question 38 (2 points) 
-- Which files does the database minimally need to access to answer the following SQL query given the 
-- min/max summaries in Minmax.metadata? 

select min(price) 
from product; 

-- Answer: 
-- Minmax.metadata      First find the lowest price range.  
-- Product3.data        ...Then look for the product with lowest price. 


-- Question 39 (2 points) 
-- Which files does the database minimally need to access to answer the following SQL query given the 
-- min/max summaries in Minmax.metadata? 
select avg(price) 
from product; 

-- Answer: 
-- All of them. We dont know if there are 100 products in one list. So we need to take all tables. 


-- Question 40 (2 points) 
-- Which of the following statements about joins and indexes are correct? 

-- ANSWER: 
-- The runtime performance of a hash join is linear. 
-- If data is already sorted, the database can utilize sort-merge joins efficiently. 




-- MSc Only: Big Data Management (5 points)

-- Only answer if you are an MSc student.
-- Select the correct answers:

-- The Reduce phase in MapReduce aggregates the intermediate results produced by the Map phase.
-- Graph databases are specifically designed for capturing and traversing relationships, such as in social networks.
-- Key-value stores are great for storing multi-media objects such as music and videos.
-- Relational databases have historically been catching up in terms of performance with more specialized data-management systems, sometimes even rendering them obsolete.
-- DuckDB is primarily designed for processing structured data and supports anti joins natively.
-- DuckDB is optimized for in-memory analytical processing.
