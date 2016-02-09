package org.horiga.study.kafka.consumer;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;

@Configuration
public class KafkaConsumerConfig {

	@Bean
	public ConsumerConfig consumerConfig() {

		// http://kafka.apache.org/documentation.html#newconsumerconfigs
		// > 3.3.2 - New Consumer configs. since `0.9.0.0`

		Properties props = new Properties();
		//props.put("bootstrap.servers", "localhost:19092,localhost:29092,localhost:39092"); // string
		props.put("bootstrap.servers", "localhost:9092");
		//props.put("zookeeper.connect", "localhost:2181");
		props.put("group.id", "study-kafka.consumer-group"); // string
		//props.put("key.deserializer", ""); // class
		//props.put("value.deserializer", ""); // class
		props.put("heartbeat.interval.ms", "1000"); // default value is 3000
		// auto commit
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "3000"); // 3s

		props.put("client.id", "study-kafka");

		ConsumerConfig config = new ConsumerConfig(props);

		return config;
	}

	@Bean
	public ConsumerConnector consumerConnector(ConsumerConfig config) {
		kafka.javaapi.consumer.ConsumerConnector connector =
				kafka.consumer.Consumer.createJavaConsumerConnector(config);
		return connector;
	}
}
