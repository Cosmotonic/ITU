




maintainability, dependability, 



# Make tracking point. 
<!-- 
Project files 
1. You come up with a metrics eg. "I want all users registered."  
2. In AppMetrics we create a method that has a trigger  
3. Now we find in the code where the users are difinitly added to the database and we can for sure say we want to measure them.
    Here we add: AppMetrics.registeredUsers.labels("registered").inc();
4. Now we take docker down and rebuild. 

Grafana. 
1. Edit dashboard, add visualasation. 
2. You may not see the name of your metric but you can call it from code->metric browswer and pass in the .name "minitwit_registered_users_total"
3. Now you can hit run queries but if your simulation isnt running you may not see anything. 

Start simulator 
1. Ensure you are in the right folder. cd minitwit-java/api-simulator
2. then run the following command. python minitwit_simulator.py http://localhost:7070
3. Head back to grafana and make sure you are set to look at "last 30 minutes" or so to get your data. 
-->


# Start grafana (visualisation) and prometheus (scraper)
git pull 
docker compose up 


# Connect til Minitwit DB
```bash
ssh root@din-server-ip
docker exec -it minitwit_postgres-db-1 psql -U minitwit_admin -d minitwitdb
sql
\dt          -- vis alle tabeller
\q           -- afslut
SELECT COUNT(user_id) FROM users 
```
# how many users on db. 1310 8:58, 3/6/2026

# Average amount of followers a user has.
\d follower -- Gives me a display of the columns in follower table
select who_id from follower 

// 




# Lokalt udvikling
Vagrant opretter en VM med samme miljø for alle udviklere.  
Docker kører oven på Vagrant og starter applikationen.

**Rækkefølge:**
1. `vagrant up` – starter VM'en
2. `vagrant ssh` – logger ind på VM'en
3. `docker-compose up` – starter applikationen

Applikationen kører lokalt så du kan se hvad du udvikler.

# Produktion
Digital Ocean er serveren. Docker deployer og kører applikationen der.

# Pointen
Vagrant sikrer alle udviklere arbejder under samme betingelser lokalt.  
Docker sikrer applikationen kører ens lokalt og i produktion.


# Project Commands                                  - Description
ssh -i ~/.ssh/id_ed25519  ssh root@46.101.231.61    Enter server for minitweat
ctrl + æ                                            skift til terminal

# enter external disks
ls /dev/disk/by-id/


# LINUX Commands 
## Navigation
- `pwd` – vis hvor du er
- `ls` – vis filer i mappe
- `ls -la` – vis alt inkl. skjulte filer
- `cd <mappe>` – gå ind i mappe
- `cd ..` – gå et niveau op
- `cd ~` – gå til rod-mappen

## Filer
- `cat <fil>` – vis indhold af fil
- `nano <fil>` – rediger fil
- `cp <fra> <til>` – kopier fil
- `mv <fra> <til>` – flyt/omdøb fil
- `rm <fil>` – slet fil
- `mkdir <mappe>` – opret mappe
    
## System
- `whoami` – vis hvem du er logget ind som
- `who` – vis hvem der er logget ind
- `htop` – vis ressourceforbrug
- `df -h` – vis diskplads
- `free -h` – vis RAM forbrug

## Docker
- `docker ps` – vis kørende containers
- `docker ps -a` – vis alle containers
- `docker-compose up -d` – start applikation i baggrunden
- `docker-compose down` – stop applikation
- `docker logs <container>` – vis logs fra container

## Netværk
- `curl <url>` – lav HTTP request
- `ping <ip>` – test forbindelse

## Pakker
- `apt install <pakke> -y` – installer pakke
- `apt update` – opdater pakkeliste


# SQLite Commands

## Åbn database
```bash
sqlite3 minitwit-java.db          # åbn database
```

## Dot commands (SQLite shell)
- `.tables` – vis alle tabeller
- `.schema <tabel>` – vis kolonner og typer for en tabel
- `.headers on` – vis kolonnenavne i output
- `.mode column` – pænt formateret output
- `.mode json` – output som JSON
- `.mode csv` – output som CSV
- `.databases` – vis hvilken .db fil du er tilsluttet
- `.quit` – afslut SQLite shell

## Gem output til fil
```sql
.output /sti/til/output.csv
SELECT * FROM user;
.output stdout   -- skift tilbage til terminal
```

## Eller direkte fra shell (uden at gå ind i SQLite)
```bash
sqlite3 -json minitwit-java.db "SELECT * FROM user;" > output.json
sqlite3 -csv minitwit-java.db "SELECT * FROM user;" > output.csv
```

## SQL queries
```sql
SELECT * FROM user;                                         -- vis alle rækker
SELECT email FROM user;                                     -- vis specifik kolonne
SELECT * FROM user WHERE email LIKE '%@gmail.com';          -- filtrer på gmail
SELECT * FROM user LIMIT 10;                                -- begræns antal rækker
```

## Tabel operationer
```sql
CREATE TABLE gmail_users AS                                 -- opret ny tabel fra query
    SELECT * FROM user WHERE email LIKE '%@gmail.com';

DROP TABLE gmail_users;                                     -- slet tabel
```


Upload your SSH public key in DigitalOcean>Settings>Security
