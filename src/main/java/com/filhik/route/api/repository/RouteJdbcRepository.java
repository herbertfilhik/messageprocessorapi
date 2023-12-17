package com.filhik.route.api.repository;

import com.filhik.route.api.model.Route;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

@Repository
public class RouteJdbcRepository implements RouteRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public RouteJdbcRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public UUID save(Route route) {
        var sql = "INSERT INTO routes(id, route_id, courier_id, origin_id, destination_id, status, event_time) " +
                "values (:id, :routeId, :courierId, :originId, :destinationId, :status, :eventTime)";
        Map<String, Object> paramMap = Map.of(
                "id", route.getId(),
                "routeId", route.getRouteId(),
                "courierId", route.getCourierId(),
                "originId", route.getOriginId(),
                "destinationId", route.getDestinationId(),
                "status", route.getStatus(),
                "eventTime", Timestamp.from(route.getEventTime())
        );
        namedJdbcTemplate.update(sql, paramMap);
        return route.getId();
    }
}
