package com.message.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.listener"})
public class MessageProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageProcessorApplication.class, args);
	}

}
