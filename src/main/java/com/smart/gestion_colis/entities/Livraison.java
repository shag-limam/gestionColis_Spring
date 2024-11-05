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
    private String statut;

    @Column(nullable = false)
    private boolean isStarted; // Nouveau champ pour suivre si la livraison a démarré

    @ManyToOne()
    @JoinColumn(name = "livreur_id", referencedColumnName = "id")
    //@JsonIgnore  // Ignore la relation inverse pour éviter les cycles
    private Livreur livreur;

    @OneToOne
    @JoinColumn(name = "colis_id", referencedColumnName = "id")
    private Colis colis;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itineraire_id", referencedColumnName = "id", nullable = false, unique = true)
    private Itineraire itineraire;

    public boolean estLivree() {
        return "Livré".equalsIgnoreCase(this.statut);
    }

    public boolean estEnCours() {
        return "En cours".equalsIgnoreCase(this.statut);
    }

    public boolean estEnAttente() {
        return "En attente".equalsIgnoreCase(this.statut);
    }

    public void annulerLivraison() {
        this.statut = "Annulé";
    }

    public void setDateLivraisonReelle(Date date) {
        this.dateLivraisonReelle = date;
    }

    public void demarrerLivraison() {
        this.isStarted = true;
        this.statut = "En cours"; // Optionnel : mettre à jour le statut à "En cours"
    }


    @Override
    public String toString() {
        return "Livraison{" +
                "id=" + id +
                ", dateLivraisonReelle=" + dateLivraisonReelle +
                ", statut='" + statut + '\'' +
                ", isStarted=" + isStarted + // Inclure isStarted dans toString
                ", livreur=" + (livreur != null ? livreur.getFullName() : "null") +
                ", colis=" + (colis != null ? colis.getId() : "null") +
                ", itineraire=" + (itineraire != null ? itineraire.getId() : "null") +
                '}';
    }
}

