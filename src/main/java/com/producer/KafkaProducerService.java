package com.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mentoring.route.generator.domain.dto.RouteDTO;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, RouteDTO> kafkaTemplate;

    @Transactional
    public void sendRouteEvent(String topic, RouteDTO event) {
        kafkaTemplate.send(topic, event);
    }    
}
