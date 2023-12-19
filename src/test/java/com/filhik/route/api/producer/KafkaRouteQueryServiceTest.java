package com.filhik.route.api.producer;

import com.filhik.route.api.model.Route;
import com.filhik.route.api.repository.RouteJdbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KafkaRouteQueryServiceTest {

    @Mock
    private RouteJdbcRepository routeJdbcRepository;

    @InjectMocks
    private KafkaRouteQueryService kafkaRouteQueryService;

    @BeforeEach
    void setUp() {
        // Configuração inicial, se necessário
    }

    @Test
    void findAllRoutesByIdShouldReturnRoutes() {
        UUID id = UUID.randomUUID();
        UUID routeId = UUID.randomUUID(); // Gera um UUID válido
        UUID courierId = UUID.randomUUID();
        String status = "CREATED";
        UUID originId = UUID.randomUUID();
        UUID destinationId = UUID.randomUUID();
        Instant eventTime = Instant.now();        

        // Usando o método estático 'create' para criar instâncias de Route
        Route route1 = Route.create(id, routeId, courierId, status, originId, destinationId, eventTime);
        Route route2 = Route.create(id, routeId, courierId, status, originId, destinationId, eventTime);
        List<Route> expectedRoutes = Arrays.asList(route1, route2);

        // Convertendo UUID para String
        String routeIdStr = routeId.toString();

        // Configura o comportamento esperado do repositório
        when(routeJdbcRepository.findByRouteIdString(routeIdStr)).thenReturn(expectedRoutes);

        // Executa o método a ser testado
        List<Route> actualRoutes = kafkaRouteQueryService.findAllRoutesById(routeIdStr);

        // Verifica se o resultado é o esperado
        assertEquals(expectedRoutes, actualRoutes);
    }

    // Aqui você pode adicionar mais testes, como testar comportamentos para IDs de rota inválidos,
    // ou quando o repositório retorna uma lista vazia, etc.
}
