package com.mpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.RouteModel;

import java.util.ArrayList;
import java.util.List;

@Component
public class RouteKafkaConsumerStatusCompleted {

    private final List<RouteModel> completedRoutes = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "routes", groupId = "completed-consumer-group")
    public void listen(String message) {
        try {
            RouteModel route = objectMapper.readValue(message, RouteModel.class);
            if ("COMPLETED".equals(route.getStatus())) {
                completedRoutes.add(route);
            }
        } catch (Exception e) {
            // Tratar exceção
        }
    }

    public List<RouteModel> getCompletedRoutes() {
        return new ArrayList<>(completedRoutes);
    }
}
