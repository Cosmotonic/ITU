
select * 
from shift join user 
on cashier = username;

-- Different joins on slide 7
-- Using, cross join, join (cross join is used by Martin) 

-- look up 1NF-3NF 


-- Left outer join - does include all results from left table to right but not all from right
-- join - only include matching results of two tables. (join is also known as Inner join)
-- full outer join - includes all on both on tables. 
CREATE VIEW totalPurchase AS
    SELECT * FROM product JOIN purchase 
    ON product.productname = purchase.productname;
SELECT sum(price) FROM all_purchase; -- 333 s 




-- Anti join vs Not in . slide 21 

