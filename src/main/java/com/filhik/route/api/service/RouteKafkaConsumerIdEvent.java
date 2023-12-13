package com.filhik.route.api.service;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.repository.EventMapStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteKafkaConsumerIdEvent {

	@Autowired
	private final EventMapStorage eventMapStorage;

	public RouteKafkaConsumerIdEvent(EventMapStorage eventMapStorage) {
		this.eventMapStorage = eventMapStorage;
	}

	public void consumeMessage(RouteDTO routeDTO) {
		eventMapStorage.addEventForRoute(routeDTO);
	}

	// Outros métodos, se necessário
}
