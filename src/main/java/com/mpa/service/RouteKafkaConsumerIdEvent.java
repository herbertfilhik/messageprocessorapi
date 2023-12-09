package com.mpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.RouteModel;

@Service
public class RouteKafkaConsumerIdEvent {

    private List<RouteModel> routeIdEvents = new ArrayList<>();
    private List<RouteModel> allEvents = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "routes", groupId = "my-consumer-group-idevent")
    public void consumeMessage(ConsumerRecord<String, String> message) {
        // Process the Kafka message
        String messageValue = message.value();

        // Extract the "id" from the message and create a RouteModel object
        RouteModel routeIdEvent = extractRouteModelFromId(messageValue);
        if (routeIdEvent != null) {
            routeIdEvents.add(routeIdEvent);
        }

        // Deserialize the JSON message into a RouteModel object
        RouteModel fullRouteEvent = extractRouteModelFromMessage(messageValue);
        if (fullRouteEvent != null) {
            allEvents.add(fullRouteEvent);
        }
    }

    private RouteModel extractRouteModelFromId(String message) {
        try {
            // Use o ObjectMapper para desserializar a mensagem JSON em um objeto RouteModel
            JsonNode jsonNode = objectMapper.readTree(message);

            // Extract the value from object "id" from JsonNode
            String id = jsonNode.get("id").asText();

            // Create a RouteModel object with the extracted "id"
            RouteModel route = new RouteModel();
            route.setId(id);

            return route;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Retorne null ou um valor padrão em caso de erro
        }
    }

    private RouteModel extractRouteModelFromMessage(String message) {
        try {
            // Use o ObjectMapper para desserializar a mensagem JSON em um objeto RouteModel
            RouteModel route = objectMapper.readValue(message, RouteModel.class);
            return route;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Retorne null ou um valor padrão em caso de erro
        }
    }

    public List<RouteModel> getRouteIdEvent() {
        return routeIdEvents;
    }

    public List<RouteModel> getAllEvents() {
        return allEvents;
    }

    public List<RouteModel> getEventsForRoute(String routeId) {
        // Filtrar os eventos que correspondem ao routeId especificado
        List<RouteModel> filteredEvents = allEvents.stream()
                .filter(route -> route.getId().equals(routeId))
                .collect(Collectors.toList());

        return filteredEvents;
    }
}
