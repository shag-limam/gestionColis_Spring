package com.smart.gestion_colis.dtos;

import com.smart.gestion_colis.entities.Colis;
import com.smart.gestion_colis.entities.Livreur;

import java.util.Date;

public class LivraisonDto {
    private Integer id;
    private Date dateLivraisonReelle;
    private String statut; // Ex : "En cours", "Livré", "Annulé"
    private Livreur livreur; // Correspond à l'ID du livreur affecté
    private Colis colis; // Correspond à l'ID du colis

    // Getter et Setter pour id
    public Integer getId() {
        return id;
    }

    public LivraisonDto setId(Integer id) {
        this.id = id;
        return this;
    }

    // Getter et Setter pour dateLivraisonReelle
    public Date getDateLivraisonReelle() {
        return dateLivraisonReelle;
    }

    public LivraisonDto setDateLivraisonReelle(Date dateLivraisonReelle) {
        this.dateLivraisonReelle = dateLivraisonReelle;
        return this;
    }

    // Getter et Setter pour statut
    public String getStatut() {
        return statut;
    }

    public LivraisonDto setStatut(String statut) {
        this.statut = statut;
        return this;
    }

    // Getter et Setter pour livreurId
    public Livreur getLivreur() {
        return livreur;
    }

    public LivraisonDto setLivreur(Livreur livreur) {
        this.livreur = livreur;
        return this;
    }

    // Getter et Setter pour colisId
    public Colis getColis() {
        return colis;
    }

    public LivraisonDto setColis(Colis colis) {
        this.colis = colis;
        return this;
    }

    @Override
    public String toString() {
        return "LivraisonDto{" +
                "id=" + id +
                ", dateLivraisonReelle=" + dateLivraisonReelle +
                ", statut='" + statut + '\'' +
                ", livreur=" + livreur +
                ", colis=" + colis +
                '}';
    }
}
