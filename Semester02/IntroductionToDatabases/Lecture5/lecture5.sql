-- Lecture 5

-- create tables
create or replace table shift(day date, cashier string);
insert into shift values
  ('2025-02-11'::date, 'Mads'),
  ('2025-02-12'::date, 'Anna'),
  ('2025-02-18'::date, 'Anna'),
  ('2025-02-19'::date, 'Anna');

create or replace table user(username string, email string, password string);
insert into user values
  ('Anna', 'anna', 'coffee!'),
  ('Martin', 'mhent', 'secret123'),
  ('Omar', 'omsh', 'secret123');

create or replace table product(productname string, price int);
insert into product values
  ('Tea', 100),
  ('Small', 85),
  ('Large', 100),
  ('Fancy', null);

create or replace table purchase(purchasetime timestamp, productname string, username string);
insert into purchase values
  ('2025-02-11 9:55'::timestamp, 'Tea', 'Martin'),
  ('2025-02-12 10:03'::timestamp, 'Small', 'Martin'),
  ('2025-02-12 10:05'::timestamp, 'Small', 'Omar'),
  ('2025-02-12 10:06'::timestamp, 'Large', 'Omar'),
  ('2025-02-19 9:00'::timestamp, 'Small', 'Martin');

-- wrong: using limit and order by (misses Tea)
select *
from product
order by price desc, productname
limit 1;

-- wrong: aggregate function but no group by (does not compile)
select productname, max(price)
from product;

-- wrong: outputs all groups and max price per group
select productname, max(price)
from product
group by productname;

-- correct: most expensive products
select *
from product
where price = (select max(price) from product)
order by productname;

-- insert full rows
insert into user values
  ('Mads', 'mads@itu.dk', 'test'),
  ('Christina', 'chris@itu.dk', 'SuperSecure');
select * from user;

-- insert only specific columns
insert into user(username) values ('Donna');

-- create a table with unique email addresses
create or replace table email(email string primary key);

-- insert combined with subquery
insert into email
select email from user where email is not null;

select * from email;

-- insert a duplicate email address (error)
insert into email values ('mads@itu.dk');

-- specify what to do, e.g. ignore or update
insert into email values ('mads@itu.dk')
on conflict do nothing;

-- update user Donna to add email and pw
update user
set email = 'donna@itu.dk', password = 'myPass'
where username = 'Donna';

select * from user;

-- live exercise 1
update user
set email = concat(email, '@itu.dk')
where email not like '%@itu.dk';

-- delete the Fancy product
delete from product 
where productname = 'Fancy';

select * from product;

-- delete everything from product
delete from product;

select count(*) from product;

-- add password_hash and salt to user table
alter table user add column password_hash string;
alter table user add column salt double;

-- generate random salt values, and hash passwords
update user set salt = random();
update user set password_hash = sha256(concat(password, salt));

-- drop the cleartext passwords
alter table user drop column password;

-- live exercise 2
update user set password_hash = sha256(concat('fL7mK2jZ9c', salt))
where username = 'Donna';

select password_hash = sha256(concat('fL7mK2jZ9c', salt))
from user
where username = 'Donna';

-- views, populate product table again
insert into product values
  ('Tea', 100),
  ('Small', 85),
  ('Large', 100),
  ('Fancy', null);

-- view that computes revenue per day
create view revenuePerDay as
select purchasetime::date as day, sum(price) as revenue
from purchase join product
on purchase.productname = product.productname
group by day;

select * from revenuePerDay
where revenue >= 100
order by revenue desc;

-- behind the scenes: query rewrite
select * from (
  select purchasetime::date as day, sum(price) as revenue
  from purchase join product
  on purchase.productname = product.productname
  group by day
)
where revenue >= 100
order by revenue desc;

-- WITH clause
with revenuePerDay as (
  select purchasetime::date as day, sum(price) as revenue
  from purchase join product
  on purchase.productname = product.productname
  group by day
)
select * from revenuePerDay
where revenue >= 100
order by revenue desc;

-- recursive with
with recursive counter(num) as (
  select 1
  union all
  select num + 1 from counter
)
select num from counter
limit 10;

-- create a view for the following examples
create view sales as
select pu.*, price
from purchase pu join product pr
on pu.productname = pr.productname;

select * from sales order by purchasetime;

