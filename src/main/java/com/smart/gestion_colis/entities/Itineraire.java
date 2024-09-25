package com.smart.gestion_colis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "itineraires")
public class Itineraire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String departurePoint;

    @Column(nullable = false)
    private String arrivalPoint;

    @ElementCollection
    @CollectionTable(name = "waypoints", joinColumns = @JoinColumn(name = "itineraire_id"))
    @Column(name = "waypoint")
    private List<String> waypoints;

    // Relation avec Colis
    @OneToOne(mappedBy = "itineraire", cascade = CascadeType.ALL)
    private Colis colis;

    // Méthodes personnalisées

    // Obtenir l'identifiant de l'itinéraire
    public Integer getId() {
        return id;
    }

    // Définir l'identifiant de l'itinéraire
    public Itineraire setId(Integer id) {
        this.id = id;
        return this;
    }

    // Obtenir le point de départ
    public String getDeparturePoint() {
        return departurePoint;
    }

    // Définir le point de départ
    public Itineraire setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
        return this;
    }

    // Obtenir le point d'arrivée
    public String getArrivalPoint() {
        return arrivalPoint;
    }

    // Définir le point d'arrivée
    public Itineraire setArrivalPoint(String arrivalPoint) {
        this.arrivalPoint = arrivalPoint;
        return this;
    }

    // Obtenir la liste des points de passage
    public List<String> getWaypoints() {
        return waypoints;
    }

    // Définir la liste des points de passage
    public Itineraire setWaypoints(List<String> waypoints) {
        this.waypoints = waypoints;
        return this;
    }

    // Ajouter un point de passage
    public Itineraire addWaypoint(String waypoint) {
        this.waypoints.add(waypoint);
        return this;
    }

    // Supprimer un point de passage
    public Itineraire removeWaypoint(String waypoint) {
        this.waypoints.remove(waypoint);
        return this;
    }

    // Obtenir le colis associé
    public Colis getColis() {
        return colis;
    }

    // Définir le colis associé
    public Itineraire setColis(Colis colis) {
        this.colis = colis;
        return this;
    }
}
