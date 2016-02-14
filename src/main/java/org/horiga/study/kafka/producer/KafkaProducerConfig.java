package org.horiga.study.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

	@Bean
	public KafkaProducer kafkaProducer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:19092,localhost:29092,localhost:39092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("batch.size", 16384 * 10);
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		return producer;
	}
}
