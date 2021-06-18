package com.example.MyRest.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "route")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Route {
    @Id
    @Column(nullable = false)
    private Long routeId;

    @Column(nullable = false)
    private LocalDate routeDate;
    @Column(nullable = false)
    private String departurePoint;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private LocalTime departureTime;
    @Column(nullable = false)
    private LocalTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "trainId")
    private Train train;

    public Long getRouteId() {
        return routeId;
    }

    public LocalDate getRouteDate() {
        return routeDate;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public String getDestination() {
        return destination;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public Train getTrain() {
        return train;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public void setRouteDate(LocalDate routeDate) {
        this.routeDate = routeDate;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}