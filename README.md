

## Create Kafka topic

```bash
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic study-kafka
Created topic "study-kafka".
$ bin/kafka-topics.sh --list --zookeeper localhost:2181
study-kafka
```
