-- Entity
CREATE TABLE social_worker (
    id INT PRIMARY KEY, 
    name STRING NOT NULL, 
    contact STRING, 
    photo VARCHAR(255)
);

CREATE TABLE person_with_addiction (
    id INT PRIMARY KEY, 
    name STRING NOT NULL, 
    next_appointment datetime, 
    addiction_type STRING, 
    contact STRING, 
    photo VARCHAR(255)
); 
    
-- Relationship
CREATE TABLE assign (
    id INT PRIMARY KEY,
    social_worker_id INT REFERENCES social_worker(id),
    person_with_addiction_id INT REFERENCES person_with_addiction(id),
    time_of_visit DATETIME
);

-- Entities below my aggregation "collaboration"
CREATE TABLE logbook (
    id INT PRIMARY KEY,
    assign_id INT,
    summary STRING, 
    time_of_latest_visit DATETIME, 
    -- my normalised relation of 1..m records. 
    FOREIGN KEY (assign_id) REFERENCES assign(id) 
);

CREATE TABLE follow_up (
    id INTEGER PRIMARY KEY,
    logbook_id INT,
    time_of_visit DATETIME,
    todo_description VARCHAR(140),
    -- my normalised relation of 1..m todo registrations
    FOREIGN KEY (logbook_id) REFERENCES logbook(id)
);





