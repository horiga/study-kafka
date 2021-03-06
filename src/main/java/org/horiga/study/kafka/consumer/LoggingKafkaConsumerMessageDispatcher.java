package org.horiga.study.kafka.consumer;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import kafka.consumer.KafkaStream;
import kafka.consumer.TopicFilter;
import kafka.consumer.Whitelist;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import kafka.serializer.Decoder;
import kafka.serializer.StringDecoder;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingKafkaConsumerMessageDispatcher {

	private ExecutorService worker;

	@Autowired
	@Qualifier("consumer.study-kafka")
	private ConsumerConnector consumerConnector;

	@Value("${app.kafka.consumer.concurrency.num:10}")
	private int concurrency;

	@PostConstruct
	public synchronized void init() {
		if (worker != null) {
			return;
		}

		worker = Executors.newFixedThreadPool(concurrency,
			new ThreadFactoryBuilder().setNameFormat("logback-consumer-worker-%d").build());

		final KafkaMessageListener<String, String> loggingMessageListener = message ->
				log.info("<<consume kafka-message>>: topic={}, partition={}, offset={}, message=(key={}, value={})",
					message.topic(), message.partition(), message.offset(), message.key(), message.message());

		final TopicFilter filter = new Whitelist("replication-topic*");
		final Decoder<String> keyDecoder = new StringDecoder(null);
		final Decoder<String> valueDecoder = new StringDecoder(null);

		final List<KafkaStream<String, String>> streams =
				consumerConnector.createMessageStreamsByFilter(filter, concurrency, keyDecoder, valueDecoder);

		streams.stream().forEach(stream -> worker.execute(() -> {
			for (MessageAndMetadata<String, String> message : stream) {
				try {
					if (Objects.isNull(message)) {
						log.info("consumed message was (null).");
						continue;
					}
					loggingMessageListener.accept(message);
				} catch (Exception e) {
					log.error("Failed to consume message", e);
				}
			}
		}));
	}

	@PreDestroy
	public void shutdown() {
		MoreExecutors.shutdownAndAwaitTermination(worker, 5000, TimeUnit.MILLISECONDS);
		consumerConnector.shutdown();
	}
}
