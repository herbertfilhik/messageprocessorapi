package com.filhik.route.api.model;

import com.filhik.route.api.listener.dto.RouteDTO;
import com.filhik.route.api.listener.dto.RouteDTO.RouteStatus;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Essa classe é exclusivamente de domínio, fornecendo um metodo estático de fábrica para criar uma instância de
 * Route a partir de um RouteDTO.
 *
 * @see RouteDTO
 */
public class Route {
    private UUID id;
    private UUID routeId;
    private UUID courierId;
    private String status;
    private UUID originId;
    private UUID destinationId;
    private Instant eventTime;

    private Route(UUID id, UUID routeId, UUID courierId, String status, UUID originId, UUID destinationId, Instant eventTime) {
        Objects.requireNonNull(routeId, "Route id must not be null");
        this.id = id;
        this.routeId = routeId;
        this.courierId = courierId;
        this.status = status;
        this.originId = originId;
        this.destinationId = destinationId;
        this.eventTime = eventTime;
    }
    
    public static Route create(UUID id, UUID routeId, UUID courierId, String status, UUID originId, UUID destinationId, Instant eventTime) {
        return new Route(id, routeId, courierId, status, originId, destinationId, eventTime);
    }
    
    public RouteDTO toDTO() {
        return new RouteDTO(
            this.id,
            this.originId,
            this.destinationId,
            this.courierId,
            RouteStatus.valueOf(this.status), // Supondo que RouteStatus é um enum
            this.eventTime
        );
    }

    public static Route fromDTO(RouteDTO routeDTO) {
        return new Route(UUID.randomUUID(),
                routeDTO.id(),
                routeDTO.courierId(),
                routeDTO.status().name(),
                routeDTO.originId(),
                routeDTO.destinationId(),
                routeDTO.eventTime()
        );
    }

    public UUID getId() {
        return id;
    }

    public UUID getRouteId() {
        return routeId;
    }

    public UUID getCourierId() {
        return courierId;
    }

    public String getStatus() {
        return status;
    }

    public UUID getOriginId() {
        return originId;
    }

    public UUID getDestinationId() {
        return destinationId;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route that = (Route) o;
        return Objects.equals(routeId, that.routeId) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, status);
    }

    @Override
    public String toString() {
        return "RouteModel{" +
                "routeId=" + routeId +
                ", status='" + status + '\'' +
                '}';
    }
}
