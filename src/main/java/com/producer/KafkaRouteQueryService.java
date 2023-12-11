package com.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.RouteModel;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class KafkaRouteQueryService {

    private final BlockingQueue<RouteModel> messageQueue = new LinkedBlockingQueue<>();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "routes", groupId = "completed-consumer-group-by-id")
    public void listen(String message) {
        try {
            RouteModel routeModel = objectMapper.readValue(message, RouteModel.class);
            if (routeModel != null && routeModel.getId() != null) {
                messageQueue.add(routeModel);
            }
        } catch (Exception e) {
            // Tratar exceção
        }
    }

    public Optional<RouteModel> findRouteById(String routeId) {
        return messageQueue.stream()
                .filter(route -> route.getId() != null && route.getId().equals(routeId))
                .reduce((first, second) -> second);
    }
}
