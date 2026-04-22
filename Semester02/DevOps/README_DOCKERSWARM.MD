
# Industry Standard Architecture (3 Managers + 2 Workers) (A node gets a routingmesh when its added to a swarm)
This setup utilizes your three managers to also run application workloads to maximize resource efficiency.

Flow Description
Nodes 1, 2 & 3 (Managers + Workers): Run your Svelte (Frontend) and Java (Backend) containers as replicated services.

Node 4 (Worker): Dedicated to the Database. It is locked here using a placement constraint to ensure stable disk performance.

Node 5 (Worker): Dedicated to Grafana. It is kept isolated so heavy graphing tasks do not steal CPU from your Java backend.

Traffic Path (Step-by-step)
Internet → DigitalOcean Load Balancer (Handles HTTPS termination).

Load Balancer → Port 80 on any random Node (1, 2, or 3).

Routing Mesh → Routes traffic internally to the NGINX container (running on Node 1, 2, or 3).

NGINX → Inspects the path (/api) and forwards the packet to the correct Java container.

# #######################################################





# Docker Swarm — Noter og Ordbog

## Hvad handler det om?

Målet med øvelsen er at bygge en **pålidelig webservice** — én der ikke går ned selvom en enkelt server eller container crasher, og som kan håndtere mange brugere samtidig.

Alle workers er aktive hele tiden, load balanceren fordeler requests mellem dem, og hvis én går ned fortsætter den anden uden afbrydelse for brugeren.
Det er hele pointen med opsætningen — høj tilgængelighed og kapacitet på samme tid, ikke en reserve der venter i kulissen.

Det opnår vi med **Docker Swarm**: et system der koordinerer flere servere som én samlet enhed. En server er **manager** (hjernen der beslutter hvad der sker) og resten er **workers** (musklerne der rent faktisk kører containers).

---

## De fire trin i øvelsen

| Trin | Hvad vi gjorde |
|------|----------------|
| 1 | Oprettede tre servere (droplets) på DigitalOcean via API |
| 2 | Forbandt dem til en Swarm-klynge |
| 3 | Deployede en service på klyngen |
| 4 | Observerede self-healing og load balancing |

---

## Trin 1 — Opret serverne

### Sæt miljøvariabler (Windows)

Miljøvariabler gemmer værdier vi bruger mange gange, så vi ikke skal skrive dem igen og igen.

```bash
set DIGITAL_OCEAN_TOKEN=dit_token_her
set DIGITALOCEAN_PRIVATE_NETWORKING=true
set DROPLETS_API=https://api.digitalocean.com/v2/droplets
set BEARER_AUTH_TOKEN=Authorization: Bearer %DIGITAL_OCEAN_TOKEN%
set JSON_CONTENT=Content-Type: application/json
```

### Opret swarm-manager

```bash
curl -X POST "%DROPLETS_API%" \
  -d "{\"name\":\"swarm-manager\",\"tags\":[\"demo\"],\"size\":\"s-1vcpu-1gb\",\"image\":\"docker-20-04\",\"ssh_keys\":[\"DIT:FINGERPRINT:HER\"]}" \
  -H "%BEARER_AUTH_TOKEN%" -H "%JSON_CONTENT%"
```

**Forventet output:** Et langt JSON-svar med bl.a. `"id": 561828587` og `"status": "new"`

**Hvad sker der:** Vi sender en HTTP POST til DigitalOcean's API med en JSON-beskrivelse af den server vi vil have. `curl` er et kommandolinjeværktøj der sender HTTP-forespørgsler ligesom en browser — men viser råt JSON i stedet for HTML.

### Gem server-ID og hent IP-adresse

```bash
set SWARM_MANAGER_ID=561828587

curl -s -X GET "%DROPLETS_API%" -H "%BEARER_AUTH_TOKEN%" -H "%JSON_CONTENT%" \
  | jq -r ".droplets[] | select(.name == \"swarm-manager\") | .networks.v4[] | select(.type == \"public\") | .ip_address"
```

**Forventet output:** `138.68.59.73`

**Hvad sker der:** Vi henter en liste over alle droplets og bruger `jq` til at filtrere JSON-svaret — find droplet'en med navn "swarm-manager", gå ind i dens netværksoplysninger, og returner den offentlige IP-adresse. IP-adressen er klar først efter serveren er startet op (30-60 sekunder).

