package com.mpa.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.*;
import com.model.RouteModel;
import com.fasterxml.jackson.databind.ObjectMapper; // Import necessário para deserialização

@Service
public class RouteKafkaConsumerIdEvent {
    // Mapa para armazenar mensagens por routeId
    private Map<String, List<RouteModel>> routeMessages = new HashMap<>();

    // ObjectMapper para deserializar a mensagem JSON em RouteModel
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "routes", groupId = "my-consumer-group-idevent")
    public void consumeMessage(ConsumerRecord<String, String> message) {
        try {
            // Deserializar a mensagem JSON em RouteModel
            RouteModel route = objectMapper.readValue(message.value(), RouteModel.class);

            // Adicionar a mensagem ao mapa, agrupada por routeId
            routeMessages.computeIfAbsent(route.getId(), k -> new ArrayList<>()).add(route);
        } catch (Exception e) {
            // Tratamento de exceções de deserialização, etc.
        }
    }

    public List<RouteModel> getEventsForRouteId(String routeId) {
        return routeMessages.getOrDefault(routeId, Collections.emptyList());
    }

    // Outros métodos e lógica, se necessário
}
