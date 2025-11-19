# Kafka Setup Guide

## Prerequisites
- Java 17 or higher installed
- Command line terminal access

---

## Installation Steps

### 1. Download and Extract Kafka
[Download Kafka](https://kafka.apache.org/downloads) and extract the archive to your preferred location.

### 2. Generate Cluster UUID

**macOS/Linux:**
```bash
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
```

**Windows (Command Prompt):**
```cmd
bin\windows\kafka-storage.bat random-uuid
```
Save the output UUID for the next step.

**Windows (PowerShell):**
```powershell
$KAFKA_CLUSTER_ID = (.\bin\windows\kafka-storage.bat random-uuid)
```

### 3. Format Log Directories

**macOS/Linux:**
```bash
bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties
```

**Windows (Command Prompt):**
```cmd
bin\windows\kafka-storage.bat format --standalone -t <KAFKA_CLUSTER_ID> -c config\server.properties
```
Replace `<KAFKA_CLUSTER_ID>` with the UUID from step 2.

**Windows (PowerShell):**
```powershell
.\bin\windows\kafka-storage.bat format --standalone -t $KAFKA_CLUSTER_ID -c config\server.properties
```

### 4. Start Kafka Server

**macOS/Linux:**
```bash
bin/kafka-server-start.sh config/server.properties
```

**Windows (Command Prompt):**
```cmd
bin\windows\kafka-server-start.bat config\server.properties
```

**Windows (PowerShell):**
```powershell
.\bin\windows\kafka-server-start.bat config\server.properties
```

Kafka will start on `localhost:9092` by default.

---

## Create Required Topics

Execute the following commands to create all necessary topics for the microservices architecture.

> **Note:** The `.bat` commands work in both Command Prompt and PowerShell. In PowerShell, prefix with `.\` for relative paths.

### macOS/Linux

```bash
# Order lifecycle topics
./bin/kafka-topics.sh --create --topic create_order --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic order_created --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic order_charged --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic order_not_charged --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic order_shipped --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic order_processed --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic order_delivered --bootstrap-server localhost:9092

# Billing topics
./bin/kafka-topics.sh --create --topic charge_order --bootstrap-server localhost:9092

# Restaurant topics
./bin/kafka-topics.sh --create --topic process_order --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic deliver_order --bootstrap-server localhost:9092

# Customer topics
./bin/kafka-topics.sh --create --topic customer_created --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic customer_updated --bootstrap-server localhost:9092
```

### Windows (Command Prompt)

```cmd
REM Order lifecycle topics
bin\windows\kafka-topics.bat --create --topic create_order --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic order_created --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic order_charged --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic order_not_charged --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic order_shipped --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic order_processed --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic order_delivered --bootstrap-server localhost:9092

REM Billing topics
bin\windows\kafka-topics.bat --create --topic charge_order --bootstrap-server localhost:9092

REM Restaurant topics
bin\windows\kafka-topics.bat --create --topic process_order --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic deliver_order --bootstrap-server localhost:9092

REM Customer topics
bin\windows\kafka-topics.bat --create --topic customer_created --bootstrap-server localhost:9092
bin\windows\kafka-topics.bat --create --topic customer_updated --bootstrap-server localhost:9092
```

### Windows (PowerShell)

```powershell
# Order lifecycle topics
.\bin\windows\kafka-topics.bat --create --topic create_order --bootstrap-server localhost:9092
.\bin\windows\kafka-topics.bat --create --topic order_created --bootstrap-server localhost:9092
.\bin\windows\kafka-topics.bat --create --topic order_charged --bootstrap-server localhost:9092
.\bin\windows\kafka-topics.bat --create --topic order_not_charged --bootstrap-server localhost:9092
.\bin\windows\kafka-topics.bat --create --topic order_shipped --bootstrap-server localhost:9092
.\bin\windows\kafka-topics.bat --create --topic order_processed --bootstrap-server localhost:9092
.\bin\windows\kafka-topics.bat --create --topic order_delivered --bootstrap-server localhost:9092

# Billing topics
.\bin\windows\kafka-topics.bat --create --topic charge_order --bootstrap-server localhost:9092

# Restaurant topics
.\bin\windows\kafka-topics.bat --create --topic process_order --bootstrap-server localhost:9092
.\bin\windows\kafka-topics.bat --create --topic deliver_order --bootstrap-server localhost:9092

# Customer topics
.\bin\windows\kafka-topics.bat --create --topic customer_created --bootstrap-server localhost:9092
.\bin\windows\kafka-topics.bat --create --topic customer_updated --bootstrap-server localhost:9092
```

---

## Verify Installation

### List all topics

**macOS/Linux:**
```bash
./bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

**Windows (Command Prompt):**
```cmd
bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
```

**Windows (PowerShell):**
```powershell
.\bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092
```

### Monitor a topic (example: order_created)

**macOS/Linux:**
```bash
./bin/kafka-console-consumer.sh --topic order_created --from-beginning --bootstrap-server localhost:9092
```

**Windows (Command Prompt):**
```cmd
bin\windows\kafka-console-consumer.bat --topic order_created --from-beginning --bootstrap-server localhost:9092
```

**Windows (PowerShell):**
```powershell
.\bin\windows\kafka-console-consumer.bat --topic order_created --from-beginning --bootstrap-server localhost:9092
```

---

## Stop Kafka Server

**macOS/Linux:**
```bash
bin/kafka-server-stop.sh
```

**Windows (Command Prompt):**
```cmd
bin\windows\kafka-server-stop.bat
```

**Windows (PowerShell):**
```powershell
.\bin\windows\kafka-server-stop.bat
```

---

## Troubleshooting

### macOS/Linux

**Port already in use:**
- Check if another Kafka instance is running: `lsof -i :9092`
- Stop existing instance or change port in `config/server.properties`

**Permission denied:**
- Ensure scripts are executable: `chmod +x bin/*.sh`

**Connection refused:**
- Verify Kafka server is running
- Check `logs/server.log` for errors

### Windows (Command Prompt)

**Port already in use:**
- Check if another Kafka instance is running: `netstat -ano | findstr :9092`
- Stop existing instance or change port in `config\server.properties`

**Java not found:**
- Ensure `JAVA_HOME` environment variable is set
- Add Java to your PATH: `set PATH=%JAVA_HOME%\bin;%PATH%`

**Connection refused:**
- Verify Kafka server is running
- Check `logs\server.log` for errors

**Long path issues:**
- Extract Kafka to a short path (e.g., `C:\kafka`)
- Avoid paths with spaces

### Windows (PowerShell)

**Port already in use:**
- Check if another Kafka instance is running: `Get-NetTCPConnection -LocalPort 9092 -ErrorAction SilentlyContinue`
- Alternative: `netstat -ano | Select-String ":9092"`
- Stop existing instance or change port in `config\server.properties`

**Java not found:**
- Ensure `JAVA_HOME` environment variable is set
- Check with: `$env:JAVA_HOME`
- Add Java to your PATH: `$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"`

**Connection refused:**
- Verify Kafka server is running
- Check `logs\server.log` for errors

**Execution policy error:**
- If scripts won't run, check policy: `Get-ExecutionPolicy`
- To allow scripts: `Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser`

**Long path issues:**
- Extract Kafka to a short path (e.g., `C:\kafka`)
- Avoid paths with spaces
