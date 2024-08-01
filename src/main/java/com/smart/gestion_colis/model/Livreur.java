//package com.smart.gestion_colis.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//
//import java.util.List;
//
//@Entity
//@DiscriminatorValue("LIVREUR")
//public class Livreur extends Utilisateur {
//
//    @NotBlank(message = "La licence est obligatoire.")
//    @Column(name = "licence")
//    private String licence;
//
//    // Renommé 'vehiculeType' pour éviter le conflit de noms
//    @NotBlank(message = "Le véhicule est obligatoire.")
//    @Column(name = "vehicule_type") // Assurez-vous que le nom de la colonne correspond au nouveau nom de la propriété
//    private String vehiculeType;
//
//    @OneToMany(mappedBy = "livreur", cascade = CascadeType.ALL)
//    private List<Colis> colis;
//
//    // Renommé 'vehiculeEntity' pour clarifier qu'il s'agit d'une relation vers une autre entité
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "vehicule_id")
//    private Vehicule vehiculeEntity; // Le nom de cette propriété reste inchangé car il n'y avait pas de conflit
//
//    public Livreur(String nom, String email, String motDePasse, String licence, String vehiculeType) {
//        super(nom, email, motDePasse);
//        this.licence = licence;
//        this.vehiculeType = vehiculeType; // Mise à jour du paramètre pour refléter le nouveau nom de la propriété
//    }
//
//    // Ajoutez des getters et setters pour 'vehiculeType' et 'vehiculeEntity'
//}
