package com.mpa.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.RouteModel;

@Service
public class RouteKafkaConsumerStatusCompleted {

   private List<RouteModel> completedRoutes = new ArrayList<>();

   @KafkaListener(topics = "routes", groupId = "my-consumer-group-completed")
   public void consumeMessage(ConsumerRecord<String, String> message) {
       // Process the Kafka message
       String messageValue = message.value();
       // Process the message, check for "COMPLETED" status
       if (messageValue.contains("\"status\":\"COMPLETED\"")) {
           RouteModel route = transformMessageToRoute(messageValue);
           completedRoutes.add(route);
       }
   }

   private RouteModel transformMessageToRoute(String message) {
	    try {
	        // Create an ObjectMapper to deserialize JSON into a Java object
	        ObjectMapper objectMapper = new ObjectMapper();

	        // Use the ObjectMapper to deserialize the JSON message into a Route object
	        RouteModel route = objectMapper.readValue(message, RouteModel.class);

	        // Return the Route instance with the deserialized data
	        return route;
	    } catch (Exception e) {
	        // Handle deserialization exceptions if they occur
	        e.printStackTrace(); // You can customize how to handle the exception, such as logging or throwing a custom exception
	        return null; // Return null or an empty Route instance in case of an error
	    }
	}

   public List<RouteModel> getCompletedRoutes() {
       return completedRoutes;
   }
}
