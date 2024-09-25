package com.smart.gestion_colis.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vehicules")
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String marque;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String immatriculation;

    // Proper bidirectional mapping
    @OneToOne(mappedBy = "vehicule")
    private Livreur livreur;  // Refers to the 'vehicule' field in Livreur

    // Relation with Geolocalisation
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geolocalisation_id", referencedColumnName = "id")
    private Geolocalisation geolocalisation;
}
