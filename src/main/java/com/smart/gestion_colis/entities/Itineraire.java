package com.smart.gestion_colis.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore  // Ignore les waypoints pendant la sérialisation JSON
    private List<String> waypoints;

    // Relation avec Livraisons
    @OneToOne(mappedBy = "itineraire", cascade = CascadeType.ALL)
    @JsonBackReference
    private Livraison livraison;

    // Méthodes personnalisées
    public Integer getId() {
        return id;
    }

    public Itineraire setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public Itineraire setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
        return this;
    }

    public String getArrivalPoint() {
        return arrivalPoint;
    }

    public Itineraire setArrivalPoint(String arrivalPoint) {
        this.arrivalPoint = arrivalPoint;
        return this;
    }

    public List<String> getWaypoints() {
        return waypoints;
    }

    public Itineraire setWaypoints(List<String> waypoints) {
        this.waypoints = waypoints;
        return this;
    }

    public Livraison getLivraison() {
        return livraison;
    }

    public Itineraire setLivraison(Livraison livraison) {
        this.livraison = livraison;
        return this;
    }
}
