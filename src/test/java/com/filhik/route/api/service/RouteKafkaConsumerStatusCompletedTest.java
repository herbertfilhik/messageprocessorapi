package com.filhik.route.api.service;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.model.Route;
import com.filhik.route.api.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RouteKafkaConsumerStatusCompletedTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteKafkaConsumerStatusCompleted routeKafkaConsumerStatusCompleted;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListenWhenStatusIsCompleted() {
        // Crie um objeto RouteDTO com status COMPLETED para teste
        UUID id = UUID.randomUUID(); // Substitua por um UUID válido
        UUID originId = UUID.randomUUID(); // Substitua por um UUID válido
        UUID destinationId = UUID.randomUUID(); // Substitua por um UUID válido
        UUID courierId = UUID.randomUUID(); // Substitua por um UUID válido
        RouteDTO.RouteStatus status = RouteDTO.RouteStatus.COMPLETED;
        Instant eventTime = Instant.now(); // Substitua por uma instância válida de Instant

        RouteDTO completedRouteDTO = new RouteDTO(id, originId, destinationId, courierId, status, eventTime);

        // Chame o método listen
        routeKafkaConsumerStatusCompleted.listen(completedRouteDTO);

        // Verifique se o método save do routeRepository foi chamado
        verify(routeRepository, times(1)).save(any(Route.class));
    }

    @Test
    public void testListenWhenStatusIsNotCompleted() {
        // Crie um objeto RouteDTO com status diferente de COMPLETED para teste
        UUID id = UUID.randomUUID(); // Substitua por um UUID válido
        UUID originId = UUID.randomUUID(); // Substitua por um UUID válido
        UUID destinationId = UUID.randomUUID(); // Substitua por um UUID válido
        UUID courierId = UUID.randomUUID(); // Substitua por um UUID válido
        RouteDTO.RouteStatus status = RouteDTO.RouteStatus.ACCEPTED;
        Instant eventTime = Instant.now(); // Substitua por uma instância válida de Instant

        RouteDTO nonCompletedRouteDTO = new RouteDTO(id, originId, destinationId, courierId, status, eventTime);

        // Chame o método listen
        routeKafkaConsumerStatusCompleted.listen(nonCompletedRouteDTO);

        // Verifique se o método save do routeRepository não foi chamado
        verify(routeRepository, never()).save(any(Route.class));
    }


    @Test
    public void testGetCompletedRoutesFromDatabase() {
        // Crie uma lista fictícia de rotas completadas
        List<Route> completedRoutes = createSampleCompletedRoutes();

        // Configure o comportamento esperado do routeRepository.findByStatus
        when(routeRepository.findByStatus("COMPLETED")).thenReturn(completedRoutes);

        // Chame o método getCompletedRoutesFromDatabase
        List<RouteDTO> actualRouteDTOs = routeKafkaConsumerStatusCompleted.getCompletedRoutesFromDatabase();

        // Verifique se os resultados são os esperados
        assertEquals(completedRoutes.size(), actualRouteDTOs.size());
        // Adicione mais asserções conforme necessário
    }

    private List<Route> createSampleCompletedRoutes() {
        // Crie uma lista fictícia de rotas completadas para o teste
        // Substitua isto por dados de teste reais
        return Collections.emptyList();
    }
}
