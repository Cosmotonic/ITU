# Logging i Java – Noter

## Hvad er logging
Logging er en måde at gemme information om hvad der sker i din applikation.
Alt hvad din app skriver til stdout/stderr (`System.out.println`, `log.info` osv.) bliver fanget af Docker og kan samles op af Promtail.

---

## SLF4J + Logback (Java)

### Dependencies i pom.xml
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.9</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.14</version>
</dependency>
```
> Brug ikke `slf4j-simple` sammen med logback — de konflikter.

### Opret logger i App.java
```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
}
```

### Log-niveauer
```java
log.debug("Detaljer til udvikling: {}", værdi);
log.info("User '{}' logged in", username);
log.warn("Failed login attempt for '{}'", username);
log.error("DB query failed", e); // i stedet for e.printStackTrace()
```

- `debug` – detaljer til udvikling
- `info` – vigtige hændelser
- `warn` – noget gik lidt galt
- `error` – noget fejlede

### logback.xml
Placeres i `src/main/resources/logback.xml`.

**Log til konsol og fil:**
```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [coffeeshop] [%level] %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/coffeeshop.log</file>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [coffeeshop] [%level] %msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

Log-filen gemmes i `logs/coffeeshop.log` i projektets rodmappe. Mappen oprettes automatisk.

Pattern-felterne: `%d` = timestamp, `[coffeeshop]` = applikationsnavn, `%level` = niveau, `%msg` = besked.

---

## Loki (log-aggregering)

### Loki Logback Appender
Sender logs direkte fra Java-app til Loki i stedet for til en fil.

**Dependency:**
```xml
<dependency>
    <groupId>com.github.loki4j</groupId>
    <artifactId>loki-logback-appender</artifactId>
    <version>1.5.2</version>
</dependency>
```

**logback.xml med Loki:**
```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [coffeeshop] [%level] %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="LOKI" class="com.github.loki4j.logback.Loki4jAppender">
        <http>
            <url>http://localhost:3100/loki/api/v1/push</url>
        </http>
        <format>
            <label>
                <pattern>app=coffeeshop,level=%level</pattern>
            </label>
            <message>
                <pattern>%d{yyyy-MM-dd HH:mm:ss} [coffeeshop] [%level] %msg%n</pattern>
            </message>
        </format>
    </appender>

    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="LOKI" />
    </root>
</configuration>
```

---

## Docker + Logging Stack
 
### Hvad er hvad
| Container | Rolle |
|---|---|
| App | Skriver logs til stdout/stderr |
| Promtail | Samler logs fra Docker containers og sender til Loki |
| Loki | Gemmer logs |
| Grafana | Visualiserer logs |

### Flow
```
App containers (label: logging=promtail)
        ↓
      Promtail
        ↓
       Loki
        ↓
      Grafana
```

### Docker Compose forklaring
- `ports: 3100:3100` → venstre = din maskine, højre = inde i containeren
- `volumes: ./loki/fil:/etc/loki/fil` → venstre = din fil, højre = container fil
- `labels: logging: "promtail"` → markerer containeren så Promtail samler dens logs op
- `context: minitwit_client` → peger på mappen med Dockerfile
- Named volume (`loki-data:/loki`) → data overlever selvom containeren genstarter

### Containers kan tale sammen via netværk
```yaml
networks:
  - itu-minitwit-network
```
Alle containers på samme netværk kan referere til hinanden via containernavn, fx `http://loki:3100`.

### Promtail samler kun logs fra containers med label
```yaml
labels:
  logging: "promtail"
```

---

## pom.xml struktur
Alle `<dependency>` skal være inde i `<dependencies>` — aldrig udenfor:
```xml
<dependencies>
    <dependency>
        ...
    </dependency>
</dependencies>
```
