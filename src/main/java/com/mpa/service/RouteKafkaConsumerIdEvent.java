package com.mpa.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.RouteModel;
import com.repository.EventMapStorage;

@Component
public class RouteKafkaConsumerIdEvent {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final EventMapStorage eventMapStorage;

    @Autowired
    public RouteKafkaConsumerIdEvent(EventMapStorage eventMapStorage) {
        this.eventMapStorage = eventMapStorage;
    }

    @KafkaListener(topics = "routes", groupId = "my-consumer-group-idevent")
    public void consumeMessage(ConsumerRecord<String, String> message) {
        try {
            RouteModel route = objectMapper.readValue(message.value(), RouteModel.class);
            eventMapStorage.addEventForRoute(route.getId(), route); // Supondo que RouteModel tenha getRouteId()
        } catch (Exception e) {
            e.printStackTrace(); // Tratamento de exceções
        }
    }

    // Outros métodos, se necessário
}
