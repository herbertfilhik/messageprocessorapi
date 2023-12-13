package com.filhik.route.api.producer;

import com.filhik.route.api.listener.dto.RouteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, RouteDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, RouteDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void sendRouteEvent(String topic, RouteDTO event) {
        kafkaTemplate.send(topic, event);
    }    
}
