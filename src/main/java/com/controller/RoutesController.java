package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.RouteModel;
import com.mpa.service.RouteKafkaConsumerIdEvent;
import com.mpa.service.RouteKafkaConsumerStatusCompleted;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/routes")
public class RoutesController {

	@Autowired
	private RouteKafkaConsumerStatusCompleted rotaKafkaConsumer;

	@Autowired
	private RouteKafkaConsumerIdEvent rotaKafkaConsumerIdEvent;

	@GetMapping("/completed")
	@Operation(summary = "Get completed routes", description = "Returns a list of completed routes.")
	public ResponseEntity<List<RouteModel>> getCompletedRoutes() {
		List<RouteModel> completedRoutes = rotaKafkaConsumer.getCompletedRoutes();
		return ResponseEntity.ok(completedRoutes);
	}

	@GetMapping("/{routeId}/events")
	@Operation(summary = "Get all routes by Id Event", description = "Returns a list of Id Events.")
	public ResponseEntity<List<RouteModel>> getEventsForRoute(@PathVariable String routeId) {
	    // Obtenha todos os eventos associados ao ID especificado
	    List<RouteModel> allEventsForRoute = rotaKafkaConsumerIdEvent.getEventsForRoute(routeId);

	    if (allEventsForRoute.isEmpty()) {
	        return ResponseEntity.noContent().build(); // Retorna uma resposta vazia se não houver eventos correspondentes
	    } else {
	        return ResponseEntity.ok(allEventsForRoute); // Retorna a lista de eventos correspondentes se houver algum
	    }
	}

}