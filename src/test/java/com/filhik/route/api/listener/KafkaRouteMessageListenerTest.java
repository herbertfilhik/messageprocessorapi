package com.filhik.route.api.listener;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.listener.dto.RouteDTO.RouteStatus;
import com.filhik.route.api.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.util.UUID;

public class KafkaRouteMessageListenerTest {

    @Mock
    private RouteService routeService;

    @InjectMocks
    private KafkaRouteMessageListener kafkaRouteMessageListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListen() {
        // Criar um mock de RouteDTO
        RouteDTO mockRouteDTO = new RouteDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), RouteStatus.CREATED, null);

        // Chamar o método listen
        kafkaRouteMessageListener.listen(mockRouteDTO);

        // Verificar se o método save foi chamado com o RouteDTO correto
        verify(routeService).save(mockRouteDTO);
    }
}
