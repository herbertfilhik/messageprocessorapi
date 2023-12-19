package com.filhik.route.api.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.listener.dto.RouteDTO.RouteStatus;
import com.filhik.route.api.model.Route;
import com.filhik.route.api.producer.KafkaProducerService;
import com.filhik.route.api.producer.KafkaRouteQueryService;
import com.filhik.route.api.repository.RouteJdbcRepository;
import com.filhik.route.api.service.RouteKafkaConsumerIdEvent;
import com.filhik.route.api.service.RouteKafkaConsumerStatusCompleted;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RoutesControllerTest {

	@Mock
	private RouteJdbcRepository routeJdbcRepository;

	@Mock
	private KafkaProducerService kafkaProducerService;

	@Mock
	private RouteKafkaConsumerStatusCompleted kafkaConsumerService;

	@Mock
	private RouteKafkaConsumerIdEvent routeKafkaConsumerIdEvent;

	@Mock
	private KafkaRouteQueryService kafkaRouteQueryService;

	@InjectMocks
	private RoutesController routesController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetCompletedRoutes() {
		// Mock the response
		when(kafkaConsumerService.getCompletedRoutesFromDatabase()).thenReturn(Collections.emptyList());

		// Call the method
		ResponseEntity<List<RouteDTO>> response = routesController.getCompletedRoutes();

		// Assert the response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isEmpty());
	}

	@Test
	void testGetEventsForRoute_RouteExists() {
	    // Criar uma lista de RouteDTO para usar como retorno do mock
	    List<RouteDTO> mockRouteDtoList = Arrays.asList(
    	    new RouteDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), RouteStatus.CREATED, null),
    	    new RouteDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), RouteStatus.WAITING_COURIER, null),
    	    new RouteDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), RouteStatus.ACCEPTED, null),
    	    new RouteDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), RouteStatus.STARTED, null),
    	    new RouteDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), RouteStatus.COMPLETED, null),
    	    new RouteDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), RouteStatus.CANCELED, null)    	      	    
    	    // Adicione mais RouteDTOs conforme necessário
	    );

	    // Configurar os mocks
	    when(routeKafkaConsumerIdEvent.getEventsForRouteId(anyString())).thenReturn(mockRouteDtoList);

	    // Chamar o método
	    ResponseEntity<List<RouteDTO>> response = routesController.getEventsForRoute("routeIdExistente");

	    // Asserts
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	    assertFalse(response.getBody().isEmpty());
	}


	@Test
	void testGetEventsForRoute_RouteDoesNotExist() {
		// Configurar os mocks
		when(routeKafkaConsumerIdEvent.getEventsForRouteId(anyString())).thenReturn(Collections.emptyList());

		// Chamar o método
		ResponseEntity<List<RouteDTO>> response = routesController.getEventsForRoute("routeIdInexistente");

		// Asserts
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().isEmpty());
	}

	@Test
	void testForceCompleteRouteById_RouteNotFound() {
	    // Configurar os mocks
	    when(kafkaRouteQueryService.findAllRoutesById(anyString())).thenReturn(Collections.emptyList());

	    // Chamar o método
	    ResponseEntity<String> response = routesController.forceCompleteRouteById("routeIdInexistente");

	    // Asserts
	    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testForceCompleteRouteById_RouteAlreadyCompleted() {
	    UUID routeIdCompletado = UUID.randomUUID();

	    // Criar uma lista de rotas que simula uma rota existente e já completada
	    List<Route> mockRoutes = Arrays.asList(
	        Route.create(routeIdCompletado, UUID.randomUUID(), UUID.randomUUID(), RouteStatus.COMPLETED.name(), UUID.randomUUID(), UUID.randomUUID(), Instant.now())
	    );

	    // Configurar os mocks
	    when(kafkaRouteQueryService.findAllRoutesById(eq(routeIdCompletado.toString()))).thenReturn(mockRoutes);
	    when(routeJdbcRepository.findByRouteIdAndStatus(eq(routeIdCompletado.toString()), eq(RouteStatus.COMPLETED.name()))).thenReturn(true);

	    // Chamar o método
	    ResponseEntity<String> response = routesController.forceCompleteRouteById(routeIdCompletado.toString());

	    // Asserts
	    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testForceCompleteRouteById_Success() {
	    // Gerar um UUID válido para o teste
	    UUID routeIdValido = UUID.randomUUID();

	    // Criar uma lista de rotas que simula uma rota existente que pode ser completada
	    List<Route> mockRoutes = Arrays.asList(
	        Route.create(UUID.randomUUID(), routeIdValido, UUID.randomUUID(), "COMPLETED", UUID.randomUUID(), UUID.randomUUID(), Instant.now())
	    );

	    // Configurar os mocks
	    when(kafkaRouteQueryService.findAllRoutesById(eq(routeIdValido.toString()))).thenReturn(mockRoutes);
	    when(routeJdbcRepository.findByRouteIdAndStatus(eq(routeIdValido.toString()), eq(RouteStatus.COMPLETED.name()))).thenReturn(false);

	    // Chamar o método
	    ResponseEntity<String> response = routesController.forceCompleteRouteById(routeIdValido.toString());

	    // Asserts
	    assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	// Outros testes...
}
