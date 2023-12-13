package com.filhik.route.api.repository;

import com.filhik.route.api.listener.dto.RouteDTO;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EventMapStorage {
    // Mapa para armazenar mensagens por routeId
    private Map<String, List<RouteDTO>> routeMessages = new HashMap<>();

    public void addEventForRoute(RouteDTO route) {
        var routeId = route.id().toString();
        // Adicionar a mensagem ao mapa, agrupada por routeId
        routeMessages.computeIfAbsent(routeId, k -> new ArrayList<>()).add(route);
    }

    public List<RouteDTO> getEventsForRouteId(String routeId) {
        return routeMessages.getOrDefault(routeId, Collections.emptyList());
    }

    // Outros métodos e lógica, se necessário
}
