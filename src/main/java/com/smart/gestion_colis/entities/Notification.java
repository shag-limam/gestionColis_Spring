package com.smart.gestion_colis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String message;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt = new Date();

    @Column(nullable = false)
    private boolean read = false;  // Renommer de 'isRead' à 'read'

    // Relation avec le livreur (optionnellement, on pourrait aussi notifier un client)
    @ManyToOne
    @JoinColumn(name = "livreur_id", referencedColumnName = "id")
    private Livreur livreur;

    // Relation avec le client (en cas de notification pour le client)
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @ManyToOne
    @JsonIgnore  // Ignore la relation inverse pour éviter les cycles
    @JoinColumn(name = "admin_id", referencedColumnName = "id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "livraison_id", referencedColumnName = "id")
    private Livraison livraison;

    @ManyToOne
    @JoinColumn(name = "vehicule_id", referencedColumnName = "id")
    private Vehicule vehicule;

    public Notification(String message, Admin admin, Vehicule vehicule) {
        this.message = message;
        this.admin = admin;
        this.vehicule = vehicule;
    }

    public Notification(String message, Livreur livreur, Livraison livraison) {
        this.message = message;
        this.livreur = livreur;
        this.livraison = livraison;
    }

    public Notification(String message, Livreur livreur, Vehicule vehicule) {
        this.message = message;
        this.livreur = livreur;
        this.vehicule = vehicule;
    }


    public Notification(String message, Client client) {
        this.message = message;
        this.client = client;
    }

    public Notification(String message, Admin admin) {
        this.message = message;
        this.admin = admin;
    }
}
