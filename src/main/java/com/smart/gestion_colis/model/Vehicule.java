//package com.smart.gestion_colis.model;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//@DiscriminatorValue("VEHICULE")
//public class Vehicule extends Utilisateur {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String marque;
//    private String modele;
//    private String immatriculation;
//
//    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
//    private List<Livraison> livraisons;
//
//    @OneToMany(mappedBy = "vehicule", cascade = CascadeType.ALL)
//    private List<Itineraire> itineraires;
//
//    public Vehicule(String marque, String modele, String immatriculation) {
//        super();
//        this.marque = marque;
//        this.modele = modele;
//        this.immatriculation = immatriculation;
//    }
//}
