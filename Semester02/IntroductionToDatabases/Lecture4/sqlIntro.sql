

create table shift(day date, cashier string); 
insert into shift values 
('2025-02-11'::date, 'Mads'), 
('2025-02-12'::date, 'Anna'), 
('2025-02-18'::date, 'Anna'), 
('2025-02-19'::date, 'Anna'); 

create table user(username string, email string, password string); 
insert into user values 
('Anna', 'anna', '***'), 
('Martin', 'mhent', '***'), 
('Omar', 'omsh', '***'); 

create table product(productname string, price int); 
insert into product values 
('Tea', 100), 
('Small', 85), 
('Large', 100), 
('Fancy', NULL); 

create table purchase(purchasetime timestamp, productname string,  
username string); 
insert into purchase values 
('2025-02-11 9:55'::timestamp, 'Tea', 'Martin'), 
('2025-02-12 10:03'::timestamp, 'Small', 'Martin'), 
('2025-02-12 10:05'::timestamp, 'Small', 'Omar'), 
('2025-02-12 10:06'::timestamp, 'Large', 'Omar'), 
('2025-02-19 9:00'::timestamp, 'Small', 'Martin');


----------- QUERY ----------

-- 1. How many purchases are recorded in the database?
-- Label the result as "total_count".   
SELECT COUNT(*) AS total_count
FROM purchase;

-- 2. The coffeehouse has decided to increase prices by 30%. Output the product table after 
-- the price increase. Label the updated price as "new_price".
select productname, price * 1.3 as new_price 
from product

-- 3. Output purchases of only "Tea" and "Large" products using the IN clause.   
SELECT *, 
from purchase 
where productName in ('Tea', 'Large')

-- 4. Output cashiers in alphabetical order without duplicates.   
select distinct cashier
from shift 
order by cashier asc

-- 5. Which purchases(sell) were made by which cashier?   
select cashier, purchase.*
from shift join purchase -- Think you start here. 
on shift.day = purchase.purchasetime::date; 
-- the reason we use ::date is casting, so we cast purchasetime as date. shift.day is already date format. 

-- 6. Which purchases(sell) were made by which cashier?   
select cashier, purchase.*
from shift join purchase -- Think you start here. 
on shift.day = purchase.purchasetime::date
-- the reason we use ::date is casting, so we cast purchasetime as date. shift.day is already date format. 
where cashier = 'Anna'; 

-- 7. What products did Anna sell to Martin? List only the product names without duplicates.   
select distinct productname
from shift join purchase -- you got two tables you want to join 
on shift.day = purchasetime::date -- tell sql where you want to join them. 
where cashier = 'Anna' 
and username = 'Martin'

-- 8. Which cashier is not in the user table?   
select cashier
from shift
where cashier not in (select username from user)

-- other solution 
select distinct cashier
from shift left outer join user 
on cashier = username
where username is null

-- 9. Find the email addresses of users who purchased large coffees.   
select distinct email 
from user join purchase 
on user.username = purchase.username 
where productname = 'Large'

-- 10. Which users purchased both small and large coffees? (Hint: Join the purchase table with itself.)   
select distinct s.username
from purchase s join purchase l
on s.username = l.username
where s.productname = 'Small'
and l.productname = 'Large'

-- 11. How much money did the coffeehouse make per product? Sort by the best-selling 
-- roduct first, and alphabetically for products with the same sales. 
SELECT p.productname SUM(pr.price) AS total_revenue
FROM purchase p
JOIN product pr ON p.productname = pr.productname
GROUP BY p.productname
ORDER BY total_revenue DESC, p.productname ASC;

-- 12. How often was each product sold? Sort by the best-selling product first, and 
-- alphabetically for products with the same number of sales. (Hint: Do not use COUNT(*) 
-- because it counts NULL as 1.)
SELECT productname, COUNT(productname) AS times_sold
FROM purchase
GROUP BY productname
ORDER BY times_sold DESC, productname ASC;

-- 13. What days were the busiest by the number of products sold? List only the busiest dates 
-- and the number of products sold on those dates. 



