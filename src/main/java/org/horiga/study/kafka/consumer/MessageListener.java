package org.horiga.study.kafka.consumer;

import kafka.message.MessageAndMetadata;

import java.util.function.Consumer;

public interface MessageListener<K, V> extends Consumer<MessageAndMetadata<K, V>> {
}
