package org.horiga.study.kafka;

import org.horiga.study.kafka.producer.KafkaMessageProducer;
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

	@RequestMapping({"", "/", "/hello"})
	public String hello(
			@RequestParam(name = "message", required = false, defaultValue = "study kafka") String message) {
		log.info("Receiving message: {}", message);
		kafkaMessageProducer.publish(message);
		return "Hello, " + message;
	}
}
