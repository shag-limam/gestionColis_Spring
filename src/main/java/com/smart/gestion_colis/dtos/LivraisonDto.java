package com.smart.gestion_colis.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class LivraisonDto {

    // Informations sur la livraison
    private String statut;

    // Informations sur le colis
    private Integer colisId;

    // Informations sur l'itinéraire
    private String departurePoint;
    private String arrivalPoint;
    private List<String> waypoints;

    // Identifiant du livreur
    private Integer livreurId;

    // Ajout de la date de livraison prévue
    private Date dateLivraisonPrevue;

    // Ajout de la date d'expédition du colis
    private Date dateExpedition;
}
