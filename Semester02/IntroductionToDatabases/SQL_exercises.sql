-- ============================================================
-- GAMLE NOTER
-- ============================================================

SELECT whom_id, COUNT(*) as follower_count 
FROM follower 
GROUP BY whom_id 
ORDER BY follower_count DESC 
LIMIT 1;

-- find the user who follows the most 
SELECT who_id, COUNT(*) who_id_count
FROM follower 
GROUP BY who_id
ORDER BY who_id_count DESC
LIMIT 5;

.table 
.schema user 
SELECT username FROM user WHERE user_id = 9; -- Octavio Wagganer
SELECT text FROM message WHERE author_id = 9; -- alle Octavio wagganers beskeder

-- combine tables so the follower table shows the name of the user instead. 
SELECT user.username, COUNT(*) as who_id_count
FROM follower
JOIN user ON follower.who_id = user.user_id
GROUP BY follower.who_id
ORDER BY who_id_count DESC
LIMIT 5;


-- ============================================================
-- NYE NOTER – 7.3 INSERT / DELETE / UPDATE  +  7.4 VIEWS
-- ============================================================

-- INSERT: angiv altid kolonnenavne eksplicit
-- udeladte kolonner får NULL eller DEFAULT
INSERT INTO product(productname) VALUES ('Sprite');

-- INSERT med flere kolonner
INSERT INTO socialWorker.social_worker(id, name, contact, photo) 
VALUES (15, 'kasper', 'amager strandvej 158d', 'photo/dir');

-- INSERT med foreign key hentet fra subquery
INSERT INTO assign(social_worker_id)
SELECT social_worker_id FROM social_workers WHERE name = 'Hansen';

-- INSERT med VALUES uden kolonnenavne kræver præcis antal værdier som tabellen har kolonner
-- INSERT INTO product VALUES ('Sprite')  →  fejl hvis tabellen har flere kolonner


-- DELETE: fjern rækker med WHERE – uden WHERE slettes hele tabellen
DELETE FROM product WHERE productname = 'Sprite';

-- DELETE med subquery
DELETE FROM supplies WHERE prodnr IN (
    SELECT prodnr FROM product WHERE productname LIKE '%CHARD%'
);

-- OBS: ON DELETE CASCADE propagerer sletningen til relaterede tabeller


-- UPDATE: opdater eksisterende rækker
UPDATE product SET price = 35 WHERE price = 25;

-- UPDATE med CASE (if-then-else i SQL)
UPDATE supplier SET supcategory =
    CASE WHEN supstatus >= 70 AND supstatus <= 90 THEN 'GOLD'
         WHEN supstatus > 90                      THEN 'PLATINUM'
         ELSE                                          'SILVER'
    END;

-- OBS: ON UPDATE CASCADE propagerer ændringen til relaterede tabeller


-- ============================================================
-- NYE NOTER – 7.4 VIEWS
-- ============================================================

-- VIEW: virtuel tabel – ingen data gemmes fysisk
-- DBMS omskriver automatisk queries mod viewet til basetabellen (query modification)
-- Bruges til: ease of use, sikkerhed, logisk dataindependens

CREATE VIEW productAboveFifty AS
SELECT productname, price FROM product WHERE price < 50;

-- brug viewet som en normal tabel
SELECT * FROM productAboveFifty;

-- vil du have en fysisk kopi (ikke pensum):
CREATE TABLE ny_tabel AS 
SELECT productname, price FROM product WHERE price < 50;


-- OPDATERBARE VIEWS
-- Et view kan opdateres hvis ændringen entydigt kan mappes til basetabellen
-- Ikke opdaterbart: aggregatfunktioner, GROUP BY, DISTINCT, flere tabeller i FROM, UNION/INTERSECT/EXCEPT

-- WITH CHECK OPTION: afviser opdateringer der bryder viewets WHERE-betingelse
CREATE VIEW productAboveFifty AS
SELECT productname, price FROM product WHERE price < 50
WITH CHECK OPTION;

