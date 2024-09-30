package com.smart.gestion_colis.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "colis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Colis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double poids;

    @Lob
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageData imageData;

    @Column(nullable = false)
    private String adresseExpediteur;

    @Column(nullable = false)
    private String adresseDestinataire;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateExpedition;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateLivraisonPrevue;

    // Association avec l'entité Client
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Association avec Livraison
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "livraison_id", referencedColumnName = "id")
    @JsonManagedReference // Point de départ de la sérialisation pour la livraison
    private Livraison livraison;

    // Méthodes supplémentaires (Getters, Setters générés par Lombok)

    public Integer getId() {
        return id;
    }

    public Colis setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Colis setDescription(String description) {
        this.description = description;
        return this;
    }

    public Double getPoids() {
        return poids;
    }

    public Colis setPoids(Double poids) {
        this.poids = poids;
        return this;
    }

    public ImageData getImageData() {
        return imageData;
    }

    public void setImageData(ImageData imageData) {
        this.imageData = imageData;
    }

    public String getAdresseExpediteur() {
        return adresseExpediteur;
    }

    public Colis setAdresseExpediteur(String adresseExpediteur) {
        this.adresseExpediteur = adresseExpediteur;
        return this;
    }

    public String getAdresseDestinataire() {
        return adresseDestinataire;
    }

    public Colis setAdresseDestinataire(String adresseDestinataire) {
        this.adresseDestinataire = adresseDestinataire;
        return this;
    }

    public Date getDateExpedition() {
        return dateExpedition;
    }

    public Colis setDateExpedition(Date dateExpedition) {
        this.dateExpedition = dateExpedition;
        return this;
    }

    public Date getDateLivraisonPrevue() {
        return dateLivraisonPrevue;
    }

    public Colis setDateLivraisonPrevue(Date dateLivraisonPrevue) {
        this.dateLivraisonPrevue = dateLivraisonPrevue;
        return this;
    }


    public Livraison getLivraison() {
        return livraison;
    }

    public Colis setLivraison(Livraison livraison) {
        this.livraison = livraison;
        return this;
    }

    public Client getClient() {
        return client;
    }

    public Colis setClient(Client client) {
        this.client = client;
        return this;
    }

    @Override
    public String toString() {
        return "Colis{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", poids=" + poids +
                ", adresseExpediteur='" + adresseExpediteur + '\'' +
                ", adresseDestinataire='" + adresseDestinataire + '\'' +
                ", dateExpedition=" + dateExpedition +
                ", dateLivraisonPrevue=" + dateLivraisonPrevue +
                ", livraison=" + (livraison != null ? livraison.getId() : "null") +
                ", client=" + (client != null ? client.getId() : "null") + // Affiche l'ID du client
                ", imageData=" + (imageData != null ? imageData.getName() : "null") +
                '}';
    }
}
