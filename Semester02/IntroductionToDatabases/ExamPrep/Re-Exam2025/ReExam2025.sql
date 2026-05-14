

-- ER Modeling, DDL, and Normal Forms (20 points) 
-- Question 1 (7 points) 
-- 
-- A water park consists of one or more swimming pools, 
-- each with various water depths and temperatures.
-- 
-- Water parks are identified by their names and often have a 
-- motto, such as 'The best place to swim in the 1 city'. 
-- 
-- Some, but not all, water parks feature slides. Each slide has a number, but these 
-- numbers are only unique within each water park. slides vary in color, difficulty, and length. 

-- Question 2 (7 points) 
-- Write SQL DDL statements to create tables for each entity identified in the ER model of the previous 
-- question. Define data types for each attribute. Include primary keys and foreign keys to enforce 
-- relationships between tables. 

create table waterPark( 
    name string primary key, 
    motto string 
); 

create table swimmingPool( 
    ID int, 
    name string, 
    waterDepth float, 
    temperature float, 
    primary key (ID, name), 
    foreign key (name) references waterPark(name) 
); 

create table slide( 
    number int, 
    name string, 
    color string, 
    difficulty int, 
    length float, 
    primary key (number, name), 
    foreign key (name) references waterPark(name) 
);


-- Question 3 (3 points) 
-- Given a relation WaterparkRace(PersonID, SlideID, SlideColor, PersonalBestTime) and the following 
-- functional dependencies: 

-- • PersonID, SlideID → SlideColor, PersonalBestTime 
-- • SlideID → SlideColor 

-- What normal form is the relation in? 
-- • Not in 1NF 
-- • 1NF 
-- • 2NF 
-- • 3NF / BCNF

-- ANSWER: 1NF 
-- Normal form (NF)
-- 1NF: Ingen celle må indeholde flere ting. (Ja, for alle celler indeholder kun en form for info)
-- 2NF: Ingen attribut må afhænge af kun en del af nøglen. (pk : PersonID, slideID men slide color er kun afhængig af slideID.)
-- 3NF: Ingen attribut må afhænge af en anden ikke-nøgle attribut.


-- Question 4 (3 points) 
-- If the relation in the previous question is not in 3NF / BCNF, normalize the relation to the highest possible 
-- normal form (3NF / BCNF) and select the resulting relations. If the relation is in 3NF / BCNF, select the 
-- answer “The relation is already in 3NF / BCNF”. 

-- ANSWER: 
    -- WaterparkRace(PersonID, SlideID, PersonalBestTime)   -- remove color as it is depnding on slide id which is party PK. 
    -- SlideColor(SlideID, SlideColor)                      -- Split out color so attributes are only depending on PK


-- Question 5 & 6 (5 points) 
-- What is the minimum length of all blue boats? 

-- ANSWER: 45 
SELECT MIN(length_ft) FROM boats WHERE color = 'Blue';

-- or more sloppy: 
select * from boats
where color == 'Blue'
order by length_ft asc 

-- Question 7 & 8 (5 points) 
-- How many boat names have more than one word?  

-- ANSWER: 10 
SELECT COUNT(boatName) FROM boats WHERE boatName LIKE '% %';

-- Question 9 & 10 (5 points) 
-- What is the average length of all boats in the 5th race? 

-- ANSWER: 44.5  

SELECT AVG(length_ft)
FROM boats b
JOIN raceResults r ON b.boatName = r.boatName
WHERE r.raceId = 5;


-- Question 11 & 12 (5 points) 
-- What is the minimum number of boats that competed in any race?

-- ANSWER: 5 
SELECT MIN(antal_baade) 
FROM (
  SELECT COUNT(DISTINCT boatName) AS antal_baade 
  FROM raceResults 
  GROUP BY raceId
) AS sub;

-- alternative answer. Less specific.  
SELECT count(distinct boatName) differentRaces
from raceResults
group by raceId
order by differentRaces asc 


-- Question 13 & 14 (5 points) 
-- Which boats came into second place most often? 

-- ANSWER: Nauti Buoy 

-- My answer, should be more precise instead of a total list. 
with secondPlaces as (
  select boatName, place from raceResults where place = 2
)
select boatName, count(boatName) boatCount from secondPlaces
group by boatName 
order by boatCount desc

-- EXAM correction
with second_places as ( 
select boatName, count(*) as cnt 
from raceResults 
where place = 2 
group by boatName 

) 

