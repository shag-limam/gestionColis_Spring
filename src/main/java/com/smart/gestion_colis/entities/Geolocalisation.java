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
@Table(name = "geolocalisation")
public class Geolocalisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String positionActuelle;

    @ElementCollection
    @CollectionTable(name = "positions_historiques", joinColumns = @JoinColumn(name = "geolocalisation_id"))
    @Column(name = "historique_position")
    private List<String> historiquePositions;

    // Relation avec Vehicule (Un véhicule est localisé via Geolocalisation)
    @OneToOne(mappedBy = "geolocalisation", cascade = CascadeType.ALL)
    private Vehicule vehicule;
}
