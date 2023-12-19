package com.filhik.route.api.listener.dto;

import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RouteDTOTest {

	@Test
	void testEqualityAndHashcode() {
		UUID id = UUID.randomUUID();
		UUID originId = UUID.randomUUID();
		UUID destinationId = UUID.randomUUID();
		UUID courierId = UUID.randomUUID();
		Instant eventTime = Instant.now();

		RouteDTO dto1 = new RouteDTO(id, originId, destinationId, courierId, RouteDTO.RouteStatus.CREATED, eventTime);
		RouteDTO dto2 = new RouteDTO(id, originId, destinationId, courierId, RouteDTO.RouteStatus.CREATED, eventTime);

		assertEquals(dto1, dto2);
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	// Outros testes...
}
