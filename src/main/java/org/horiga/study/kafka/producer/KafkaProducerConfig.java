package org.horiga.study.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

	public KafkaProducer kafkaProducer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("batch.size", 16384 * 10);
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		return producer;
	}
}
