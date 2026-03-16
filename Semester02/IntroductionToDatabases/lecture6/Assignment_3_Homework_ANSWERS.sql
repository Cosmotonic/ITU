-- Question 1 & 2 (5 points) 
-- On how many days did wind turbine WT-001 produce less power than it did on the previous day? 
-- Answer: 13
WITH windowName AS (
  SELECT production_date, turbine_id, power_output,
      LAG(power_output) OVER (PARTITION BY turbine_id ORDER BY production_date) AS prev_day_output -- igår
  FROM wind_turbine_production
  WHERE turbine_id = 'WT-001'
)
SELECT count(production_date)
FROM windowName
WHERE (power_output < prev_day_output) 

/*
NOTE Tilgang trin:
Forstå hvad window functions er — OVER, PARTITION BY, ORDER BY
Hent dagens og gårsdagens output ved siden af hinanden med LAG
Wrap i CTE, filtrer hvor dagens er lavere end gårsdagens, tæl rækkerne
*/


-- Question 3 & 4 (5 points) 
-- Rank the power output per wind turbine per day. That is, a wind turbine is ranked #1 if it produced 
-- more power than the other wind turbines on the same day. How often did wind turbine WT-001 rank #1? 
-- Answer: 3
WITH date_partition_window as (
  SELECT production_date, turbine_id, power_output,
    RANK() OVER (PARTITION BY production_date ORDER BY power_output DESC) as daily_best_WT
  FROM wind_turbine_production
) 
SELECT COUNT(turbine_id) -- daily_best_wt, production_date, power_output, turbine_id
FROM date_partition_window
WHERE daily_best_WT = 1 AND turbine_id = 'WT-001'

/*
NOTE Tilgang trin:
-- Vi skal have et vindue med en ny collumn der hedder daily_best_WT
-- Det skal nok group by date vi skal lave en count af den daily_best_wt hvor wt-001 er nævnt
-- Note PARTITION BY : "nulstil beregningen hver gang denne værdi skifter".
*/


-- Question 5 & 6 (5 points) 
-- Consider the power output of three consecutive days for all wind turbines (2 previous days and the 
-- current day). On how many days did the power output of three consecutive days exceed 500? 
-- answer: 4
WITH 
  daily_total_window as (
  SELECT production_date, SUM(power_output)::INT as daily_sum
  FROM wind_turbine_production
  GROUP BY production_date
  ),

  three_day_sum_window as (
  SELECT production_date, daily_sum,
  SUM(daily_sum) OVER (
  ORDER BY production_date -- en dag ad gangen selvfølgelig
  ROWS BETWEEN 2 PRECEDING AND CURRENT ROW) as three_day_sum
  FROM daily_total_window
  )
  
SELECT COUNT(three_day_sum) -- three_day_sum, production_date
FROM three_day_sum_window
WHERE (three_day_sum > 500)

-- note: First we make a new window (A temp table view in our own order) that summarize all daily power
-- Then we make a select and summarize on that new daily sum collumn. 
-- we tell it to roll over and count some before and the current and name that - finaly sort 500+



-- Question 7 & 8 (5 points) 
-- Compute the cumulative sum of power output per wind turbine. What is the cumulative power 
-- output of wind turbine WT-001 on 2026-01-06? Note: The query must use a window function to 
-- compute the cumulative sum. 
-- answer: 199
WITH cumulative_window as (
  SELECT turbine_id, production_date,
  sum(power_output) OVER (PARTITION BY turbine_id ORDER BY production_date)::INT as WT_cumulative_sum
  from wind_turbine_production
  )
SELECT * from cumulative_window
WHERE turbine_id = 'WT-001' AND production_date = '2026-01-06'


--- Guess the output game from CLAUDE ---
-- gets the average output for turbine WT-001
SELECT turbine_id, 
    AVG(power_output) OVER (PARTITION BY turbine_id) as avg_output
FROM wind_turbine_production
WHERE turbine_id = 'WT-001'
ORDER BY avg_output
LIMIT 1

