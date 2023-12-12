package com.mpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import br.com.mentoring.route.generator.domain.dto.RouteDTO;
import com.repository.EventMapStorage;

@Component
public class RouteKafkaConsumerIdEvent {

	@Autowired
	private final EventMapStorage eventMapStorage;

	public RouteKafkaConsumerIdEvent(EventMapStorage eventMapStorage) {
		this.eventMapStorage = eventMapStorage;
	}

	@KafkaListener(topics = "routes", groupId = "my-consumer-group-idevent")
	public void consumeMessage(RouteDTO routeDTO) {
		eventMapStorage.addEventForRoute(routeDTO.id().toString(), routeDTO);
	}

	// Outros métodos, se necessário
}
