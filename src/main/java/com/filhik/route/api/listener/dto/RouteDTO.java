package com.filhik.route.api.listener.dto;

import java.time.Instant;
import java.util.UUID;

import com.filhik.route.api.model.Route;

public record RouteDTO(
        UUID id,
        UUID originId,
        UUID destinationId,
        UUID courierId,
        RouteStatus status,
        Instant eventTime) {


    public enum RouteStatus {
        CREATED,
        WAITING_COURIER,
        ACCEPTED,
        STARTED,
        COMPLETED,
        CANCELED;
    }   
}