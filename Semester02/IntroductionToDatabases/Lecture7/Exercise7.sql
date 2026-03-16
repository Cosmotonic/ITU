


-- Create role 
CREATE ROLE supervisor; 
CREATE ROLE race_officer; 
CREATE ROLE trainer;

-- create tables
CREATE TABLE race_results ( 
    race_id int; 
)

CREATE TABLE horses ( 
    horse_id int;
    name string;
)

-- Add roles to tables 
grant SELECT on table race_results to race_officer; 
grant INSERT on table race_results to race_officer; 

grant SELECT on table race_results to supervisor; 
grant SELECT on table horses to supervisor; 

grant SELECT on table horses to trainer; 
grant UPDATE on table horses to trainer; 

grant role race_officer to role supervisor;

-- Grant users  
grant role race_officer to user Liam;
grant role supervisor to user Ava;
grant role Trainer to user Sophia;
grant role Trainer to user Eathan;


-- 
select * from 'https://www.reddit.com/r/worldnews.json'; 

select * from read_json_objects('https://www.reddit.com/r/worldnews.json');
-- Med automatisk parsing:
select * from 'worldnews.json';
-- DuckDB læser JSON og konverterer til VARCHAR, BIGINT, BOOLEAN, STRUCT, ARRAY osv.
-- Slides side 51 viser præcis dette

-- Uden automatisk parsing:
select * from read_json_objects('worldnews.json');
-- DuckDB returnerer hver JSON-linje som én rå JSON-type kolonne
-- Du navigerer selv rundt med json_extract() eller ->


-- question 3 
select json->'/data/children/0/data/title'
from read_json_objects('worldnews.json');

-- question 4. 
select unnest(from_json(json->'/data/children', '["JSON"]'))->'/data/title' as title
from read_json_objects('https://www.reddit.com/r/worldnews.json');

-- question 5. 
-- Write a SQL query that splits all titles into words and counts the frequency of words longer than 
-- three letters. Output the top 10 results by word count. Hint: Use the string_split() function to 
-- split the titles into arrays of words, unnest the word arrays, and then group by words. 
with titles as (
    select unnest(from_json(json->'/data/children', '["JSON"]'))->'/data/title' as title
    from read_json_objects('https://www.reddit.com/r/worldnews.json')
),
words as (
    select unnest(string_split(title::varchar, ' ')) as word
    from titles
)
select 
    word,
    count(*) as word_count
from words
where len(word) > 3
group by word
order by word_count desc
limit 10;