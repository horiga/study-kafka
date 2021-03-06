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

@Slf4j
@Component
public class PartitionsKafkaMessageProducer {
	@Autowired
	KafkaProducer<String, String> kafkaProducer;

	@Value("${app.kafka.topic.partitions:kafka-partitions}")
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
				log.info("Published message with partitions({}). topic={}, partition={}, offset={}", message,
						recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
			}
		}
	}

	public void publish(String message) {
		final String key = "partitions." + System.currentTimeMillis();
		kafkaProducer.send(new ProducerRecord<>(topic, key, message), new MessageCallback(message));
	}
}
