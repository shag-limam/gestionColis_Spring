//package com.smart.gestion_colis.model;
//
//import com.smart.gestion_colis.entities.Client;
//import com.smart.gestion_colis.entities.Livreur;
//import jakarta.persistence.*;
//import java.util.Date;
//
//@Entity
//@DiscriminatorValue("COLIS")
//public class Colis extends Utilisateur {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String description;
//    private String statut;
//    private Date dateExpedition;
//    private Date dateLivraison;
//
//    @ManyToOne
//    @JoinColumn(name = "livreur_id")
//    private Livreur livreur;
//
//    @ManyToOne
//    @JoinColumn(name = "client_id")
//    private Client client;
//
//    @OneToOne
//    @JoinColumn(name = "livraison_id")
//    private Livraison livraison;
//
//    @OneToOne
//    @JoinColumn(name = "itineraire_id")
//    private Itineraire itineraire;
//
//    public Colis(String description, String statut, Date dateExpedition, Date dateLivraison) {
//        super();
//        this.description = description;
//        this.statut = statut;
//        this.dateExpedition = dateExpedition;
//        this.dateLivraison = dateLivraison;
//    }
//}
