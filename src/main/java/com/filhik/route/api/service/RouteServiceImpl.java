package com.filhik.route.api.service;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.model.Route;
import com.filhik.route.api.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {

    private static final Logger log = LoggerFactory.getLogger(RouteServiceImpl.class);

    private final RouteRepository repository;

    public RouteServiceImpl(RouteRepository repository) {
        this.repository = repository;
    }

    /**
     * The entrypoint to persist the route events. This method should guarantee that an event already persisted
     * could not be overrided by an <b>oldest event</b> of a <b>previous status</b>.
     *
     * @param routeDTO
     */
    @Override
    public void save(RouteDTO routeDTO) {
        var route = Route.fromDTO(routeDTO);
        log.info("Saving new event: {}", route);

        try {
            // TODO avoid old event override
            repository.save(route);
        } catch (DuplicateKeyException e) {
            log.warn("Duplicated key: {}", e.getMessage());
        }
    }
}
