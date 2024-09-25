package com.smart.gestion_colis.dtos;

import java.util.List;

public class ItineraireDto {
    private String departurePoint;
    private String arrivalPoint;
    private List<String> waypoints;

    // Getter et Setter pour departurePoint
    public String getDeparturePoint() {
        return departurePoint;
    }

    public ItineraireDto setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
        return this;
    }

    // Getter et Setter pour arrivalPoint
    public String getArrivalPoint() {
        return arrivalPoint;
    }

    public ItineraireDto setArrivalPoint(String arrivalPoint) {
        this.arrivalPoint = arrivalPoint;
        return this;
    }

    // Getter et Setter pour waypoints
    public List<String> getWaypoints() {
        return waypoints;
    }

    public ItineraireDto setWaypoints(List<String> waypoints) {
        this.waypoints = waypoints;
        return this;
    }

    @Override
    public String toString() {
        return "ItineraireDto{" +
                "departurePoint='" + departurePoint + '\'' +
                ", arrivalPoint='" + arrivalPoint + '\'' +
                ", waypoints=" + waypoints +
                '}';
    }
}