-- reminder: group by produces one row per group
select username, sum(price) 
from sales
group by username
order by username;

-- window functions produce one row per row
select username, sum(price) over(partition by username order by purchasetime) 
from sales
order by purchasetime;

-- cumulative sum of sales over time
select *, sum(price) over (order by purchasetime) as cum_sales
from sales
order by purchasetime;

-- cumulative sum of sales per user over time
select *, sum(price) over (partition by username order by purchasetime) as cum_sales
from sales
order by purchasetime;

-- cumulative sum of sales per user over time, result sorted by username first
select *, sum(price) over (partition by username order by purchasetime) as cum_sales
from sales
order by username, purchasetime;

-- rank products by how well they sold
with product_sums as (
  select pr.productname, sum(coalesce(pr.price, 0)) as prod_sales
  from product pr left outer join sales s
  on pr.productname = s.productname
  group by pr.productname
)
select rank() over(order by prod_sales desc) as product_rank, *
from product_sums
order by product_rank, productname;

-- difference of sales to previous day
with revenue_per_day as (
  -- same as the very last query of lecture 4
  select purchasetime::date as day, sum(price) as revenue
  from purchase join product
  on purchase.productname = product.productname
  group by day 
)
select day, revenue, revenue-lag(revenue) over(order by day) as diff_to_prev_day
from revenue_per_day;

-- row framing example
create table results(points int);
insert into results values (10), (8), (12), (9), (9), (9), (5), (7);

-- compute the sum of each point and the points on either side of it
select points, 
  sum(points) over (rows between 1 preceding and 1 following) we
from results;

-- better: always use an order by within the window function,
-- requires an id column in the results table
drop table results;
create table results(id int, points int);
insert into results values (1, 10), (2, 8), (3, 12), (4, 9), (5, 9), (6, 9), (7, 5), (8, 7);

select points, 
  sum(points) over (rows between 1 preceding and 1 following order by id) we
from results order by id;


---------------------------------------------------------
--                                                     --
-- the SQL code below runs in Snowflake, not in DuckDB --
--                                                     --
---------------------------------------------------------

-- UDFs
-- SQL UDF to calculate discount by given percent
create function discount(price int, discount_percent int)
returns float
as
$$
  price * (100 - discount_percent)::float / 100
$$;

select productname, price, discount(price, 18), discount(price, 25)
from product;

-- JavaScript UDF to test anagrams
create function is_anagram(A string, B string)
returns boolean
language javascript
as
$$
  A = A.toLowerCase();
  B = B.toLowerCase();
  var sorted_a = A.split('').sort().join('');
  var sorted_b = B.split('').sort().join('');
  return sorted_a == sorted_b;
$$;

create table names(name string);
insert into names values
  ('Elina'), ('Naile'), ('Aleni'), ('Marcus'), ('Sophie');

select x.name, y.name
from names x, names y
where is_anagram(x.name, y.name)
  and x.name < y.name
order by x.name;

-- Python UDF to hash a password using Argon2
create or replace function argon2_str(input string)
returns string
language python
runtime_version = '3.13'
packages = ('argon2-cffi')
handler = 'argon2_str'
as
$$
from argon2 import PasswordHasher
def argon2_str(input):
  hashed = PasswordHasher().hash(input)
  return hashed
$$;

-- execute Python UDF on original user table
select username, email, argon2_str(password) from user;

-- LLMs

-- create the amazon_reviews table
create or replace table amazon_reviews (
  reviewer string,
  stars int,
  review text
);

