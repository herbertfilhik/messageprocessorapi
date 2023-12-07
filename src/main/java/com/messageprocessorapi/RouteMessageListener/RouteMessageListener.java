package com.messageprocessorapi.RouteMessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RouteMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RouteMessageListener.class);

    @KafkaListener(topics = "routes", groupId = "routeGroup")
    public void listen(String message) {
        logger.info("Mensagem de rota recebida: {}", message); 
    }
}
