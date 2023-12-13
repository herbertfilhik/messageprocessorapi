package com.filhik.route.api.service;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.repository.EventMapStorage;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {

    private final EventMapStorage repository;

    public RouteServiceImpl(EventMapStorage repository) {
        this.repository = repository;
    }

    @Override
    public void save(RouteDTO routeDTO) {
        repository.addEventForRoute(routeDTO);
    }
}
