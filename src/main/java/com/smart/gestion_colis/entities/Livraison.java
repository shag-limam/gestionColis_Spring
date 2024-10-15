//package com.smart.gestion_colis.entities;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "livraisons")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Livraison {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
//    private Integer id;
//
//    @Temporal(TemporalType.DATE)
//    @Column(nullable = true)
//    private Date dateLivraisonReelle;
//
//    @Column(nullable = false)
//    private String statut;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "livreur_id", nullable = false)
//    @JsonManagedReference  // Gérer la relation côté propriétaire
//    private Livreur livreur;
//
//    @OneToOne(mappedBy = "livraison", fetch = FetchType.EAGER)
//    private Colis colis;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "itineraire_id", referencedColumnName = "id", nullable = false, unique = true)
//    private Itineraire itineraire;
//
//    public boolean estLivree() {
//        return "Livré".equalsIgnoreCase(this.statut);
//    }
//
//    public boolean estEnCours() {
//        return "En cours".equalsIgnoreCase(this.statut);
//    }
//
//    public void annulerLivraison() {
//        this.statut = "Annulé";
//    }
//
//    public void setDateLivraisonReelle(Date date) {
//        this.dateLivraisonReelle = date;
//    }
//
//    public void affecterLivreur(Livreur livreur) {
//        this.livreur = livreur;
//    }
//
//    @Override
//    public String toString() {
//        return "Livraison{" +
//                "id=" + id +
//                ", dateLivraisonReelle=" + dateLivraisonReelle +
//                ", statut='" + statut + '\'' +
//                ", livreur=" + (livreur != null ? livreur.getFullName() : "null") +
//                ", colis=" + (colis != null ? colis.getId() : "null") +
//                ", itineraire=" + (itineraire != null ? itineraire.getId() : "null") +
//                '}';
//    }
//}

package com.smart.gestion_colis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    //@ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne()
//    @JoinColumn(name = "livreur_id", nullable = false)
    @JoinColumn(name = "livreur_id", referencedColumnName = "id")
    //@JsonIgnore  // Ignore la relation inverse pour éviter les cycles
    private Livreur livreur;


//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "colis_id", referencedColumnName = "id", nullable = false)
//    private Colis colis;
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

    public void annulerLivraison() {
        this.statut = "Annulé";
    }

    public void setDateLivraisonReelle(Date date) {
        this.dateLivraisonReelle = date;
    }

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


//package com.smart.gestion_colis.entities;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.Date;
//
//@Entity
//@Table(name = "livraisons")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Livraison {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
//    private Integer id;
//
//    @Temporal(TemporalType.DATE)
//    @Column(nullable = true)
//    private Date dateLivraisonReelle;
//
//    @Column(nullable = false)
//    private String statut;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "livreur_id", nullable = false)
//    @JsonBackReference  // Côté inverse de la relation pour éviter les cycles
//    private Livreur livreur;
//
//    @OneToOne(mappedBy = "livraison", fetch = FetchType.EAGER)
//    private Colis colis;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "itineraire_id", referencedColumnName = "id", nullable = false, unique = true)
//    private Itineraire itineraire;
//
//    public boolean estLivree() {
//        return "Livré".equalsIgnoreCase(this.statut);
//    }
//
//    public boolean estEnCours() {
//        return "En cours".equalsIgnoreCase(this.statut);
//    }
//
//    public void annulerLivraison() {
//        this.statut = "Annulé";
//    }
//
//    public void setDateLivraisonReelle(Date date) {
//        this.dateLivraisonReelle = date;
//    }
//
//    @Override
//    public String toString() {
//        return "Livraison{" +
//                "id=" + id +
//                ", dateLivraisonReelle=" + dateLivraisonReelle +
//                ", statut='" + statut + '\'' +
//                ", livreur=" + (livreur != null ? livreur.getFullName() : "null") +
//                ", colis=" + (colis != null ? colis.getId() : "null") +
//                ", itineraire=" + (itineraire != null ? itineraire.getId() : "null") +
//                '}';
//    }
//}
