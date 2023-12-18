package com.filhik.route.api.service;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.model.Route;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.filhik.route.api.repository.RouteRepository;

@Component
public class RouteKafkaConsumerStatusCompleted {

    private static final Logger logger = LoggerFactory.getLogger(RouteKafkaConsumerStatusCompleted.class);

    private final RouteRepository routeRepository; // Injete o repositório necessário

    @Autowired
    public RouteKafkaConsumerStatusCompleted(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public void listen(RouteDTO routeDTO) {
        logger.info("Mensagem recebida: " + routeDTO);
        if ("COMPLETED".equals(routeDTO.status().name())) {
            // Converter o DTO em uma entidade usando o método de fábrica
            Route route = Route.fromDTO(routeDTO);
            // Salvar a entidade no banco de dados usando o repositório
            routeRepository.save(route);
        }
    }
    
    public List<RouteDTO> getCompletedRoutesFromDatabase() {
        List<Route> completedRoutes = routeRepository.findByStatus("COMPLETED");
        // Converter a lista de Route para RouteDTO
        return completedRoutes.stream()
                              .map(Route::toDTO)
                              .collect(Collectors.toList());
    }
}
