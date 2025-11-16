# Kafka Setup Guide

## Prerequisites
- Java 17 or higher installed
- Command line terminal access

---

## Installation Steps

### 1. Download and Extract Kafka
[Download Kafka](https://kafka.apache.org/downloads) and extract the archive to your preferred location.

### 2. Generate Cluster UUID
```bash
KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"
```

### 3. Format Log Directories
```bash
bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties
```

### 4. Start Kafka Server
```bash
bin/kafka-server-start.sh config/server.properties
```

Kafka will start on `localhost:9092` by default.

---

## Create Required Topics

Execute the following commands to create all necessary topics for the microservices architecture:

```bash
# Order lifecycle topics
./bin/kafka-topics.sh --create --topic create_order --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic order_created --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic order_charged --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic order_not_charged --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic order_shipped --bootstrap-server localhost:9092

# Billing topics
./bin/kafka-topics.sh --create --topic charge_order --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic ship_order --bootstrap-server localhost:9092

# Customer topics
./bin/kafka-topics.sh --create --topic customer_created --bootstrap-server localhost:9092
./bin/kafka-topics.sh --create --topic customer_updated --bootstrap-server localhost:9092
```

---

## Verify Installation

### List all topics
```bash
./bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

### Monitor a topic (example: order_created)
```bash
./bin/kafka-console-consumer.sh --topic order_created --from-beginning --bootstrap-server localhost:9092
```

---

## Troubleshooting

**Port already in use:**
- Check if another Kafka instance is running: `lsof -i :9092`
- Stop existing instance or change port in `config/server.properties`

**Permission denied:**
- Ensure scripts are executable: `chmod +x bin/*.sh`

**Connection refused:**
- Verify Kafka server is running
- Check `logs/server.log` for errors
