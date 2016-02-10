# study-kafka

## Setup Apache Kafka and Zookeeper

* Zookeeper

```bash
$ wget http//ftp.jaist.ac.jp/pub/apache/zookeeper/stable/zookeeper-3.4.6.tar.gz
$ tar xvf zookeeper-3.4.6.tar.gz
$ ln -s zookeeper-3.4.6 zookeeper
$ cd zookeeper
$ bin/zkServer.sh start
```

* Kafka

```bash
$ wget http://ftp.riken.jp/net/apache/kafka/0.9.0.0/kafka_2.11-0.9.0.0.tgz
$ tar xvf kafka_2.11-0.9.0.0.tgz
$ ln -s kafka_2.11-0.9.0.0 kafka
$ cd kafka
$ bin/kafka-server-start.sh config/server.properties
```

## Create Kafka topic

```bash
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic study-kafka
Created topic "study-kafka".
$ bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 10 --topic kafka-partitions
Created topic "kafka-partitions".
$ bin/kafka-topics.sh --list --zookeeper localhost:2181
kafka-partitions
study-kafka
```

### test

* test command

```bash
$ curl -iks http://localhost:8080/hello\?message\=horiga
$ curl -iks http://localhost:8080/hello\?message\=hoge
$ curl -iks http://localhost:8080/hello\?message\=fuga
```

```bash
[nio-8080-exec-1] org.horiga.study.kafka.HelloController   : Receiving message: horiga
[ad | producer-1] o.h.s.k.producer.KafkaMessageProducer    : Published message(horiga). topic=study-kafka, partition=0, offset=1
[nsumer-worker-0] .c.LoggingKafkaConsumerMessageDispatcher : <<consume kafka-message>>: topic=study-kafka, partition=0, offset=1, message=(key=study-kafka.1455117565063, value=horiga)
[nio-8080-exec-3] org.horiga.study.kafka.HelloController   : Receiving message: hoge
[ad | producer-1] o.h.s.k.producer.KafkaMessageProducer    : Published message(hoge). topic=study-kafka, partition=0, offset=2
[nsumer-worker-0] .c.LoggingKafkaConsumerMessageDispatcher : <<consume kafka-message>>: topic=study-kafka, partition=0, offset=2, message=(key=study-kafka.1455117585220, value=hoge)
[nio-8080-exec-5] org.horiga.study.kafka.HelloController   : Receiving message: fuga
[ad | producer-1] o.h.s.k.producer.KafkaMessageProducer    : Published message(fuga). topic=study-kafka, partition=0, offset=3
[nsumer-worker-0] .c.LoggingKafkaConsumerMessageDispatcher : <<consume kafka-message>>: topic=study-kafka, partition=0, offset=3, message=(key=study-kafka.1455117595587, value=fuga)
```
