//package com.smart.gestion_colis.model;
//
//import jakarta.persistence.*;
//import java.util.Date;
//
//@Entity
//@DiscriminatorValue("NOTIFICATION")
//public class Notification extends Utilisateur {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String message;
//    private Date dateEnvoi;
//
//    @ManyToOne
//    @JoinColumn(name = "client_id")
//    private Client client;
//
//    @ManyToOne
//    @JoinColumn(name = "livreur_id")
//    private Livreur livreur;
//
//    public Notification(String message, Date dateEnvoi) {
//        super();
//        this.message = message;
//        this.dateEnvoi = dateEnvoi;
//    }
//}