select boatName 
from second_places 
where cnt = (select max(cnt) from second_places);

-- Question 15 & 16 (5 points) 
-- Which boats never competed in any race?
-- Viking, Nordic Star

select distinct b.boatName  from boats b
left join raceResults r on r.boatName = b.boatName
where raceId is null


-- Question 17 (5 points) 
-- Write a SQL query that outputs the name of each boat, the number of races each boat has participated in, 
-- and the average finishing place of each boat across those races.

-- boatName, numbersOfRaces, avgPlace
with nameAndResults as (
  select boatName, count(raceId) as numbersOfRaces from raceResults
  group by boatName
), avgPlace as (
  select avg(place) avgPlace, boatName from raceResults
  group by boatName
)
select n.boatName, n.numbersOfRaces, round(av.avgPlace, 1) from nameAndResults n
join avgPlace av on av.boatName = n.boatName
order by n.numbersOfRaces desc


-- EXAM Correction
select b.boatName,  
    count(r.raceId) as numberOfRaces,  
    round(avg(coalesce(place, 0)), 1) as avgPlace  -- Hvis place er NULL, så brug 0 i stedet
    from boats b 
    left join raceResults r on b.boatName = r.boatName 
    group by b.boatName 
order by numberOfRaces desc, avgPlace;

-- Question 18 & 19 (5 points) 
-- How many times did the boat "Wave Rider" exceed its recent performance? That is, how often was this 
-- boat’s maxSpeed in a race greater than the average maxSpeed over that race and the two previous ones, 
-- based on raceId order? Count the number of such races.
-- tag: addition add add previous numbers 

-- ANSWER: 7 

  with avgSpeedOrdered as(
    select boatName, maxSpeed, avg(maxSpeed) OVER ( 
        order by raceId 
        rows between 2 preceding and current row ) as avg_speed 
        from raceResults
    where boatName = 'Wave Rider'
  )
  select count(*) from avgSpeedOrdered 
  where maxSpeed > avg_speed
  

-- Question 20 (8 points) 
-- Given the users Dirk, Erich, Fiona, and Gaia, along with the tables test, employees, and production, write 
-- the SQL commands following the Snowflake syntax as shown in Lecture 7 to create the role hierarchy 
-- depicted in the following diagram. The commands must include the CREATE ROLE statements and GRANT 
-- statements to establish the role hierarchy, assign roles to users, and assign the five privileges shown.

create role Manager; 
create role Engineer; 
create role Intern; 

grant role Manager to user Erich; 
grant role Manager to user Fiona; 
grant role Engineer to user Gaia; 
grant role Intern to user Dirk; 

grant role Engineer to role Manager; 
grant role Intern to role Manager; 

grant update on table employees to role Manager;
grant select, insert on table test to role Intern
grant select, insert on table production to role Engineer

-- Question 21 (2 points) 
-- Which of the following statements about the RBAC setup in the previous question are correct? 
-- Answer: 
--  Interns never get to read production data. 
--  Adding a role-to-role grant between the engineer role and intern role is enough to allow Gaia to 
-- oversee what Dirk is writing to the test table.


-- Question 22 (2 points) 

select * from icecream 
where price = 40; 

-- Select all that apply: 
-- Min/max summary of table IceCream 
-- Min/max summary of file prod1.data 
-- Min/max summary of file prod2.data 
-- Min/max summary of row group 1 
-- Min/max summary of row group 2 
-- Min/max summary of row group 3 

-- Question 23 (2 points) 

select * from icecream 
where name = 'Chocolate'; 
Select all that apply: 

-- Min/max summary of table IceCream 
-- Min/max summary of file prod1.data 
-- Min/max summary of file prod2.data 
-- Min/max summary of row group 1 
-- Min/max summary of row group 2 
-- Min/max summary of row group 3 
-- Min/max summary of row group 4 
-- Min/max summary of row group 5 

-- NOTE: vi skal læse SUMMARYs for at UNDGÅ AT LÆSE ROWS. Det er røvhamrende counterintuitivt men sådan er det. 


-- Question 24 (2 points) 
select * from icecream 
where price > 100 
or price < 30;

-- Min/max summary of table IceCream 
-- NOTE: IceCream Summary siger 30-90 så allerde der ved koden der ikke findes noget der er under 30 eller over 100 i andre tabeller. 


-- Question 25 (2 points) 
select min(price) from icecream; 

