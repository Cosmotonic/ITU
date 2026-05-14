-- ctrl+shitf+p     color 
-- Normal forms  


-- TODO Studies 
Partition, windows 



-- Normal forms.
    1NF: No cell may contain multiple values.
        Example: A PhoneNumbers field containing "1234, 5678" is not allowed.
    2NF: No attribute may depend on only part of a composite key.
        Example: In a table with key (StudentID, CourseID), StudentName depends only on StudentID, so it violates 2NF.
    3NF: No attribute may depend on another non-key attribute.
        Example: DepartmentPhone depends on Department instead of directly on EmployeeID, so it violates 3NF.


-- *** OLTP: Online Transactional Processing (Drift) - Intuition: Det er her, dagligdagens transaktioner sker lynhurtigt.
-- Purpose: Handles many small, fast read and write operations (e.g., buying an ice cream or withdrawing cash).
-- Focus: Data integrity and speed of updates/inserts.
-- Example: A supermarket point-of-sale system or an airline booking site.

-- *** OLAP: Online Analytical Processing (Analyse) -  Intuition: Det er her, man ser på de store linjer og historiske data.
-- Purpose: Runs complex queries on massive amounts of data to find patterns or insights.
-- Focus: Read performance and the ability to aggregate data (e.g., sum, average).
-- Example: "What were the total ice cream sales in North Jutland this July compared to last year?"




