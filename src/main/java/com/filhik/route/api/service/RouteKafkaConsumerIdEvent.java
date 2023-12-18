package com.filhik.route.api.service;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.model.Route;
import com.filhik.route.api.repository.RouteRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RouteKafkaConsumerIdEvent {

    private final RouteRepository routeRepository; // Injete o repositório necessário

    @Autowired
    public RouteKafkaConsumerIdEvent(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<RouteDTO> getEventsForRouteId(String routeId) {
        // Agora você pode usar o repositório para buscar eventos diretamente do banco de dados
        return routeRepository.findByRouteIdString(routeId)
                .stream()
                .map(Route::toDTO)
                .collect(Collectors.toList());
    }

    // Outros métodos, se necessário
}
