package com.filhik.route.api.service;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.model.Route;
import com.filhik.route.api.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RouteKafkaConsumerIdEventTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteKafkaConsumerIdEvent routeKafkaConsumerIdEvent;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEventsForRouteId() {
        // Defina o routeId desejado para o teste
        String routeId = "your-route-id";

        // Crie uma lista fictícia de rotas com o routeId desejado
        List<Route> expectedRoutes = createSampleRoutesWithRouteId(routeId);

        // Configure o comportamento esperado do routeRepository.findByRouteIdString
        when(routeRepository.findByRouteIdString(routeId)).thenReturn(expectedRoutes);

        // Chame o método getEventsForRouteId
        List<RouteDTO> actualRouteDTOs = routeKafkaConsumerIdEvent.getEventsForRouteId(routeId);

        // Verifique se os resultados são os esperados
        assertEquals(expectedRoutes.size(), actualRouteDTOs.size());
        // Adicione mais asserções conforme necessário
    }

    private List<Route> createSampleRoutesWithRouteId(String routeId) {
        // Crie uma lista fictícia de rotas com o routeId desejado para o teste
        // Substitua isto por dados de teste reais
        return Collections.emptyList();
    }
}
