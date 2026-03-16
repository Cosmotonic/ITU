-- How many forum posts mention Germany in their message? 
-- Answer: 2
-- SQL: 
SELECT count(*) FROM forum
WHERE message LIKE '%Germany%'

-- Which users have not placed any bets? 
-- Answer: George
-- SQL: 
SELECT * FROM bet
WHERE bet_a = 0 AND bet_b = 0; 

-- Which users posted the most messages in the forum? 
-- Answer: Bob 4, Fiona 4, Julia 2, Alice 1, Ethan 1
-- SQL: 
SELECT username, COUNT(username) as most_active_poster
FROM forum
group by userName
ORDER BY most_active_poster DESC

-- Which countries won 3:1? (Treat both 3:1 and 1:3 outcomes as wins for the country that scored 3 goals.) 
-- Answer: Spain and France
-- SQL: 
SELECT
    CASE WHEN goals_a = 3 AND goals_b = 1 THEN ca.cname
         WHEN goals_a = 1 AND goals_b = 3 THEN cb.cname
    END as Winner_ID
FROM game g 
JOIN country ca ON ca.cid = g.country_a -- 
JOIN country cb ON cb.cid = g.country_b -- 
WHERE (goals_a = 3 AND goals_b = 1) OR (goals_a = 1 AND goals_b = 3);


-- Which countries won most often? 
-- Answer: France 4, Spain 3, England 2, Netherlands 2, Portugal 1, Italy 1, Germany 1.
-- SQL: 
SELECT
  CASE WHEN goals_a > goals_b then ca.cname
       WHEN goals_a < goals_b then cb.cname
    END as Winner_ID, count(*) as wins
FROM game g 
JOIN country ca ON ca.cid = g.country_a -- 
JOIN country cb ON cb.cid = g.country_b -- 
WHERE (goals_a > goals_b ) OR (goals_a < goals_b)
GROUP BY Winner_ID
ORDER BY wins DESC; 


-- Write a SQL query that produces a high score list of users and their total points.
-- Answer: Hannah 6, Bob 6, Charlie 3, Ivan 3, Diana 3, Alice 3, George 0, Fiona 0, Ethan 0, Julia 0.
-- SQL: 
SELECT u.username,
  SUM(CASE WHEN b.bet_a = g.goals_a AND b.bet_b = g.goals_b THEN 3 ELSE 0 END) as total_points
FROM user u
LEFT JOIN bet b ON b.username = u.username
LEFT JOIN game g ON g.gid = b.gid
GROUP BY u.username
ORDER BY total_points DESC


-- GPT GIV MIG EN UDFORDRING: 
-- GPT GIV MIG EN UDFORDRING: 
-- GPT GIV MIG EN UDFORDRING: 
-- Skriv en query der finder alle brugere der har skrevet mere end ét forum indlæg.
SELECT f.username, COUNT (f.username) as total_messages 
FROM forum f
GROUP BY f.username
HAVING total_messages > 1
ORDER BY total_messages DESC


-- Find alle brugere der har bettet på mindst 3 kampe.
SELECT username, COUNT(username) as bet_counter
FROM bet
GROUP BY username
HAVING ( bet_counter > 2 )
ORDER BY bet_counter desc


-- Find alle kampe hvor ingen af holdene scorede.
-- Tilføj JOIN og landenes navne i SELECT.
SELECT ca.cname as team_a, cb.cname as team_b, gid 
FROM game g
LEFT JOIN country ca ON ca.cid = g.country_a
LEFT JOIN country cb ON cb.cid = g.country_b
WHERE (goals_b = 0 AND goals_a = 0)


-- Tænk på det som en sætning: "Giv mig landets navn OG antallet af gange det vinder, fra game tabellen, grupperet per land, sorteret efter antal."
-- SELECT = "giv mig"
-- COUNT = "antallet af"
-- FROM = "fra"
-- GROUP BY = "grupperet per"
-- HAVING  = condition
-- ORDER BY = "sorteret efter"


-- Take one relation from the sports betting database and modify it so that the relation is in first 
normal form (1NF) but not in second normal form (2NF).  
Relation: bet(username, gid, bet_a, bet_b, photo)
Primary key: (username, gid)

Funktionelle afhængigheder:
username, gid → bet_a
username, gid → bet_b
username → photo

-- Now the resulting relation must be in second 
normal form (2NF) but not in third normal form (3NF
relation: game(gid, country_a, country_b, kickofftime, goals_a, goals_b, cname) 
Primary key: (gid) 
Funktionelle afhængigheder: 
gid -> country_a -> cname



