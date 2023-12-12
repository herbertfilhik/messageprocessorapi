package com.producer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import br.com.mentoring.route.generator.domain.dto.RouteDTO;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class KafkaRouteQueryService {

    private final BlockingQueue<RouteDTO> messageQueue = new LinkedBlockingQueue<>();

    @KafkaListener(topics = "routes", groupId = "completed-consumer-group-by-id")
    public void listen(RouteDTO routeDTO) {
        if (routeDTO != null && routeDTO.id() != null) {
            messageQueue.add(routeDTO);
        }
    }

    public Optional<RouteDTO> findRouteById(String routeId) {
        return messageQueue.stream()
                .filter(route -> route.id() != null && route.id().toString().equals(routeId))
                .reduce((first, second) -> second);
    }
}