package com.repository;

import org.springframework.stereotype.Component;
import br.com.mentoring.route.generator.domain.dto.RouteDTO;
import java.util.*;

@Component
public class EventMapStorage {
    // Mapa para armazenar mensagens por routeId
    private Map<String, List<RouteDTO>> routeMessages = new HashMap<>();

    public void addEventForRoute(String routeId, RouteDTO route) {
        // Adicionar a mensagem ao mapa, agrupada por routeId
        routeMessages.computeIfAbsent(routeId, k -> new ArrayList<>()).add(route);
    }

    public List<RouteDTO> getEventsForRouteId(String routeId) {
        return routeMessages.getOrDefault(routeId, Collections.emptyList());
    }

    // Outros métodos e lógica, se necessário
}
