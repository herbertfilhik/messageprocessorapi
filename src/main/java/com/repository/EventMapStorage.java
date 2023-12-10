package com.repository;

import org.springframework.stereotype.Component;
import com.model.RouteModel;
import java.util.*;

@Component
public class EventMapStorage {
    // Mapa para armazenar mensagens por routeId
    private Map<String, List<RouteModel>> routeMessages = new HashMap<>();

    public void addEventForRoute(String routeId, RouteModel route) {
        // Adicionar a mensagem ao mapa, agrupada por routeId
        routeMessages.computeIfAbsent(routeId, k -> new ArrayList<>()).add(route);
    }

    public List<RouteModel> getEventsForRouteId(String routeId) {
        return routeMessages.getOrDefault(routeId, Collections.emptyList());
    }

    // Outros métodos e lógica, se necessário
}
