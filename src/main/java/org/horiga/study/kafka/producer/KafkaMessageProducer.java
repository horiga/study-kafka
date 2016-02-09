package org.horiga.study.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class KafkaMessageProducer {

	@Autowired
	KafkaProducer<String, String> kafkaProducer;

	@Value("${app.kafka.producer.numOfPartition:2}")
	private int numOfPartition;

	@Value("${app.kafka.topic:study-kafka}")
	private String topic;

	static final class MessageCallback implements Callback {

		private final String message;

		MessageCallback(String message) {
			this.message = message;
		}

		@Override
		public void onCompletion(RecordMetadata recordMetadata, Exception e) {
			if (!Objects.isNull(e)) {
				log.error("Failed to publish message. message={}", message, e);
			} else {
				log.info("Published message({}). topic={}, partition={}, offset={}", message,
						recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
			}
		}
	}

	public void publish(String message) {
		final String key = "study-kafka-" + System.currentTimeMillis();
		final String value = message;
		kafkaProducer.send(new ProducerRecord<>(topic, numOfPartition, key, value), new MessageCallback(message));
	}

}
