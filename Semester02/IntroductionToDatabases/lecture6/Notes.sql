-- Rows meaning the rows between row 1-5 etc. 
-- Range meaning everything in the row that has a value in the range of 1-2 etc. 
-- Groups (rarely used) can combine(?)

select * lag(price) over (order by purchasetime rows between current row and 2 fllolowing) 
from sales

-- window: Orderby/partition slide 19 - 22
-- Test: Compare games where the current game had more goals than the preiovus 


-- UNION does not hold dupliates UNION ALL does. 
-- INTERSECT - overlapping 
-- EXCEPT uniq the first condition. 



-- Build & run instructions slide 33. 