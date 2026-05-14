-- Question 1 (7 points) 
/*
Relationships:
Researchers visiting an ancient site often study multiple artifacts during their visit, although some visits 
may result in no artifacts being studied. 

The entry time to the ancient site is always recorded. Ancient sites 
are identified by their name and are located in a specific country. 

Researchers are identified by a unique 
researcher ID and also have a name and a field of expertise. 

Artifacts are characterized by their estimated 
age and material. All artifacts are at least 500 years old. 

relationships: 
During each visit to an ancient site, a researcher 
may study many artifacts but may remove at most one artifact for further research purposes. Each artifact 
can be removed only once.
*/ 

-- Question 2 (7 points) 
CREATE TABLE Researcher (
    researcher_id INTEGER PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    field_of_expertise VARCHAR(255) NOT NULL
);

CREATE TABLE Site (
    site_id INTEGER PRIMARY KEY,
    country VARCHAR(255) NOT NULL,
    site_name VARCHAR(255) NOT NULL
);

CREATE TABLE Artifact (
    artifact_id INTEGER PRIMARY KEY,
    age INTEGER CHECK (age >= 500),
    material VARCHAR(255),

    studied_by INTEGER REFERENCES Researcher(researcher_id),
    removed_by INTEGER REFERENCES Researcher(researcher_id),
    belongs_to_site INTEGER REFERENCES Site (site_id)
);

CREATE TABLE Visit (
    researcher_id INTEGER REFERENCES Researcher(researcher_id),
    site_id INTEGER REFERENCES Site(site_Id),
    time_of_visit TIMESTAMP,
    PRIMARY KEY (researcher_id, country, site_name, time_of_visit),
);

-- Question 3 (5 points) 
-- fucking pas for nu. 

-- Question 4 (3 points)
-- ANSWER: Not in 1NF 

-- Question 5 
--  FleaMarket(Artifact, Seller) 
--  ArtifactInfo(Artifact, ArtifactOrigin) 






-- Question 12 & 13 (5 points) 

select firstname, lastname, sale_history.aid, sale_history.sell_date
from buyer 
join sale_history on buyer.cpr_number = sale_history.cpr_number 
join artifact on sale_history.aid = artifact.id 
where material = 'Iron' 
order by sell_date desc 
-- limit 1; 