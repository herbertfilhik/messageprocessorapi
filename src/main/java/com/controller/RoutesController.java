package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.RouteModel;
import com.mpa.service.RouteKafkaConsumerIdEvent;
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
	private RouteKafkaConsumerIdEvent rotaKafkaConsumerIdEvent;

	@Autowired
	public RoutesController(EventMapStorage eventMapStorage) {
		this.eventMapStorage = eventMapStorage;
	}

	@Autowired
	private KafkaRouteQueryService kafkaRouteQueryService;

	@GetMapping("/completed")
	@Operation(summary = "Get completed routes", description = "Returns a list of completed routes.")
	public ResponseEntity<List<RouteModel>> getCompletedRoutes() {
		List<RouteModel> completedRoutes = kafkaConsumerService.getCompletedRoutes();
		return ResponseEntity.ok(completedRoutes);
	}

	@GetMapping("/{routeId}/events")
	@Operation(summary = "Get all routes by Id Event", description = "Returns a list of Id Events.")
	public ResponseEntity<List<RouteModel>> getEventsForRoute(@PathVariable String routeId) {
		List<RouteModel> eventsForRoute = eventMapStorage.getEventsForRouteId(routeId); // Use EventMapStorage
		return ResponseEntity.ok(eventsForRoute);
	}

	@PostMapping("/force-complete")
	@Operation(summary = "Post routes in kafka", description = "Returns a route with new Status.")
	public ResponseEntity<String> forceCompleteRoute(@RequestBody RouteModel routeModel) {
		kafkaProducerService.sendRouteEvent("routes", routeModel);
		return ResponseEntity.ok("Mensagem enviada para o tópico Kafka com sucesso.");
	}

	@PostMapping("/{routeId}/force-complete")
	@Operation(summary = "Post routes in kafka completed by id", description = "Returns a route with new Status completed.")
	public ResponseEntity<String> forceCompleteRouteById(@PathVariable String routeId) {
		Optional<RouteModel> existingRoute = kafkaRouteQueryService.findRouteById(routeId);

		if (!existingRoute.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID da rota não encontrado.");
		}

		RouteModel route = existingRoute.get();
		if ("COMPLETED".equals(route.getStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rota já está completada.");
		}

	    // Convertendo o tempo atual em milissegundos para segundos
	    String eventTimeInSeconds = String.valueOf(System.currentTimeMillis() / 1000.0);

	    // Usando o novo construtor
		RouteModel completedRoute = new RouteModel(routeId, route.getOriginId(), route.getDestinationId(),
				route.getCourierId(), "COMPLETED", System.currentTimeMillis());
		kafkaProducerService.sendRouteEvent("routes", completedRoute); // Salvar a rota atualizada no Kafka

		return ResponseEntity.ok("Rota com ID " + routeId + " atualizada para COMPLETED.");
	}
}