```bash
set SWARM_MANAGER_IP=138.68.59.73
```

### Opret worker1 og worker2

Samme fremgangsmåde — kør POST-kommandoen med `"name":"worker1"` og `"name":"worker2"`. Gem IP-adresserne:

```bash
set WORKER1_IP=165.227.148.49
set WORKER2_IP=159.89.16.184
```

---

## Trin 2 — Byg Swarm-klyngen

### Åbn porte på manager

Docker Swarm bruger specifikke porte til intern kommunikation mellem nodes. Vi åbner dem i firewallen:

```bash
ssh root@%SWARM_MANAGER_IP% "ufw allow 22/tcp && ufw allow 2376/tcp && ufw allow 2377/tcp && ufw allow 7946/tcp && ufw allow 7946/udp && ufw allow 4789/udp && ufw reload && ufw --force enable && systemctl restart docker"
```

**Forventet output:** En række `Rule added` linjer efterfulgt af `Firewall reloaded`

**Portenes roller:**
- `22` — SSH (vi selv kan logge ind)
- `2376` — Docker daemon
- `2377` — Swarm cluster management
- `7946` — kommunikation mellem nodes
- `4789` — overlay netværk (trafik mellem containers på tværs af servere)

### Initialiser Swarm

```bash
ssh root@%SWARM_MANAGER_IP% "docker swarm init --advertise-addr %SWARM_MANAGER_IP%"
```

**Forventet output:**
```
Swarm initialized: current node (lym841lqzelyk2zhjotae1xiw) is now a manager.

To add a worker to this swarm, run the following command:
    docker swarm join --token SWMTKN-1-1ma432... 138.68.59.73:2377
```

**Hvad sker der:** Manageren genererer et **join-token** — en hemmelig nøgle som workers skal præsentere for at få lov til at melde sig ind i klyngen. `--advertise-addr` fortæller manageren hvilken IP-adresse den skal annoncere til andre nodes.

### Hent join-token og tilslut workers

```bash
for /f %i in ('ssh root@%SWARM_MANAGER_IP% "docker swarm join-token worker -q"') do set WORKER_TOKEN=%i

ssh root@%WORKER1_IP% "docker swarm join --token %WORKER_TOKEN% %SWARM_MANAGER_IP%:2377"
ssh root@%WORKER2_IP% "docker swarm join --token %WORKER_TOKEN% %SWARM_MANAGER_IP%:2377"
```

**Forventet output (begge workers):** `This node joined a swarm as a worker.`

### Verificer klyngen

```bash
ssh root@%SWARM_MANAGER_IP% "docker node ls"
```

**Forventet output:**
```
ID                            HOSTNAME        STATUS    AVAILABILITY   MANAGER STATUS
lym841lqzelyk2zhjotae1xiw *   swarm-manager   Ready     Active         Leader
rhwqav9hvj7wcbwwb3k2oy3w6     worker1         Ready     Active
sllaf1cjva9f7qtjb0qu3qffr     worker2         Ready     Active
```

---

## Trin 3 — Deploy en service

```bash
ssh root@%SWARM_MANAGER_IP% "docker service create -p 8080:8080 --name appserver stifstof/crashserver"
```

**Forventet output:** `verify: Service converged` efter 1-2 minutter

**Hvad sker der:** Vi beder manageren downloade Docker-imaget `stifstof/crashserver` fra Docker Hub og starte det som en service. `-p 8080:8080` åbner port 8080 udadtil. Manageren beslutter selv hvilken node der kører containeren.

`crashserver` er et image lavet til øvelsen — det er en webserver der med vilje dræber sig selv 3 sekunder efter den modtager en HTTP-forespørgsel, for at simulere et server-crash.

### Åbn port 8080 i firewallen

```bash
ssh root@%SWARM_MANAGER_IP% "ufw allow 8080/tcp && ufw reload"
```

Åbn nu `http://138.68.59.73:8080/status` i browseren.

**Forventet output i browser:**
```
This page is served from: b82874ae227c
The server container will die in 3s
```

---

## Trin 4 — Skaler og observer

### Skaler til 5 kopier

