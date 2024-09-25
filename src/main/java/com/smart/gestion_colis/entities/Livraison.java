package com.smart.gestion_colis.entities;

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
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

    @OneToOne(mappedBy = "livraison")
    private Colis colis;

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
                ", livreur=" + livreur.getFullName() + // Suppose que Livreur a un attribut 'nom'
                ", colis=" + colis.getId() + // Suppose que Colis a un attribut 'id'
                '}';
    }
}
