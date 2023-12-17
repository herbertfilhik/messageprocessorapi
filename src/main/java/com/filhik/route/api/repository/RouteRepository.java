package com.filhik.route.api.repository;

import com.filhik.route.api.model.Route;

import java.util.UUID;

public interface RouteRepository {

    /**
     * It must save/persist the route data.
     *
     * @param route
     * @return persisted route id
     */
    UUID save(Route route);
}
