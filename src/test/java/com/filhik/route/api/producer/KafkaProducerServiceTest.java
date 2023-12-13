package com.filhik.route.api.producer;

import com.filhik.route.api.listener.dto.RouteDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceTest {

    @Mock
    private KafkaTemplate<String, RouteDTO> kafkaTemplate;

    @InjectMocks
    private KafkaProducerService service;

    @Test
    void shouldSendRoute() {
        when(kafkaTemplate.send(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenReturn(null);

        service.sendRouteEvent("topic", getRouteDTO());

        verify(kafkaTemplate).send(ArgumentMatchers.anyString(), ArgumentMatchers.any());
    }

    @Test
    void shouldFailWhenCallSendRoute() {
        when(kafkaTemplate.send(ArgumentMatchers.anyString(), ArgumentMatchers.any())).thenThrow(RuntimeException.class);

        assertThrowsExactly(RuntimeException.class, () -> service.sendRouteEvent("topic", getRouteDTO()));
    }

    private static RouteDTO getRouteDTO() {
        return new RouteDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), RouteDTO.RouteStatus.COMPLETED, Instant.now());
    }
}
