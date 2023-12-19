package com.filhik.route.api.model;

import java.time.Instant;
import java.util.UUID;
import com.filhik.route.api.listener.dto.RouteDTO;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RouteTest {

	@Test
	void testCreate() {
		UUID id = UUID.randomUUID();
		UUID routeId = UUID.randomUUID();
		UUID courierId = UUID.randomUUID();
		String status = "CREATED";
		UUID originId = UUID.randomUUID();
		UUID destinationId = UUID.randomUUID();
		Instant eventTime = Instant.now();

		Route route = Route.create(id, routeId, courierId, status, originId, destinationId, eventTime);

		assertNotNull(route);
		assertEquals(id, route.getId());
		assertEquals(routeId, route.getRouteId());
		// ... assertivas para outros campos
	}

	@Test
	void testToDTO() {
		UUID id = UUID.randomUUID();
		UUID routeId = UUID.randomUUID();
		UUID courierId = UUID.randomUUID();
		String status = "CREATED";
		UUID originId = UUID.randomUUID();
		UUID destinationId = UUID.randomUUID();
		Instant eventTime = Instant.now();

		Route route = Route.create(id, routeId, courierId, status, originId, destinationId, eventTime);
		RouteDTO routeDTO = route.toDTO();

		assertNotNull(routeDTO);
		assertEquals(route.getId(), routeDTO.id());
		// ... assertivas para outros campos
	}

	@Test
	void testFromDTO() {
		UUID id = UUID.randomUUID();
		UUID routeId = UUID.randomUUID();
		UUID courierId = UUID.randomUUID();
		String status = "CREATED";
		UUID originId = UUID.randomUUID();
		UUID destinationId = UUID.randomUUID();
		Instant eventTime = Instant.now();

		// Criar um RouteDTO
		RouteDTO routeDTO = new RouteDTO(id, originId, destinationId, courierId, RouteDTO.RouteStatus.valueOf(status),
				eventTime);

		// Criar um Route a partir do RouteDTO
		Route route = Route.fromDTO(routeDTO);

		assertNotNull(route);
		assertEquals(routeDTO.id(), route.getRouteId());
		// ... assertivas para outros campos
	}

	@Test
	void testEqualsAndHashCode() {
		UUID id = UUID.randomUUID();
		UUID routeId = UUID.randomUUID();
		UUID courierId = UUID.randomUUID();
		String status = "CREATED";
		UUID originId = UUID.randomUUID();
		UUID destinationId = UUID.randomUUID();
		Instant eventTime = Instant.now();

		Route route1 = Route.create(id, routeId, courierId, status, originId, destinationId, eventTime);
		Route route2 = Route.create(id, routeId, courierId, status, originId, destinationId, eventTime);

		assertEquals(route1, route2);
		assertEquals(route1.hashCode(), route2.hashCode());
	}

	@Test
	void testToString() {
		UUID id = UUID.randomUUID();
		UUID routeId = UUID.randomUUID();
		UUID courierId = UUID.randomUUID();
		String status = "CREATED";
		UUID originId = UUID.randomUUID();
		UUID destinationId = UUID.randomUUID();
		Instant eventTime = Instant.now();

		Route route = Route.create(id, routeId, courierId, status, originId, destinationId, eventTime);
		String expectedString = "RouteModel{routeId=" + route.getRouteId() + ", status='" + route.getStatus() + "'}";

		assertEquals(expectedString, route.toString());
	}
}
