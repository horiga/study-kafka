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
$ bin/kafka-topics.sh --list --zookeeper localhost:2181
study-kafka
```