-- insert the review data
insert into amazon_reviews values
('DrewD', 4, 'Superb book, but no ANSWERS to Review Questions? Really?

A great read on modern database management. Really impressive. I''m enjoying it.

However, I''m giving it a four out of five because it doesn''t include any answers to any of the Review Questions at the end of each chapter. I went to the publisher''s webpage for the book, and the solutions to the Review Questions are locked for special access for instructor''s only.

The problem is that the publisher is assuming that instructors are going to use the Review Questions as part of in-class quizzes and tests. I''ve been in education my whole life - first as an instructor and now as a student, and the reality is that instructors in IT-related subjects almost never use Review Questions at the end of chapters as quiz or test content that''s actually graded. For example, our class is graded based solely on assignments, and projects. We''re simply using this book for the content - not the 10-15 Review Questions that are at the end of each chapter.

The questions should be intended for exactly what the name suggests - REVIEW QUESTIONS. So, some answers would be great.

Now I have to hassle my instructor to sign up for the Solutions PDF. Annoying and stupid. Surely the publisher''s fault.'),

('Draining Sink', 3, 'Comprehensive but difficult to follow

Like other reviewers have said, it does go into a great deal of detail but for most of the text, it felt like reading the author''s stream of consciousness (which arguably is what books can be but instructional material isn''t always effective if done this way). Sometimes the words didn''t make sense simply because (1) there was so many of them or (2) the author described them in a way only someone already familiar with the subject would understand.

I found myself reading something over and over again and ultimately referring to youtube to make sense of it. I might also just be slow, so who knows!

I recommend the author revise with more illustrations/tables and to place them near where the text is describing them. When the words DO reference an illustration or table, sometimes it was many pages back which made it inconvenient (that sounded really whiny but it was paragraphs of information later in the book describing something many pages back. It can get annoying when it happens over and over).'),

('SimonPer', 3, 'cover bends easily

very interesting but the cover bends to easily to my liking.'),

('Amazon Customer', 2, 'No solutions/explanations for review questions

The content seems to be good but it is unfortunate that the online solution manual is locked and is intended for instructors only(strange!!! ).It asks for registration and after registration and getting your personal details, it simply says locked and intended for instructors only.I feel, verifying your knowledge with correct answer and explanation of the correct answer is very fundamental and integral part of any learning process and it is disallowed here.What is the point in buying a content which doesn''t allow to consolidate and complete your learning process.I will spend my hard-earned money elsewhere .For this sole negative approach I''m giving 2 stars.'),

('Anne', 5, 'Comprehensive book with high degree of practical orientation

I have used this book as the literature for the course "Principles of Database Management" that was taught by Bart Baesens and Wilfried Lemahieu at KU Leuven.

To start with, I haven''t had much of an IT/programming background apart from some very basic work with Microsoft Access, SQL, and VBA before starting the course and reading the book. However, I think that especially the first chapters are well-suited for people that have only little programming/IT experience. The later chapters are surely for the advanced reader.

The book is without doubt among the top books that I have worked with during my university education. It was clearly and concisely written. I think that the book''s greatest asset is that it is highly practically oriented. This means that the book show directly how the theoretical apsects are put into practice. Especially the given examples, exercises and open questions were helpful in that regard. Moreover, the playground environment and database quiz offerend on the corresponding website of that book were a great stepping stone in the learning process.

A further asset of the book is that it covers several different aspects related to the topics of data analysis / databases and successfully manages to connect them and thereby offers a holistic perspective of the topic of database management.

To sum up, I think that the book is a great way of learning about Database Management and is proper for a variety of different users and readers.'),

('ML Enthusiast', 5, 'Comprehensive presentation of data base and data analytics

I use this book to prepare lectures about application of data base, data warhouse and data analytics in banking. Whereas other books about this topic focus too much on (often unneeded) theoretical details, this book is very successful to bridge the gap between the concepts and applications. The extend of the book covers many aspects needed in daily life and help the students to broaden their view. Besides classical technical topics the book also includes useful issues as Data Integration , Data Quality and Data Governance. The books is recommended in bachelor and master courses; for lecturers and for students.'),

('Jan Mendling', 5, 'A Data Science book on Databases

This book is a much needed foundational piece on data science. The authors successfully integrate the fields of database technology, operations research and big data analytics, which have often been covered independently in the past. A key asset is its didactical approach that builds on a rich set of industry examples and exercises. Thanks to the complementary material, the book is very well suited for all scholars who run a data science or database course.');

select * from amazon_reviews;

-- double-check if the review matches the stars given
select reviewer, 
   stars,
   snowflake.cortex.complete('mistral-large', 'On a scale from 1-5, how many stars does the following review give according to the sentiment of the text. Output a single number only. Output no text. Review: ' || review) as llm_stars,
   review
from amazon_reviews;

-- what to improve per stars given
select stars,
  snowflake.cortex.complete('snowflake-arctic', 'What to improve? Answer in three words maximum: ' || listagg(review, '; ')) as improvements
from amazon_reviews
group by stars
order by stars desc;