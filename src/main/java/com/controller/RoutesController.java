package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.RouteModel;
import com.mpa.service.RouteKafkaConsumerForceConclusion;
import com.mpa.service.RouteKafkaConsumerIdEvent;
import com.mpa.service.RouteKafkaConsumerStatusCompleted;

import io.swagger.v3.oas.annotations.Operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/routes")
public class RoutesController {
	private static final Logger logger = LoggerFactory.getLogger(RoutesController.class);
	
	@Autowired
	private RouteKafkaConsumerStatusCompleted rotaKafkaConsumer;

	@Autowired
	private RouteKafkaConsumerIdEvent rotaKafkaConsumerIdEvent;
	
	@Autowired
	private RouteKafkaConsumerForceConclusion rotaKafkaConsumeForceConclusion;

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
	
	@PostMapping("/force-complete/{routeId}")
	@Operation(summary = "Forçar a conclusão de uma rota pendente", description = "Força a conclusão de uma rota pendente com base no ID da rota.")
	public ResponseEntity<String> forceCompleteRoute(@PathVariable String routeId) {
	    try {
	        boolean routeConcluidaComSucesso = rotaKafkaConsumeForceConclusion.forceCompleteRoute(routeId);

	        if (routeConcluidaComSucesso) {
	            // Verifique se a rota já estava completa
	            if (rotaKafkaConsumeForceConclusion.isRouteAlreadyCompleted(routeId)) {
	                return ResponseEntity.ok("Rota já estava completa.");
	            } else {
	                return ResponseEntity.ok("Rota foi forçada a concluir com sucesso.");
	            }
	        } else {
	            return ResponseEntity.badRequest().body("Não foi possível forçar a conclusão da rota.");
	        }
	    } catch (Exception ex) {
	        // Captura exceções gerais e registra informações de erro
	        logger.error("Erro ao forçar a conclusão da rota com ID: {}", routeId, ex);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
	    }
	}

}