```bash
ssh root@%SWARM_MANAGER_IP% "docker service scale appserver=5"
```

**Forventet output:** `verify: Service appserver converged`

**Hvad sker der:** I stedet for 1 kopi kører vi nu 5 samtidige kopier fordelt på de tre nodes. Swarm's routing mesh fordeler indkommende forespørgsler automatisk på tværs af alle kopier.

**Observation i browser:** Genindlæs `/status` flere gange hurtigt — du ser forskellige container-ID'er, fordi forskellige kopier svarer. Siden forbliver tilgængelig selvom en container dør.

### Ryd op

```bash
curl -X DELETE -H "%BEARER_AUTH_TOKEN%" -H "%JSON_CONTENT%" "https://api.digitalocean.com/v2/droplets?tag_name=demo"
```

**Hvad sker der:** Vi sletter alle droplets med tagget `demo` på én gang. Det er grunden til vi tilføjede `"tags":["demo"]` da vi oprettede dem.

---

## Ordbog

**Droplet** — DigitalOcean's navn for en virtuel server. En computer der kører i deres datacenter.

**Docker** — et system til at pakke software i isolerede enheder kaldet containers. Et Docker-image er opskriften; en container er den kørende instans.

**Docker Hub** — et offentligt registry hvor alle kan uploade og downloade Docker-images. Svarer til GitHub, men for images i stedet for kode.

**Docker Swarm** — et system der koordinerer flere Docker-servere som én samlet klynge. Håndterer deployment, skalering og self-healing automatisk.

**Manager (node)** — den server i en Swarm-klynge der fungerer som hjerne. Den beslutter hvilke nodes der kører hvilke containers, og overvåger at services er oppe.

**Worker (node)** — en server i klyngen der kører containers efter managerens instrukser. Ingen beslutningskraft.

**Service** — en beskrivelse af hvad der skal køre i Swarm'en: hvilket image, hvor mange kopier, hvilke porte. Swarm sørger for at den ønskede tilstand opretholdes.

**Container** — en kørende instans af et Docker-image. Isoleret fra resten af systemet. Kan startes og stoppes hurtigt.

**Self-healing** — Swarms evne til automatisk at genstarte containers der crasher eller stopper uventet. Ingen menneskelig indgriben nødvendig.

**Load balancing** — fordeling af indkommende forespørgsler på tværs af flere kørende kopier. Swarms routing mesh gør dette automatisk.

**Routing mesh** — Swarms mekanisme der sørger for at en forespørgsel på port 8080 på hvilken som helst node i klyngen bliver sendt videre til en container der kører servicen — uanset om containeren kører på præcis den node.

**Join-token** — en hemmelig nøgle genereret af manageren. Workers skal præsentere dette token for at få adgang til klyngen.

**`curl`** — kommandolinjeværktøj der sender HTTP-forespørgsler. Bruges her til at tale med DigitalOcean's API.

**`jq`** — kommandolinjeværktøj til at parse og filtrere JSON. Bruges til at plukke specifikke værdier (f.eks. IP-adresse) ud af API-svar.

**`ufw`** — Ubuntu's firewall (Uncomplicated Firewall). Bruges til at åbne og lukke porte på serverne.

**`ssh`** — protokol til at logge ind og køre kommandoer på en fjernserver. `ssh root@IP "kommando"` kører én kommando og logger ud igen.

**API (Application Programming Interface)** — et interface der lader programmer tale med hinanden. DigitalOcean's API lader os oprette og slette servere via HTTP-forespørgsler i stedet for at klikke i en browser.

**Miljøvariabel** — en navngivet værdi gemt i terminalsessionen. `set VAR=værdi` gemmer den; `%VAR%` bruger den. Nulstilles når terminalen lukkes.

**Fingerprint** — et kort unikt ID der identificerer en SSH-nøgle. Bruges af DigitalOcean til at afgøre hvilke nøgler der må logge ind på en ny server.

**Tag (DigitalOcean)** — en label vi sætter på droplets for at gruppere dem. Giver os mulighed for at slette alle demo-droplets på én gang med `?tag_name=demo`.

**Port** — et nummer der identificerer en specifik kommunikationskanal på en server. Port 8080 er vores webservice; port 22 er SSH; port 2377 er Swarm-management.