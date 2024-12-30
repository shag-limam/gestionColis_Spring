package com.smart.gestion_colis.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "vehicules")
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String marque;

    @Column(nullable = false)
    private String modele;  // Renommé pour être cohérent avec "marque"

    @Column(nullable = false)
    private String immatriculation;

    // Champ pour la validation du véhicule par un Admin
    @Column(nullable = false)
    private Boolean approuve = false;

    @Column(nullable = false)
    private Boolean rejected = false;

    // Relation bidirectionnelle avec Livreur
    @JsonBackReference
    @OneToOne(mappedBy = "vehicule")
    private Livreur livreur;

    // Relation avec Geolocalisation
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "geolocalisation_id", referencedColumnName = "id")
    private Geolocalisation geolocalisation;

    // Relation avec Motif
    @OneToOne(mappedBy = "vehicule", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Motif motif;

    // Photo de l'assurance
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "assurance_id", referencedColumnName = "id")
    private ImageData assurance;

    // Photo de la carte grise
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "carte_grise_id", referencedColumnName = "id")
    private ImageData carteGrise;

    // Photos of the vehicle
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "vehicule_id")
    private List<ImageData> photos = new ArrayList<>();
    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public Boolean getApprouve() {
        return approuve;
    }

    public void setApprouve(Boolean approuve) {
        this.approuve = approuve;
    }

    public Boolean getRejected() {
        return rejected;
    }

    public void setRejected(Boolean rejected) {
        this.rejected = rejected;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }

    public Motif getMotif() {
        return motif;
    }

    public void setMotif(Motif motif) {
        this.motif = motif;
    }

    public Geolocalisation getGeolocalisation() {
        return geolocalisation;
    }

    public void setGeolocalisation(Geolocalisation geolocalisation) {
        this.geolocalisation = geolocalisation;
    }

    public ImageData getAssurance() {
        return assurance;
    }

    public void setAssurance(ImageData assurance) {
        this.assurance = assurance;
    }

    public ImageData getCarteGrise() {
        return carteGrise;
    }

    public void setCarteGrise(ImageData carteGrise) {
        this.carteGrise = carteGrise;
    }

    public List<ImageData> getPhotos() {
        return photos;
    }

    public void setPhotos(List<ImageData> photos) {
        this.photos = photos;
    }
}
