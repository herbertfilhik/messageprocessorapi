package com.producer;

import org.apache.kafka.clients.producer.*;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class RouteKafkaProducer {
	private Producer<String, String> producer;

	public RouteKafkaProducer() {
		String bootstrapServers = "localhost:9092"; // Endereço do seu cluster Kafka

		Properties properties = new Properties();
		properties.put("bootstrap.servers", bootstrapServers);
		properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		this.producer = new KafkaProducer<>(properties);
	}

	public void sendToRoutesTopic(String message) {
		String topicName = "routes";

		ProducerRecord<String, String> record = new ProducerRecord<>(topicName, null, message);

		producer.send(record, new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception == null) {
					System.out.println("Mensagem enviada com sucesso para o tópico " + metadata.topic());
				} else {
					System.err.println("Erro ao enviar mensagem: " + exception.getMessage());
				}
			}
		});
	}

	public void closeProducer() {
		producer.close();
	}
}
