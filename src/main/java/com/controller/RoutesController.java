package com.controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.mentoring.route.generator.domain.dto.RouteDTO;
import br.com.mentoring.route.generator.domain.entity.RouteStatus;

import com.mpa.service.RouteKafkaConsumerStatusCompleted;
import com.producer.KafkaProducerService;
import com.producer.KafkaRouteQueryService;
import com.repository.EventMapStorage;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/routes")
public class RoutesController {

	private final EventMapStorage eventMapStorage; // Injete EventMapStorage

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Autowired
	private RouteKafkaConsumerStatusCompleted kafkaConsumerService;

	@Autowired
	private KafkaRouteQueryService kafkaRouteQueryService;

	@Autowired
	public RoutesController(EventMapStorage eventMapStorage) {
		this.eventMapStorage = eventMapStorage;
	}

	@GetMapping("/completed")
	@Operation(summary = "Get completed routes", description = "Returns a list of completed routes.")
	public ResponseEntity<List<RouteDTO>> getCompletedRoutes() {
		List<RouteDTO> completedRoutes = kafkaConsumerService.getCompletedRoutes();
		return ResponseEntity.ok(completedRoutes);
	}

	@GetMapping("/{routeId}/events")
	@Operation(summary = "Get all routes by Id Event", description = "Returns a list of Id Events.")
	public ResponseEntity<List<RouteDTO>> getEventsForRoute(@PathVariable String routeId) {
		List<RouteDTO> eventsForRoute = eventMapStorage.getEventsForRouteId(routeId); // Use EventMapStorage
		return ResponseEntity.ok(eventsForRoute);
	}

	@PostMapping("/{routeId}/force-complete")
	@Operation(summary = "Post routes in kafka completed by id", description = "Returns a route with new Status completed.")
	public ResponseEntity<String> forceCompleteRouteById(@PathVariable String routeId) {
		Optional<RouteDTO> existingRoute = kafkaRouteQueryService.findRouteById(routeId);

		if (!existingRoute.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID da rota não encontrado.");
		}

		RouteDTO route = existingRoute.get();
		if (RouteStatus.COMPLETED.equals(route.status())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rota já está completada.");
		}

		// Cria um novo objeto RouteDTO com o status atualizado
		RouteDTO completedRoute = new RouteDTO(route.id(), route.originId(), route.destinationId(), route.courierId(),
				RouteStatus.COMPLETED, Instant.now());

		kafkaProducerService.sendRouteEvent("routes", completedRoute);

		return ResponseEntity.ok("Rota com ID " + routeId + " atualizada para COMPLETED.");
	}
}