package com.messageprocessorapi.KafkaMessageProcessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.messageprocessorapi"})
public class KafkaMessageProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaMessageProcessorApplication.class, args);
	}

}
