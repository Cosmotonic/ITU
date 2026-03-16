
create or replace table product(productname string, price int); 
insert into product values 
('Tea', 100), 
('Small', 85), 
('Large', 100), 
('Fancy', NULL), 
('Funky', 130), 
('Funny', 150); 

-- SELECT * FROM product; 
-- DELETE FROM product WHERE price > 100;
-- UPDATE product SET price = price + 10 WHERE productname LIKE 'F%';
SELECT * FROM product; 
drop table product 

delete from product where price < 100 or price IS null; 
SELECT * FROM product; 

-- set discount 
alter table product add column discount double; 
update product product SET discount = price * 0.75; 



-- rank products by how well they sold
WITH turbine_sums AS (
  SELECT turbine_id, ROUND(SUM(COALESCE(power_output, 0))) AS total_power
  FROM wind_turbine_production
  GROUP BY turbine_id 
)
SELECT rank() OVER (ORDER BY total_power DESC) AS power_rank, *
FROM turbine_sums
ORDER BY power_rank, turbine_id;

