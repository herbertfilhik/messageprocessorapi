package com.filhik.route.api.repository;

import com.filhik.route.api.model.Route;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RouteRepository {
    List<Route> findByStatus(String status);    
    List<Route> findByRouteIdString(String routeId);
    UUID save(Route route);	
}