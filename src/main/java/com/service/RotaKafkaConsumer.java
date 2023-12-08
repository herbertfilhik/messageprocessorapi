package com.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelo.Rota;

@Service
public class RotaKafkaConsumer {

   private List<Rota> rotasCompletas = new ArrayList<>();

   @KafkaListener(topics = "routes", groupId = "meu-grupo-de-consumidores")
   public void consumirMensagem(ConsumerRecord<String, String> mensagem) {
       // Processar a mensagem Kafka
       String valorDaMensagem = mensagem.value();
       // Fazer o processamento da mensagem, verificar o status "COMPLETED"
       if (valorDaMensagem.contains("\"status\":\"COMPLETED\"")) {
           Rota rota = transformarMensagemEmRota(valorDaMensagem);
           rotasCompletas.add(rota);
       }
   }

   private Rota transformarMensagemEmRota(String mensagem) {
	    try {
	        // Crie um objeto ObjectMapper para desserializar JSON em um objeto Java
	        ObjectMapper objectMapper = new ObjectMapper();

	        // Use o ObjectMapper para desserializar a mensagem JSON em um objeto Rota
	        Rota rota = objectMapper.readValue(mensagem, Rota.class);

	        // Retorne a instância de Rota com os dados desserializados
	        return rota;
	    } catch (Exception e) {
	        // Lide com exceções de desserialização, se ocorrerem
	        e.printStackTrace(); // Você pode personalizar como lidar com a exceção, como fazer log ou lançar uma exceção personalizada
	        return null; // Retorne null ou uma instância de Rota vazia em caso de erro
	    }
	}

   public List<Rota> obterRotasCompletas() {
       return rotasCompletas;
   }
}
