- [download](https://kafka.apache.org/downloads) Kafka

- start Kafka:
  `bin/zookeeper-server-start.sh config/zookeeper.properties`
  `bin/kafka-server-start.sh config/server.properties`

- create the queues:
  `./bin/kafka-topics.sh --create --topic create_order --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic charge_order --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic ship_order --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic customer_created --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic customer_updated --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic order_charged --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic order_created --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic order_not_charged --bootstrap-server localhost:9092`
  `./bin/kafka-topics.sh --create --topic order_shipped --bootstrap-server localhost:9092`