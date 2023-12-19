package com.filhik.route.api.producer;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.filhik.route.api.repository.RouteJdbcRepository;
import com.filhik.route.api.model.Route;

@Service
public class KafkaRouteQueryService {

    @Autowired
    private RouteJdbcRepository routeJdbcRepository;

    public List<Route> findAllRoutesById(String routeId) {
        // Busca as rotas no banco de dados
        return routeJdbcRepository.findByRouteIdString(routeId);
    }
}
