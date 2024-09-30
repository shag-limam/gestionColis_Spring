package com.smart.gestion_colis.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "livraisons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livraison {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date dateLivraisonReelle;

    @Column(nullable = false)
    private String statut; // Ex : "En cours", "Livré", "Annulé"

    @ManyToOne
    @JoinColumn(name = "livreur_id", nullable = false)  // Une livraison est toujours liée à un livreur
    @JsonBackReference // Empêche la récursivité infinie en sérialisant la livraison vers le livreur
    private Livreur livreur;

    @OneToOne(mappedBy = "livraison")
    @JsonBackReference // Empêche la récursivité infinie en sérialisant le colis vers la livraison
    private Colis colis;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itineraire_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference // Point de départ de la sérialisation pour l'itinéraire
    private Itineraire itineraire;

    // Méthode pour vérifier si la livraison est livrée
    public boolean estLivree() {
        return "Livré".equalsIgnoreCase(this.statut);
    }

    // Méthode pour vérifier si la livraison est en cours
    public boolean estEnCours() {
        return "En cours".equalsIgnoreCase(this.statut);
    }

    // Méthode pour annuler la livraison
    public void annulerLivraison() {
        this.statut = "Annulé";
    }

    // Méthode pour mettre à jour la date réelle de livraison
    public void setDateLivraisonReelle(Date date) {
        this.dateLivraisonReelle = date;
    }

    // Méthode pour affecter un livreur
    public void affecterLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    // Méthode pour récupérer les informations sous forme de chaîne
    @Override
    public String toString() {
        return "Livraison{" +
                "id=" + id +
                ", dateLivraisonReelle=" + dateLivraisonReelle +
                ", statut='" + statut + '\'' +
                ", livreur=" + (livreur != null ? livreur.getFullName() : "null") +
                ", colis=" + (colis != null ? colis.getId() : "null") +
                ", itineraire=" + (itineraire != null ? itineraire.getId() : "null") +
                '}';
    }
}
