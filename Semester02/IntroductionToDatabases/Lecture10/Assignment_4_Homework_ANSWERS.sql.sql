
/*
Unpack laurete .json
*/
with laureates as ( 
select unnest(laureates) as laureate 
from 'C:/ITU/Semester02/IntroductionToDatabases/Lecture10/laureates.json'
) 

select * from laureates;

/* Question 1 & 2 (5 points) 
-- How many Nobel Prize laureates were born in Denmark (bornCountryCode = 'DK')? 
Enter the result and your SQL query that produces exactly this result. SQL queries that output 
different or additional information will result in a loss of points. SQL queries that are unnecessarily 
complex will also result in a loss of points. The SQL query must run in DuckDB. SQL queries that do 
not run in DuckDB will result in a loss of points. 

Answer: 13
*/
select count(laureate.bornCountryCode)
from laureates where laureate.bornCountryCode = 'DK';

/*
Question 3 & 4 (5 points) 
In which city or cities were the most Nobel Prize laureates born? Exclude laureates whose bornCity 
is null. 

Answer: New York
*/

select laureate.bornCity, count(*) as topCities
from laureates
group by laureate.bornCity
order by topCities desc
limit 3;



/*
Question 5 & 6 (5 points) 
Which people have won more than one Nobel Prize? Exclude laureates whose surname is null.

Answer: 
Marie Curie
John Bardeen
Linus Pauling
Frederick Sanger
Barry Sharpless

*/

with laureates as (
  select unnest(laureates) as laureate
  from 'C:/ITU/Semester02/IntroductionToDatabases/Lecture10/laureates.json'
)
select laureate.*, laureate.firstname || ' ' || laureate.surname as fullname
from laureates
where laureate.surname is not null AND len(laureate.prizes) > 1;

/*
Question 7 & 8 (5 points) 
Who are the three youngest Nobel laureates at the time they received their prize? Exclude laureates 
with incomplete birthdates where the day or month is zero. Assume that laureates always received 
the prize on December 10 of the award year. Hint: Use DuckDB's make_date() function to create 
the prize date, and unnest the prizes array since some laureates or organizations have received 
more than one prize.

Answer: 

Office of the United Nations High Commissioner for Refugees 	1950-12-14	1954-12-10	4
Malala Yousafzai	                                            1997-07-12	2014-12-10	17
Lawrence Bragg	                                                1890-03-31	1915-12-10	25
*/

with extracted_laureates as (
  select unnest(laureates) as person
  from 'C:/ITU/Semester02/IntroductionToDatabases/Lecture10/laureates.json'
), extracted_prizes as (
  select person, unnest(person.prizes) as prize
  from extracted_laureates
  -- Ekskluder ufuldstændige fødselsdatoer hvor dag eller måned er 0
  where split_part(person.born, '-', 2) != '00'
  and split_part(person.born, '-', 3) != '00'
)
select 
  person.firstname || ' ' || coalesce(person.surname, '') as fullname,
  cast(person.born as date) as birthdate,
  make_date(cast(prize.year as integer), 12, 10) as prize_date, -- cast(prize.year as integer): prize.year er tekst fra JSON, make_date kræver et heltal
  datediff('year', -- 'year' er et flag der styrer enheden for datediff, her år
    cast(person.born as date), 
    make_date(cast(prize.year as integer), 12, 10) -- kan ikke bruge prize_date alias her, SQL tillader ikke at referere til aliases i samme select
  ) as age_at_prize
from extracted_prizes
order by age_at_prize asc
limit 3;


/*
JSON Pointer (10 points) 
Create JSON Pointer expressions to solve the following tasks using the JSON document 
*/

/*
laureate.json. 
Question 9 & 10 (3 points) 
Access the first laureate's surname in the JSON document laureate.json. 
Enter the result and the JSON Pointer expression that produces exactly this result. 

Answer: "Röntgen"
*/

select json_extract(laureates, '/0/surname')
from 'C:/ITU/Semester02/IntroductionToDatabases/Lecture10/laureates.json';

/*
Question 11 & 12 (3 points) 
Access the birth city of the 100th laureate in the JSON document laureate.json. 
Enter the result and the JSON Pointer expression that produces exactly this result. 

Answer: Copenhagen
*/

select json_extract(laureates, '/99/bornCity')
from 'C:/ITU/Semester02/IntroductionToDatabases/Lecture10/laureates.json';



/*
Question 13 & 14 (4 points) 
Access the share of the second prize of the sixth laureate in the JSON document laureate.json. 
Enter the result and the JSON Pointer expression that produces exactly this result.

Answer: 1
*/
select json_extract(laureates, '/5/prizes/1/share')
from 'C:/ITU/Semester02/IntroductionToDatabases/Lecture10/laureates.json';


/* Indexes and Min/Max Summaries (10 points)  */

-- In row group 1, 'Cheese' falls within the range $[Apples, Milk]$. In row group 3, 'Cheese' is
-- explicitly within the range $[Bread, Cheese]$. Row groups 2 and 4 can be safely skipped 
-- because 'Cheese' falls outside their alphabetical boundaries.

-- Row group 1
-- Row group 3

https://learnit.itu.dk/pluginfile.php/446237/mod_resource/content/2/Homework%204.pdf


/*
ANALYSE AF JOIN-IMPLEMENTERINGER (SPØRGSMÅL 19)

* a. SQL database systems can be built using any join implementation.
Status: KORREKT. Forklaring: SQL er et deklarativt sprog, der definerer "hvad" der skal hentes, ikke "hvordan". 
Implementeringen af join-logikken er op til database-motoren. En database kan i teorien køre med kun én 
type join, selvom de fleste moderne systemer implementerer flere for at optimere ydeevnen.

* b. Hash joins make use of hash functions, which have near-constant insertion and lookup times.
Status: KORREKT. Forklaring: Hash-funktioner muliggør gennemsnitlig konstant tid, O(1), for både 
indsættelse og opslag i en hash-tabel. Dette gør Hash Joins meget effektive til store datamængder, 
da man hurtigt kan finde matchende rækker uden at gennemløbe hele tabellen.

* c. The performance of the sort-merge join is dominated by the merge phase.
Status: FORKERT. Forklaring: Ydeevnen domineres af sorteringsfasen. Sortering 
af to tabeller med henholdsvis N og M rækker har en tidskompleksitet 
på O(N log N + M log M). Selve flettedelen (merge-fasen) er lineær, O(N + M), 
og er derfor den mindst krævende del af processen.

* d. Nested-loop joins have a quadratic runtime.
Status: KORREKT. Forklaring: En basal Nested-loop join sammenligner hver række fra
den ydre tabel med hver række i den indre tabel. Hvis begge tabeller har størrelsen n,
resulterer det i n * n sammenligninger, hvilket svarer til en kvadratisk tidskompleksitet på O(n^2).

KONKLUSION: De korrekte svarmuligheder er a, b og d.
*/