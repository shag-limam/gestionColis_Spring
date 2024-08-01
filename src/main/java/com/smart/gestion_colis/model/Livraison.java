//package com.smart.gestion_colis.model;
//
//import jakarta.persistence.*;
//import java.util.Date;
//
//@Entity
//@DiscriminatorValue("LIVRAISON")
//public class Livraison extends Utilisateur {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Date datePrevue;
//    private String statut;
//
//    @OneToOne
//    @JoinColumn(name = "colis_id")
//    private Colis colis;
//
//    public Livraison(Date datePrevue, String statut) {
//        super();
//        this.datePrevue = datePrevue;
//        this.statut = statut;
//    }
//}
