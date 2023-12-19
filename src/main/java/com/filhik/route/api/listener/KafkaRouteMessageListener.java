package com.filhik.route.api.listener;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaRouteMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaRouteMessageListener.class);
    private final RouteService routeService;

    public KafkaRouteMessageListener(RouteService routeService) {
        this.routeService = routeService;
    }

    @KafkaListener(topics = "routes", groupId = "routeGroup")
    public void listen(RouteDTO routeDTO) {
        logger.info("Received route message: {}", routeDTO);
        routeService.save(routeDTO);
    }
}
