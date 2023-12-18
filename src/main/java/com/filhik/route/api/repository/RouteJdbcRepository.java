package com.filhik.route.api.repository;

import com.filhik.route.api.model.Route;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
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
		var sql = "INSERT INTO routes(id, route_id, courier_id, origin_id, destination_id, status, event_time) "
				+ "values (:id, :routeId, :courierId, :originId, :destinationId, :status, :eventTime)";
		Map<String, Object> paramMap = Map.of("id", route.getId(), "routeId", route.getRouteId(), "courierId",
				route.getCourierId(), "originId", route.getOriginId(), "destinationId", route.getDestinationId(),
				"status", route.getStatus(), "eventTime", Timestamp.from(route.getEventTime()));
		namedJdbcTemplate.update(sql, paramMap);
		return route.getId();
	}

	@Override
	public List<Route> findByStatus(String status) {
	    var sql = "SELECT * FROM routes WHERE status = :status";
	    Map<String, Object> paramMap = Map.of("status", status);
	    return namedJdbcTemplate.query(sql, paramMap, (rs, rowNum) -> Route.create(
	        UUID.fromString(rs.getString("id")),
	        UUID.fromString(rs.getString("route_id")),
	        UUID.fromString(rs.getString("courier_id")),
	        rs.getString("status"),
	        UUID.fromString(rs.getString("origin_id")),
	        UUID.fromString(rs.getString("destination_id")),
	        rs.getTimestamp("event_time").toInstant()
	    ));
	}

}
