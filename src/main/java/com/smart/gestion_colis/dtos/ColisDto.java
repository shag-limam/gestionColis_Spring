package com.smart.gestion_colis.dtos;

import java.util.Date;

public class ColisDto {

    private String description;
    private Double poids;
    private String adresseExpediteur;
    private String adresseDestinataire;
    private Date dateExpedition;
    private Date dateLivraisonPrevue;
    private Long itineraireId;  // ID pour l'itinéraire lié
    private Long livraisonId;    // ID pour la livraison liée
    private Long clientId;       // ID du client associé
    // On ne stocke pas directement MultipartFile ici, c'est géré dans le contrôleur

    // Getter et Setter pour description
    public String getDescription() {
        return description;
    }

    public ColisDto setDescription(String description) {
        this.description = description;
        return this;
    }

    // Getter et Setter pour poids
    public Double getPoids() {
        return poids;
    }

    public ColisDto setPoids(Double poids) {
        this.poids = poids;
        return this;
    }

    // Getter et Setter pour adresseExpediteur
    public String getAdresseExpediteur() {
        return adresseExpediteur;
    }

    public ColisDto setAdresseExpediteur(String adresseExpediteur) {
        this.adresseExpediteur = adresseExpediteur;
        return this;
    }

    // Getter et Setter pour adresseDestinataire
    public String getAdresseDestinataire() {
        return adresseDestinataire;
    }

    public ColisDto setAdresseDestinataire(String adresseDestinataire) {
        this.adresseDestinataire = adresseDestinataire;
        return this;
    }

    // Getter et Setter pour dateExpedition
    public Date getDateExpedition() {
        return dateExpedition;
    }

    public ColisDto setDateExpedition(Date dateExpedition) {
        this.dateExpedition = dateExpedition;
        return this;
    }

    // Getter et Setter pour dateLivraisonPrevue
    public Date getDateLivraisonPrevue() {
        return dateLivraisonPrevue;
    }

    public ColisDto setDateLivraisonPrevue(Date dateLivraisonPrevue) {
        this.dateLivraisonPrevue = dateLivraisonPrevue;
        return this;
    }

    // Getter et Setter pour itineraireId
    public Long getItineraireId() {
        return itineraireId;
    }

    public ColisDto setItineraireId(Long itineraireId) {
        this.itineraireId = itineraireId;
        return this;
    }

    // Getter et Setter pour livraisonId
    public Long getLivraisonId() {
        return livraisonId;
    }

    public ColisDto setLivraisonId(Long livraisonId) {
        this.livraisonId = livraisonId;
        return this;
    }

    // Getter et Setter pour clientId (nouveau champ)
    public Long getClientId() {
        return clientId;
    }

    public ColisDto setClientId(Long clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public String toString() {
        return "ColisDto{" +
                "description='" + description + '\'' +
                ", poids=" + poids +
                ", adresseExpediteur='" + adresseExpediteur + '\'' +
                ", adresseDestinataire='" + adresseDestinataire + '\'' +
                ", dateExpedition=" + dateExpedition +
                ", dateLivraisonPrevue=" + dateLivraisonPrevue +
                ", itineraireId=" + itineraireId +
                ", livraisonId=" + livraisonId +
                ", clientId=" + clientId + // Ajout du clientId dans le toString
                '}';
    }
}
