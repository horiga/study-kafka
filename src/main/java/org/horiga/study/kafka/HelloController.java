package org.horiga.study.kafka;

import org.horiga.study.kafka.producer.KafkaMessageProducer;
import org.horiga.study.kafka.producer.PartitionsKafkaMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class HelloController {

	@Autowired
	KafkaMessageProducer kafkaMessageProducer;

	@Autowired
	PartitionsKafkaMessageProducer partitionsKafkaMessageProducer;

	@RequestMapping({"", "/", "/hello"})
	public String hello(
			@RequestParam(name = "message", required = false, defaultValue = "study kafka") String message,
			@RequestParam(name = "withPartitions", required = false, defaultValue = "false") String withPartitions) {
		log.info("Receiving message: {}", message);
		if (Boolean.TRUE.toString().equalsIgnoreCase(withPartitions))
			partitionsKafkaMessageProducer.publish(message);
		else kafkaMessageProducer.publish(message);
		return "Hello, " + message;
	}
}
