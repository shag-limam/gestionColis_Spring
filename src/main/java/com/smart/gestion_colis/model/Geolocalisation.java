//package com.smart.gestion_colis.model;
//
//import jakarta.persistence.*;
//import java.util.List;
//
//@Entity
//@DiscriminatorValue("GEOLOCALISATION")
//public class Geolocalisation extends Utilisateur {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String positionActuelle;
//    private List<String> historiquePositions;
//
//    @OneToOne
//    @JoinColumn(name = "vehicule_id")
//    private Vehicule vehicule;
//
//    public Geolocalisation(String positionActuelle, List<String> historiquePositions) {
//        super();
//        this.positionActuelle = positionActuelle;
//        this.historiquePositions = historiquePositions;
//    }
//}
