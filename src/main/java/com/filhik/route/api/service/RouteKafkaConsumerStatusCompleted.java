package com.filhik.route.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filhik.route.api.listener.dto.RouteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RouteKafkaConsumerStatusCompleted {

    private static final Logger logger = LoggerFactory.getLogger(RouteKafkaConsumerStatusCompleted.class);

    private final List<RouteDTO> completedRoutes = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "routes", groupId = "completed-consumer-group")
    public void listen(RouteDTO routeDTO) {
        logger.info("Mensagem recebida: " + routeDTO);
        if ("COMPLETED".equals(routeDTO.status().name())) {
            completedRoutes.add(routeDTO);
        }
    }

    public List<RouteDTO> getCompletedRoutes() {
        return new ArrayList<>(completedRoutes);
    }
}
