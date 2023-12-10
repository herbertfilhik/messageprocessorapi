package com.mpa.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.model.RouteModel;
import com.producer.RouteKafkaProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteKafkaConsumerForceConclusion {
	private static final Logger logger = LoggerFactory.getLogger(RouteKafkaConsumerForceConclusion.class);

	private RouteKafkaConsumerIdEvent idEventConsumer; // Injetar a instância do RouteKafkaConsumerIdEvent
	private RouteKafkaProducer kafkaProducer; // Injetar a instância do RouteKafkaProducer

	public RouteKafkaConsumerForceConclusion(RouteKafkaConsumerIdEvent idEventConsumer,
			RouteKafkaProducer kafkaProducer) {
		this.idEventConsumer = idEventConsumer;
		this.kafkaProducer = kafkaProducer; // Injeta o RouteKafkaProducer
	}

	// Outros métodos do serviço

	public boolean isRouteAlreadyCompleted(String routeId) {
		// Obtenha todos os eventos associados ao ID especificado
		List<RouteModel> eventsForRoute = idEventConsumer.getEventsForRoute(routeId);

		// Verifique se há algum evento com o status "COMPLETED"
		return eventsForRoute.stream().anyMatch(route -> "COMPLETED".equals(route.getStatus()));
	}

	@KafkaListener(topics = "routes", groupId = "my-consumer-group-force-conclusion")
	public boolean forceCompleteRoute(String message) {
		logger.info("Recebida mensagem do tópico 'routes': {}", message);

		// Processar a mensagem como texto simples
		String routeId = message; // Suponha que a mensagem seja o ID da rota

		logger.info("Iniciando processamento para routeId: {}", routeId);

		// Verifique se a rota já estava completa antes de forçar a conclusão
		if (isRouteAlreadyCompleted(routeId)) {
			logger.info("RouteId {} já estava completa, nenhum processamento necessário.", routeId);
			return true; // A rota já estava completa
		}

		// Obtenha todos os eventos associados ao ID especificado
		List<RouteModel> eventsForRoute = idEventConsumer.getEventsForRoute(routeId);

		// Verifique se todos os status necessários estão presentes na lista de eventos
		List<String> requiredStatuses = Arrays.asList("WAITING_COURIER", "ACCEPTED", "STARTED", "COMPLETED");
		List<String> currentStatuses = eventsForRoute.stream().map(RouteModel::getStatus).collect(Collectors.toList());

		if (currentStatuses.containsAll(requiredStatuses)) {
			logger.info("RouteId {} já possui todos os status necessários, nenhum processamento necessário.", routeId);
			return true; // O registro já possui todos os status necessários, não é necessário fazer nada
		} else {
			logger.info("Adicionando status COMPLETED para routeId: {}", routeId);
			// Adicione o status COMPLETED ao registro
			RouteModel completedEvent = new RouteModel();
			completedEvent.setId(routeId);
			completedEvent.setStatus("COMPLETED");
			// Adicione outros campos necessários ao evento, se aplicável

			// Salve o evento com o status COMPLETED no seu repositório de eventos
			kafkaProducer.sendToRoutesTopic("Novo registro COMPLETED"); // Chama o RouteKafkaProducer

			logger.info("Conclusão do processamento para routeId: {}", routeId);
			return true; // Indica que o registro foi forçado a concluir com sucesso
		}
	}
}