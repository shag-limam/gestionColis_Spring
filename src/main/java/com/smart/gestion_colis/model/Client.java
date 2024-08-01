//package com.smart.gestion_colis.model;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//@Entity
//@DiscriminatorValue("CLIENT")
//public class Client extends Utilisateur {
//
//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
//    private List<Colis> colis;
//
//    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
//    private List<Notification> notifications;
//
//    public Client(String nom, String email, String motDePasse) {
//        super(nom, email, motDePasse);
//    }
//}