-- counts the days where the power_output was bigger than the previous three day average
WITH running_avg AS (
    SELECT 
        turbine_id,
        production_date,
        power_output,
        AVG(power_output) OVER (
            PARTITION BY turbine_id 
            ORDER BY production_date
            ROWS BETWEEN 2 PRECEDING AND CURRENT ROW
        ) as three_day_avg
    FROM wind_turbine_production
    WHERE turbine_id = 'WT-001'
)
SELECT turbine_id, power_output, three_day_avg::INT -- COUNT(*)
FROM running_avg
WHERE three_day_avg > power_output

-- Show top 3 best turbines per day 
WITH daily_rank AS (
    SELECT 
        production_date,
        turbine_id,
        power_output,
        RANK() OVER (PARTITION BY production_date ORDER BY power_output DESC) as day_rank
    FROM wind_turbine_production
)
SELECT turbine_id, COUNT(*) as top3_count -- COUNT(*) tæller antal rækker i gruppen.
FROM daily_rank
WHERE day_rank <=3
GROUP BY turbine_id
ORDER BY top3_count desc 


/*
// Question 9 (5 points) 

    private static List<Purchase> getAllPurchases(int page, int pageSize) {
        // work
        // select all pages but offset everytime its called
        // ... aka multiply the offset by the page number.

        System.out.println("Running all purchases for page / page size");
        String query = "select username, productname, purchasetime from purchase order by purchasetime DESC LIMIT ? OFFSET ?";
        int pageOffset = Math.multiplyExact(page - 1, pageSize);

        List<Purchase> purchases = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONNECTION);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, pageSize); 
            ps.setInt(2, pageOffset); 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String productname = rs.getString("productname");
                    String purchasetime = rs.getString("purchasetime");
                    purchases.add(new Purchase(username, productname, purchasetime));
                }
                // ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("failed execute");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("failed connection");
            e.printStackTrace();
        }

        System.out.println("puprchase list size: " + purchases.size());
        for (Purchase p : purchases){
        
            System.out.println(p);
        }
        return purchases;
    }

*/


/*
// Question 10 (5 points) 

    private static List<Purchase> getAllPurchasesOrdered(int page, int pageSize, String orderBy, boolean asc) {

        String direction = asc ? "ASC" : "DESC"; 
        String query = String.format("select username, productname, purchasetime from purchase order by %s %s LIMIT ? OFFSET ?", orderBy, direction); 

        int pageOffset = Math.multiplyExact(page - 1, pageSize);

        List<Purchase> purchases = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(CONNECTION);
                PreparedStatement ps = conn.prepareStatement(query);) {
            ps.setInt(1, pageSize);
            ps.setInt(2, pageOffset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String productname = rs.getString("productname");
                    String purchasetime = rs.getString("purchasetime");
                    purchases.add(new Purchase(username, productname, purchasetime));
                }
                // ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("failed execute");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println("failed connection");
            e.printStackTrace();
        }

        System.out.println("puprchase list size: " + purchases.size());
        for (Purchase p : purchases) {

            System.out.println(p);
        }
        return purchases;
    }
*/


-- QUESTION 11 - 10 points
-- the role hierarchy depicted in the following diagram.  
-- CREATE ROLE statements and 
CREATE ROLE lecturer; 
CREATE ROLE VP; 
CREATE ROLE Student;

-- GRANT statements & privileges
GRANT SELECT on table feedback to lecturer; 
GRANT INSERT on table feedback to lecturer; 
GRANT UPDATE on table feedback to lecturer; 

GRANT SELECT on table grades to student; 

GRANT role lecturer to role VP;
GRANT role student to role VP;

-- assign roles to users
grant role lecturer to user Martin;
grant role VP to user Anna;
grant role Student to user Mads;


-- Creating tables just to be clear. 
CREATE TABLE feedback ( 
    PRIMARY KEY feedback_id int;
)

CREATE TABLE grades ( 
    PRIMARY KEY grades_id int;
)
