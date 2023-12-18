package com.filhik.route.api.repository;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.filhik.route.api.model.Route;

@Repository
public class RouteJdbcRepository implements RouteRepository {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    // Constantes para consultas SQL
    private static final String SQL_INSERT = "INSERT INTO routes(id, route_id, courier_id, origin_id, destination_id, status, event_time) "
            + "values (:id, :routeId, :courierId, :originId, :destinationId, :status, :eventTime)";

    private static final String SQL_SELECT_BY_STATUS = "SELECT * FROM routes WHERE status = :status";
    
    private static final String SQL_SELECT_BY_ROUTE_ID_STRING = "SELECT * FROM routes WHERE route_id = :routeId";

    public RouteJdbcRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public UUID save(Route route) {
        Map<String, Object> paramMap = Map.of(
                "id", route.getId(),
                "routeId", route.getRouteId(),
                "courierId", route.getCourierId(),
                "originId", route.getOriginId(),
                "destinationId", route.getDestinationId(),
                "status", route.getStatus(),
                "eventTime", Timestamp.from(route.getEventTime())
        );
        namedJdbcTemplate.update(SQL_INSERT, paramMap);
        return route.getId();
    }

    @Override
    public List<Route> findByStatus(String status) {
        Map<String, Object> paramMap = Map.of("status", status);
        return namedJdbcTemplate.query(SQL_SELECT_BY_STATUS, paramMap, routeRowMapper);
    }

    // Mapeamento do resultado da consulta para um objeto Route
    private final RowMapper<Route> routeRowMapper = (rs, rowNum) -> Route.create(
            UUID.fromString(rs.getString("id")),
            UUID.fromString(rs.getString("route_id")),
            UUID.fromString(rs.getString("courier_id")),
            rs.getString("status"),
            UUID.fromString(rs.getString("origin_id")),
            UUID.fromString(rs.getString("destination_id")),
            rs.getTimestamp("event_time").toInstant()
    );

    @Override
    public List<Route> findByRouteIdString(String routeId) {
        UUID routeUuid;
        try {
            routeUuid = UUID.fromString(routeId); // Tente converter a string para UUID
        } catch (IllegalArgumentException e) {
            // Lidar com a conversão mal sucedida, por exemplo, retornar uma lista vazia ou lançar uma exceção
            return Collections.emptyList(); // Retorna uma lista vazia em caso de conversão mal sucedida
        }

        Map<String, Object> paramMap = Map.of("routeId", routeUuid);
        return namedJdbcTemplate.query(SQL_SELECT_BY_ROUTE_ID_STRING, paramMap, routeRowMapper);
    }
}
