package com.smart.gestion_colis.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VehiculeDto {

    private String marque;
    private String modele;
    private String immatriculation;

    // Getters et Setters
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


    // completer toString
    @Override
    public String toString() {
        return "VehiculeDto{" +
                "marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", immatriculation='" + immatriculation + '\'' +
                '}';
    }

}
