package org.horiga.study.kafka.consumer;

import kafka.message.MessageAndMetadata;

import java.util.function.Consumer;

public interface KafkaMessageListener<K, V> extends Consumer<MessageAndMetadata<K, V>> {
}
