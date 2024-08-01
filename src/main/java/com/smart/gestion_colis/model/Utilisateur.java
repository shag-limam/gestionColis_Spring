//package com.smart.gestion_colis.model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import lombok.*;
//
//import java.io.Serializable;
//import java.time.LocalDate;
//
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "utilisateur")
//public class Utilisateur implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank(message = "*Le nom est obligatoire.")
//    @Column(name = "nom")
//    private String nom;
//
//    @Email(message = "Veuillez entrer une adresse email valide.")
//    @NotBlank(message = "L'email est obligatoire.")
//    @Column(name = "email")
//    private String email;
//
//    @Size(min = 8, max = 100, message = "Le mot de passe doit contenir entre 8 et 100 caractères.")
//    @NotBlank(message = "Le mot de passe est obligatoire.")
//    @Column(name = "mot_de_passe")
//    private String motDePasse;
//
//    public Utilisateur(String nom, String email, String motDePasse) {
//        this.nom = nom;
//        this.email = email;
//        this.motDePasse = motDePasse;
//    }
//}
