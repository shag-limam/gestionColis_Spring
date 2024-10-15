//package com.smart.gestion_colis.entities;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Entity
//@Table(name = "colis")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Colis {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
//    private Integer id;
//
//    @Column(nullable = false)
//    private String description;
//
//    @Column(nullable = false)
//    private Double poids;
//
//    @Lob
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "image_id", referencedColumnName = "id")
//    private ImageData imageData;
//
//    @Column(nullable = false)
//    private String adresseExpediteur;
//
//    @Column(nullable = false)
//    private String adresseDestinataire;
//
//    @Temporal(TemporalType.DATE)
//    @Column(nullable = false)
//    private Date dateExpedition;
//
//    @Temporal(TemporalType.DATE)
//    @Column(nullable = false)
//    private Date dateLivraisonPrevue;
//
//    @ManyToOne
//    @JoinColumn(name = "client_id", nullable = false)
//    private Client client;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "livraison_id", referencedColumnName = "id")
//    @JsonManagedReference
//    private Livraison livraison;
//
//    @Column(nullable = false, unique = true)
//    private String referenceSuivi;
//
//    @Column(nullable = false)
//    private boolean isAvailable = true;  // Par défaut, le colis est disponible.
//
//    @PrePersist
//    public void generateReferenceSuivi() {
//        if (this.referenceSuivi == null) {
//            this.referenceSuivi = generateUniqueReference();
//        }
//    }
//
//    // Méthode pour générer une référence de suivi unique de 12 chiffres basée sur la date actuelle yy-MM-jj-hh-mm-ss
//    private String generateUniqueReference() {
//        // Crée une instance de SimpleDateFormat pour formater la date selon le format souhaité.
//        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
//
//        // Obtenez la date actuelle.
//        Date now = new Date();
//
//        // Formatez la date dans une chaîne de 12 chiffres.
//        return sdf.format(now);
//    }
//
//    @Override
//    public String toString() {
//        return "Colis{" +
//                "id=" + id +
//                ", description='" + description + '\'' +
//                ", poids=" + poids +
//                ", adresseExpediteur='" + adresseExpediteur + '\'' +
//                ", adresseDestinataire='" + adresseDestinataire + '\'' +
//                ", dateExpedition=" + dateExpedition +
//                ", dateLivraisonPrevue=" + dateLivraisonPrevue +
//                ", referenceSuivi='" + referenceSuivi + '\'' +
//                ", isAvailable=" + isAvailable +
//                ", livraison=" + (livraison != null ? livraison.getId() : "null") +
//                ", client=" + (client != null ? client.getId() : "null") +
//                ", imageData=" + (imageData != null ? imageData.getName() : "null") +
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

import java.text.SimpleDateFormat;
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

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @JsonIgnore  // Ignore la relation inverse pour éviter les cycles
    @OneToOne(mappedBy = "colis", cascade = CascadeType.ALL)
    private Livraison livraison;

    @Column(nullable = false, unique = true)
    private String referenceSuivi;

    @Column(nullable = false)
    private boolean isAvailable = true;  // Par défaut, le colis est disponible.

    @PrePersist
    public void generateReferenceSuivi() {
        if (this.referenceSuivi == null) {
            this.referenceSuivi = generateUniqueReference();
        }
    }

    private String generateUniqueReference() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        Date now = new Date();
        return sdf.format(now);
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
                ", referenceSuivi='" + referenceSuivi + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

//package com.smart.gestion_colis.entities;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Entity
//@Table(name = "colis")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Colis {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(nullable = false)
//    private Integer id;
//
//    @Column(nullable = false)
//    private String description;
//
//    @Column(nullable = false)
//    private Double poids;
//
//    @Lob
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "image_id", referencedColumnName = "id")
//    private ImageData imageData;
//
//    @Column(nullable = false)
//    private String adresseExpediteur;
//
//    @Column(nullable = false)
//    private String adresseDestinataire;
//
//    @Temporal(TemporalType.DATE)
//    @Column(nullable = false)
//    private Date dateExpedition;
//
//    @Temporal(TemporalType.DATE)
//    @Column(nullable = false)
//    private Date dateLivraisonPrevue;
//
//    @ManyToOne
//    @JoinColumn(name = "client_id", nullable = false)
//    private Client client;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "livraison_id", referencedColumnName = "id")
//    @JsonManagedReference
//    private Livraison livraison;
//
//    @Column(nullable = false, unique = true)
//    private String referenceSuivi;
//
//    @Column(nullable = false)
//    private boolean isAvailable = true;  // Par défaut, le colis est disponible.
//
//    @PrePersist
//    public void generateReferenceSuivi() {
//        if (this.referenceSuivi == null) {
//            this.referenceSuivi = generateUniqueReference();
//        }
//    }
//
//    private String generateUniqueReference() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
//        Date now = new Date();
//        return sdf.format(now);
//    }
//
//    @Override
//    public String toString() {
//        return "Colis{" +
//                "id=" + id +
//                ", description='" + description + '\'' +
//                ", poids=" + poids +
//                ", adresseExpediteur='" + adresseExpediteur + '\'' +
//                ", adresseDestinataire='" + adresseDestinataire + '\'' +
//                ", dateExpedition=" + dateExpedition +
//                ", dateLivraisonPrevue=" + dateLivraisonPrevue +
//                ", referenceSuivi='" + referenceSuivi + '\'' +
//                ", isAvailable=" + isAvailable +
//                ", livraison=" + (livraison != null ? livraison.getId() : "null") +
//                ", client=" + (client != null ? client.getId() : "null") +
//                ", imageData=" + (imageData != null ? imageData.getName() : "null") +
//                '}';
//    }
//}
