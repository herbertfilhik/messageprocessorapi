package com.filhik.route.api.listener;

import com.filhik.route.api.listener.dto.RouteDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaRouteMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaRouteMessageListener.class);

    @KafkaListener(topics = "routes", groupId = "routeGroup")
    public void listen(RouteDTO routeDTO) {
        logger.info("Received route message: {}", routeDTO); 
    }
}
