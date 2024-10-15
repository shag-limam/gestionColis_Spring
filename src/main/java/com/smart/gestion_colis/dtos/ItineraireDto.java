package com.smart.gestion_colis.dtos;

import java.util.List;

public class ItineraireDto {
    private Long id;
    private String departurePoint;
    private String arrivalPoint;
    private List<String> waypoints;

    // Getters and Setters
    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getArrivalPoint() {
        return arrivalPoint;
    }

    public void setArrivalPoint(String arrivalPoint) {
        this.arrivalPoint = arrivalPoint;
    }

    public List<String> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<String> waypoints) {
        this.waypoints = waypoints;
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
