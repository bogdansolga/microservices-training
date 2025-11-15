1. [download](https://kafka.apache.org/downloads) and unzip Kafka
2. Generate a Cluster UUID: `KAFKA_CLUSTER_ID="$(bin/kafka-storage.sh random-uuid)"`
3. Format Log Directories: `bin/kafka-storage.sh format --standalone -t $KAFKA_CLUSTER_ID -c config/server.properties`
4. Start Kafka: `bin/kafka-server-start.sh config/server.properties`
5. Create the queues:
  `./bin/kafka-topics.sh --create --topic create_order --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic charge_order --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic ship_order --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic customer_created --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic customer_updated --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic order_charged --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic order_created --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic order_not_charged --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic order_shipped --bootstrap-server localhost:9092`