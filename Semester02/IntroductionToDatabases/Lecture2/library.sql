CREATE TABLE reader (
membership_number int primary key, 
name string not null, 
age int check (age >= 12)   
); 

CREATE TABLE library ( 
name string primary key 
); 

CREATE TABLE book(
title string primary key, 
genre string not null, 
length int CHECK (length <= 1000)
);

-- aggregate
CREATE TABLE visit (
membership_number int references reader(membership_number), 
library_name string references library(name), 
time_of_visit datetime,
primary key(membership_number, library_name, time_of_visit)
);

CREATE TABLE borrow (
  book_title string references book(title), 
  membership_number int, 
  library_name string, 
  time_of_visit datetime, 
  foreign key ( membership_number, libaray_name, time_of_visit)
    references visit(membership_number, library_name, time_of_visit), 
    primary key(membership_number, library_name, time_of_visit, book_title)
)