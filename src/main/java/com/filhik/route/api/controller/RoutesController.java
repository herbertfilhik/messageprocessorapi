package com.filhik.route.api.controller;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.listener.dto.RouteDTO.RouteStatus;
import com.filhik.route.api.model.Route;
import com.filhik.route.api.producer.KafkaProducerService;
import com.filhik.route.api.producer.KafkaRouteQueryService;
import com.filhik.route.api.repository.RouteJdbcRepository;
import com.filhik.route.api.service.RouteKafkaConsumerIdEvent;
import com.filhik.route.api.service.RouteKafkaConsumerStatusCompleted;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/routes")
public class RoutesController {

	@Autowired
	private RouteJdbcRepository routeJdbcRepository; // Adicionado

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
	@Operation(summary = "Post routes in kafka completed by id", description = "Retorna uma rota com novo status COMPLETED.")
	public ResponseEntity<String> forceCompleteRouteById(@PathVariable String routeId) {
	    List<Route> routesWithId = kafkaRouteQueryService.findAllRoutesById(routeId);

	    if (routesWithId.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID da rota não encontrado.");
	    }

	    boolean isCompleted = routesWithId.stream()
	                                       .anyMatch(route -> RouteStatus.COMPLETED.equals(route.getStatus()));

	    if (isCompleted) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rota já está completada.");
	    }

	    // Encontra o último registro que não seja COMPLETED
	    Route lastNonCompletedRoute = routesWithId.stream()
	                                              .filter(route -> !RouteStatus.COMPLETED.equals(route.getStatus()))
	                                              .max(Comparator.comparing(Route::getEventTime))
	                                              .orElse(null);

	    if (lastNonCompletedRoute == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não foi possível encontrar um status válido para replicar.");
	    }
	    
	    // Verifica se já existe um registro COMPLETED para este routeId
	    boolean alreadyHasCompleted = routeJdbcRepository.findByRouteIdAndStatus(routeId, RouteStatus.COMPLETED.name());
	    if (alreadyHasCompleted) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Um registro COMPLETED já existe para este routeId.");
	    }

	    // Cria um novo objeto Route com o status COMPLETED
	    Route newCompletedRoute = Route.create(
	        UUID.randomUUID(), // Novo ID para o registro
	        UUID.fromString(routeId), // RouteID
	        lastNonCompletedRoute.getCourierId(), // CourierID do último registro
	        RouteStatus.COMPLETED.name(), // Status COMPLETED
	        lastNonCompletedRoute.getOriginId(), // OriginID do último registro
	        lastNonCompletedRoute.getDestinationId(), // DestinationID do último registro
	        Instant.now() // EventTime atual
	    );

	    routeJdbcRepository.save(newCompletedRoute);

	    return ResponseEntity.ok("Nova rota com ID " + routeId + " inserida e marcada como COMPLETED.");
	}

}