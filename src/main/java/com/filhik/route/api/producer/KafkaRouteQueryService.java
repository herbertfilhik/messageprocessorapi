package com.filhik.route.api.producer;

import com.filhik.route.api.listener.dto.RouteDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class KafkaRouteQueryService {

    private final BlockingQueue<RouteDTO> messageQueue = new LinkedBlockingQueue<>();

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