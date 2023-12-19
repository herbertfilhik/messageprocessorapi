package com.filhik.route.api.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.filhik.route.api.model.Route;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Importe outras classes e anotações necessárias

@ExtendWith(MockitoExtension.class)
public class RouteJdbcRepositoryTest {
	
    @BeforeEach
    void setUp() {
        repository = new RouteJdbcRepository(jdbcTemplate);
    }

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private RouteJdbcRepository repository;

    @Test
    void saveShouldInsertRouteIntoDatabase() {
        // Crie um objeto Route para teste
        Route route = Route.create(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(),
                "CREATED", UUID.randomUUID(), UUID.randomUUID(), Instant.now());

        // Chame o método save
        repository.save(route);

        // Verifique se o método jdbcTemplate.update foi chamado com os parâmetros corretos
        verify(jdbcTemplate).update(RouteJdbcRepository.SQL_INSERT, getExpectedParamMap(route));
    }

    private Map<String, Object> getExpectedParamMap(Route route) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", route.getId());
        paramMap.put("routeId", route.getRouteId());
        paramMap.put("courierId", route.getCourierId());
        paramMap.put("originId", route.getOriginId());
        paramMap.put("destinationId", route.getDestinationId());
        paramMap.put("status", route.getStatus());
        paramMap.put("eventTime", Timestamp.from(route.getEventTime()));
        return paramMap;
    }
    
    @Test
    void findByStatusShouldReturnRoutesWithMatchingStatus() {
        // Defina o status desejado
        String status = "CREATED";

        // Crie uma lista fictícia de rotas com o status desejado
        List<Route> expectedRoutes = createSampleRoutesWithStatus(status);

        // Configure o comportamento esperado do jdbcTemplate.query
        when(jdbcTemplate.query(
                RouteJdbcRepository.SQL_SELECT_BY_STATUS,
                Collections.singletonMap("status", status),
                repository.routeRowMapper))
                .thenReturn(expectedRoutes);

        // Chame o método findByStatus
        List<Route> actualRoutes = repository.findByStatus(status);

        // Verifique se o método jdbcTemplate.query foi chamado com os parâmetros corretos
        verify(jdbcTemplate).query(
                RouteJdbcRepository.SQL_SELECT_BY_STATUS,
                Collections.singletonMap("status", status),
                repository.routeRowMapper);

        // Verifique se os resultados são os esperados
        assertEquals(expectedRoutes, actualRoutes);
    }
    
    private List<Route> createSampleRoutesWithRouteId(String routeId) {
        // Crie uma lista fictícia de rotas com o routeId desejado para o teste
        // Retorna uma lista vazia para este exemplo
        return Collections.emptyList();
    }

    private List<Route> createSampleRoutesWithStatus(String status) {
        // Crie uma lista fictícia de rotas com o status desejado para o teste
        // Retorna uma lista vazia para este exemplo
        return Collections.emptyList();
    }
}
