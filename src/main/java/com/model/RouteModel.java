package com.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RouteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String origin;
    private String destination;
    private String courierId;
    private String status;
    private Long eventTime;
    private String originId;
    private String destinationId; // Add the destinationId property

    // Constructors

    public RouteModel() {
        // Default empty constructor
    }

    public RouteModel(String origin, String destination, String courierId, String status, Long eventTime, String originId, String destinationId) {
        this.origin = origin;
        this.destination = destination;
        this.courierId = courierId;
        this.status = status;
        this.eventTime = eventTime;
        this.originId = originId;
        this.destinationId = destinationId; // Initialize the destinationId property
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
    
    // ... (getters and setters methods for destinationId)

    // Other methods, if needed    
}
