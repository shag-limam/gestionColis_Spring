//package com.smart.gestion_colis.model;
//
//import jakarta.persistence.*;
//import java.util.Date;
//
//@Entity
//@DiscriminatorValue("ITINERAIRE")
//public class Itineraire extends Utilisateur {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String pointsPassage;
//
//    @OneToOne
//    @JoinColumn(name = "colis_id")
//    private Colis colis;
//
//    @OneToOne
//    @JoinColumn(name = "vehicule_id")
//    private Vehicule vehicule;
//
//    public Itineraire(String pointsPassage) {
//        super();
//        this.pointsPassage = pointsPassage;
//    }
//}
