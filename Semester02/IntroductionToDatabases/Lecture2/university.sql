-- Entities
CREATE TABLE Student (
    StudID INTEGER PRIMARY KEY AUTOINCREMENT,  
    Name VARCHAR(255) NOT NULL, 
    Email VARCHAR(255) NOT NULL,
    -- relation to my semester (always on my "many" relation)
    starts_in INT DEFAULT 1, 
    FOREIGN KEY (starts_in) REFERENCES Semester(SemID)
);

CREATE TABLE Semester(
  SemID INT PRIMARY KEY NOT NULL, 
  Year int NOT NULL, 
  season VARCHAR CHECK (season IN ('Fall', 'Spring')) DEFAULT 'Fall'
); 

CREATE TABLE Course(
  CID INT PRIMARY KEY NOT NULL, 
  Name VARCHAR(255) NOT NULL, 
  Capacity INT CHECK(capacity<= 200)
); 

CREATE TABLE examiner(
  EID INTEGER PRIMARY KEY AUTOINCREMENT,  
  Name VARCHAR(255) NOT NULL, 
  Email VARCHAR(255)  NOT NULL
); 

CREATE TABLE takes (
  Grade INT NOT NULL, 
  Room VARCHAR(255) NOT NULL, 
  StudentID INT NOT NULL , 
  ExaminerID INT NOT NULL, 
  CourseID INT NOT NULL,  
  SemesterID INT NOT NULL, 
  FOREIGN KEY (StudentID) REFERENCES Student(StuID), 
  FOREIGN KEY (ExaminerID) REFERENCES Examiner(EID), 
  FOREIGN KEY (CourseID) REFERENCES Course(CID), 
  FOREIGN KEY (SemesterID) REFERENCES Semester(SemID), 
  PRIMARY KEY ( StudentID, CourseID, ExaminerID, SemesterID)
); 









