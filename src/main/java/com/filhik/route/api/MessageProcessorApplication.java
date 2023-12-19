package com.filhik.route.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.filhik.route.api")
public class MessageProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageProcessorApplication.class, args);
	}
}
