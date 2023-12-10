package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.RouteModel;
import com.mpa.service.RouteKafkaConsumerIdEvent;
import com.mpa.service.RouteKafkaConsumerStatusCompleted;
import com.producer.KafkaProducerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/routes")
public class RoutesController {

	@Autowired
	private KafkaProducerService kafkaProducerService;

	@Autowired
	private RouteKafkaConsumerStatusCompleted kafkaConsumerService;

	@Autowired
	private RouteKafkaConsumerIdEvent rotaKafkaConsumerIdEvent;

	@GetMapping("/completed")
	@Operation(summary = "Get completed routes", description = "Returns a list of completed routes.")
	public ResponseEntity<List<RouteModel>> getCompletedRoutes() {
		List<RouteModel> completedRoutes = kafkaConsumerService.getCompletedRoutes();
		return ResponseEntity.ok(completedRoutes);
	}

    @GetMapping("/{routeId}/events")
    @Operation(summary = "Get all routes by Id Event", description = "Returns a list of Id Events.")
    public ResponseEntity<List<RouteModel>> getEventsForRoute(@PathVariable String routeId) {
        List<RouteModel> eventsForRoute = rotaKafkaConsumerIdEvent.getEventsForRouteId(routeId);
        return ResponseEntity.ok(eventsForRoute);
    }

	@PostMapping("/force-complete")
	@Operation(summary = "Post routes in kafka", description = "Returns a route with new Status.")
	public ResponseEntity<String> forceCompleteRoute(@RequestBody RouteModel routeModel) {
		kafkaProducerService.sendRouteEvent("routes", routeModel);
		return ResponseEntity.ok("Mensagem enviada para o tópico Kafka com sucesso.");
	}
}