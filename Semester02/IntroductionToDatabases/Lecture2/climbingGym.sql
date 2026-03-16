CREATE TABLE climbing_gym ( name String primary key); 


CREATE TABLE hall(name string references climbing_gym(name), 
number int, 
hallType string CHECK (hallType IN ('bouldering', 'rope climbing')), 
age_restriction int, 
primary key(name, number)); 


CREATE TABLE route (
rname string, 
name string, 
number int, 
difficulty string, 
primary key (rname, name, number), 
foreign key (name, number) references  hall(name, number)
)