-- Min/max summary of table IceCream 
-- we dont need to look further to get the lowest price. we know the iceream table has the full summary


-- Question 26 (2 points) 
-- Which of the following statements about indexes are correct? 
-- Clustered indexes mean that the order of the index follows the order of the data. 
-- Indexes speed up the performance of “point lookup” queries. 



-- OLAP and Big Data Management (10 points) 

-- OLTP: Online Transactional Processing (Drift) - Intuition: Det er her, dagligdagens transaktioner sker lynhurtigt.
-- OLAP: Online Analytical Processing (Analyse) -  Intuition: Det er her, man ser på de store linjer og historiske data.

-- Question 27 (1 point) 
-- Categorize the following query as OLTP or OLAP: 

select avg(length_ft) 
from boats; 

-- ANSWER: OLAP 

-- Question 28 (1 point) 
-- Categorize the following query as OLTP or OLAP: 
select raceId, count(boatName) as cb, max(maxSpeed) as ms 
from raceResults 
where raceId > 1 
group by raceId 
having ms > 9.0 and cb > 5; 

-- ANSWER: OLAP

-- Question 29 (3 points) 
-- List three characteristics of an OLTP query. 

-- ANSWER: 
-- 1. Processes small amounts of data 
-- 2. Focus on real-time data processing 
-- 3. High volume of short transactions 
-- 4. Typically involves frequent insert, update, and delete operations 

-- OLTP: 
-- Purpose: Handles many small, fast read and write operations (e.g., buying an ice cream or withdrawing cash).
-- Focus: Data integrity and speed of updates/inserts.
-- Example: A supermarket point-of-sale system or an airline booking site.

-- OLAP:
-- Purpose: Runs complex queries on massive amounts of data to find patterns or insights.
-- Focus: Read performance and the ability to aggregate data (e.g., sum, average).
-- Example: "What were the total ice cream sales in North Jutland this July compared to last year?"


-- Question 30 (5 points) 
-- Select the correct answers: 
-- DuckDB is an OLAP database system that runs on laptops, but also in satellites and cars. 
-- Apache Parquet is an open file format typically used in data lake setups in the cloud. 
-- Document databases are well-suited for integration with object-oriented languages such as Java. 
-- Unstructured data is generally not suited to be stored in SQL database systems.


-- Question 31 (5 points) 
-- Assume multiple users are ordering from a web shop. When an online order is placed, the system creates 
-- a shipping record for the order, for example: 
insert into shipping (orderId, product, quantity, status)  
values (1221, 'Coffee beans', 10, 'Pending');

-- ANSWER: 
-- Problems that can arise: 
--      - Dirty Reads: The system sees uncommitted and potentially incorrect data. 
--      - Lost Updates: One user's changes are overwritten by another's. 
--      - Inconsistent Data: Partial updates leave the database in an inconsistent state. 
--      - Non-Repeatable Reads: Different data is read on subsequent accesses (e.g., retries) due to intervening changes. 
--      - Data Integrity Issues: Discrepancies between order records and inventory. 
-- By using TRANSACTIONS, you ensure that queries are executed as a single atomic operation. Concurrently 
-- running transactions never see the intermediate state of another running transaction, meaning 
-- transactions are isolated from each other and cannot read inconsistent data. 


/*
String query = "SELECT * FROM orders"; 
Statement stmt = connection.createStatement(); 
ResultSet rs = stmt.executeQuery(query); -- // verything from orders. 
double totalRevenue = 0.0; 
while (rs.next()) { 
    Date orderDate = rs.getDate("order_date"); 
    if (orderDate.toString().equals("2025-06-20")) { 
        totalRevenue += rs.getDouble("total_price"); 
    } 
} 
*/

-- ANSWER: 
/*
PreparedStatement ps = conn.prepareStatement(
    "SELECT SUM(total_price) AS totalRevenue " +
    "FROM orders " +
    "WHERE order_date = ?"
);
ps.setDate(1, java.sql.Date.valueOf("2025-06-20"));
ResultSet rs = ps.executeQuery();
double totalRevenue = 0.0;
if (rs.next()) {
    totalRevenue = rs.getDouble("totalRevenue");
}
*/


-- Question 33 (2 points) 
-- Explain why your rewritten version of the Java program in the previous question is more efficient. 
-- ANSWER: 
--     Less data on network. Instead of SELECT we go directly to SUM. 
--     JAVA filters, the SQL uses WHERE + SUM 
--     We no longer loop in java. 