package com.filhik.route.api.controller;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.listener.dto.RouteDTO.RouteStatus;
import com.filhik.route.api.producer.KafkaProducerService;
import com.filhik.route.api.producer.KafkaRouteQueryService;
import com.filhik.route.api.service.RouteKafkaConsumerIdEvent;
import com.filhik.route.api.service.RouteKafkaConsumerStatusCompleted;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/routes")
public class RoutesController {


	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Autowired
	private RouteKafkaConsumerStatusCompleted kafkaConsumerService;
	
	@Autowired
	private RouteKafkaConsumerIdEvent routeKafkaConsumerIdEvent;
	
	@Autowired
	private KafkaRouteQueryService kafkaRouteQueryService;

	@GetMapping("/completed")
	@Operation(summary = "Obter rotas concluídas", description = "Retorna uma lista de rotas concluídas.")
	public ResponseEntity<List<RouteDTO>> getCompletedRoutes() {
	    List<RouteDTO> completedRoutes = kafkaConsumerService.getCompletedRoutesFromDatabase();
	    return ResponseEntity.ok(completedRoutes);
	}

	@GetMapping("/{routeId}/events")
	@Operation(summary = "Obter todos os eventos por ID de Rota", description = "Retorna uma lista de eventos para uma rota específica.")
	public ResponseEntity<List<RouteDTO>> getEventsForRoute(@PathVariable String routeId) {
	    List<RouteDTO> eventsForRoute = routeKafkaConsumerIdEvent.getEventsForRouteId(routeId);
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