-- Afvises – 80 opfylder ikke price < 50
UPDATE productAboveFifty SET price = 80 WHERE productname = 'Cola';

-- Accepteres – 30 opfylder price < 50
UPDATE productAboveFifty SET price = 30 WHERE productname = 'Cola';

update user 
set email = concat(email, '@itu.dk')
where email not like '%itu.dk'; 

-- QUICK REFERENCE
SELECT * FROM product LIMIT 10;
SELECT * FROM socialWorker.social_worker LIMIT 10;

-- Notes 
select * 
from product where price = (select max(price) from product) 
order by productname; 

-- DDL - Data definition language 

-- alter 
alter table <table_name> user add column password_hash string; 
alter table user add column salt double; 

update user set password_hash = sha256(concat('fl7mk2jZ9c', salt)) where username = 'Donna'; 

-- VIEWS - good for access control, permission system. 

create view revenuePerDay as
select purchasetime::date as day, 
sum(price) as revenue
from purchase join product
on purchase.productname = product.productname
group by day;

select * from revenuePerDay
where revenue >= 100
order by revenue desc;

select * from (
select purchasetime::date as day, sum(price) as revenue
from purchase join product
on purchase.productname = product.productname
group by day
)
where revenue >= 100
order by revenue desc;

-- WITH clause - only exists for a single query. View can be used for multiple queries later. 
with revenuePerDay as (
select purchasetime::date as day, sum(price) as revenue
from purchase join product
on purchase.productname = product.productname
group by day
)
select * from revenuePerDay
where revenue >= 100
order by revenue desc;

-- recursive work on WITH
with recursive counter(num) as (
select 1
union all
select num + 1 from counter
)
select num from counter
limit 10;


-- Window functions 
select username, sum(price) 
from sales
group by username
order by username;

-- window functions produce one row per row 
select username, sum(price) over(partition by username order by purchasetime) 
from sales
order by purchasetime;

-- OVER clause defines a window of rows for the function to operate on
-- PARTITION BY divides the result into partitions 
-- ORDER BY with the OVER defines logical orde r
-- FRAMING The ROWS or RANGE clause specifies the frame of rows within the partition that the function considers
-- Aggregate: Functions like SUM(), AVG(), COUNT(), MIN(), MAX() can be used as window functions to perform calculations over a window of rows.
-- Ranking Functions: Functions like ROW_NUMBER(), RANK(), DENSE_RANK() assign a unique rank or position to each row within a partition. 
-- Analytic Functions: Functions like LAG(), LEAD(), FIRST_VALUE(), and LAST_VALUE() provide access to other rows in the window relative to the current row.

--cumulative sum of sales over time -- add previous to current cummerlated sales over time. 
select*, sum(price) over(order by purchasetime) as cum_sales
from sales
order by purchasetime;

--cumulative sum of sales per user over time
select*, 
sum(price) over(partition by username order by purchasetime)   
as cum_sales
from sales
order by purchasetime;

--cumulative sum of sales per user over time, sorted by username
select*, 
sum(price) over(partition b yusername order by purchasetime)   
as cum_sales
from sales
order by username, purchasetime;


-- Compute difference of sales to previous day using -Lag(revenue)
with revenue_per_day as (-- same as the very last query of lecture 4
select purchasetime::date as day, sum(price) as revenue
from purchase join product
on purchase.productname = product.productname
group by day)
select day, revenue, 
revenue-lag(revenue) over(order by day) as diff_to_prev_day
from revenue_per_day;

-- Row Framing
create tableresults(points int);
insert into results values(10), (8), (12), (9), (9), (9), (5), (7);--compute the sum of each point and the points on either side of it
select points, 
sum(points) over(rows between 1 preceding and 1 following) as we 
from results;

-- Always use ORDER BY with window functions. '

INSERT INTO product (productname, description, price)
VALUES 
    ('Espresso', 'Strong black coffee', 35),
    ('Latte', 'Espresso with steamed milk', 45),
    ('Cappuccino', 'Espresso with foam', 45);