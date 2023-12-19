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
import org.springframework.dao.DuplicateKeyException;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class RouteServiceImplTest {

    @Mock
    private RouteRepository routeRepository;

    @InjectMocks
    private RouteServiceImpl routeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveRouteSuccess() {
        // Preparar dados de teste
        UUID id = UUID.randomUUID();
        RouteDTO routeDTO = new RouteDTO(id, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), RouteDTO.RouteStatus.COMPLETED, Instant.now());

        Route route = Route.fromDTO(routeDTO);

        // Configurar comportamento do mock
        when(routeRepository.save(route)).thenReturn(id);

        // Executar método a ser testado
        routeService.save(routeDTO);

        // Verificar interações
        verify(routeRepository).save(route);
    }

    @Test
    public void testSaveRouteWithDuplicateKeyException() {
        // Preparar dados de teste
        UUID id = UUID.randomUUID();
        RouteDTO routeDTO = new RouteDTO(id, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), RouteDTO.RouteStatus.COMPLETED, Instant.now());

        Route route = Route.fromDTO(routeDTO);

        // Configurar comportamento do mock para lançar exceção
        when(routeRepository.save(route)).thenThrow(new DuplicateKeyException("Duplicated key"));

        // Executar método a ser testado
        routeService.save(routeDTO);

        // Verificar interações
        verify(routeRepository).save(route);
    }
}
