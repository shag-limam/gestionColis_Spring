package com.smart.gestion_colis.services;

import com.smart.gestion_colis.dtos.ItineraireDto;
import com.smart.gestion_colis.entities.Colis;
import com.smart.gestion_colis.entities.Itineraire;
import com.smart.gestion_colis.entities.Livraison;
import com.smart.gestion_colis.entities.Livreur;
import com.smart.gestion_colis.repositories.ColisRepository;
import com.smart.gestion_colis.repositories.ItineraireRepository;
import com.smart.gestion_colis.repositories.LivraisonRepository;
import com.smart.gestion_colis.repositories.LivreurRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class LivraisonService {

    private final LivraisonRepository livraisonRepository;
    private final ColisRepository colisRepository;
    private final LivreurRepository livreurRepository;
    private final ItineraireRepository itineraireRepository;

    public LivraisonService(LivraisonRepository livraisonRepository,
                            ColisRepository colisRepository,
                            LivreurRepository livreurRepository,
                            ItineraireRepository itineraireRepository) {
        this.livraisonRepository = livraisonRepository;
        this.colisRepository = colisRepository;
        this.livreurRepository = livreurRepository;
        this.itineraireRepository = itineraireRepository;
    }

    /**
     * Créer une nouvelle Livraison avec un nouvel Itinéraire
     */
    @Transactional
    public Livraison createLivraison(Integer colisId, Integer livreurId, ItineraireDto itineraireDto) {
        // Récupérer le Colis
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Colis not found with id: " + colisId));

        // Récupérer le Livreur
        Livreur livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livreurId));

        // Créer un nouvel Itinéraire à partir du DTO
        Itineraire itineraire = new Itineraire();
        itineraire.setDeparturePoint(itineraireDto.getDeparturePoint());
        itineraire.setArrivalPoint(itineraireDto.getArrivalPoint());
        itineraire.setWaypoints(itineraireDto.getWaypoints());

        // Sauvegarder l'itinéraire dans la base de données
        itineraire = itineraireRepository.save(itineraire);

        // Créer la livraison et l'associer avec le Colis, le Livreur, et le nouvel Itinéraire
        Livraison livraison = new Livraison();
        livraison.setColis(colis);
        livraison.setLivreur(livreur);
        livraison.setItineraire(itineraire);
        livraison.setStatut("En cours");
        livraison.setDateLivraisonReelle(null);

        // Associer la livraison au colis
        colis.setLivraison(livraison);

        // Sauvegarder la livraison dans la base de données
        livraison = livraisonRepository.save(livraison);

        return livraison;
    }

    /**
     * Mettre à jour le statut d'une Livraison
     */
    @Transactional
    public Livraison updateLivraisonStatut(Integer livraisonId, String statut) {
        Livraison livraison = livraisonRepository.findById(livraisonId)
                .orElseThrow(() -> new RuntimeException("Livraison not found with id: " + livraisonId));

        livraison.setStatut(statut);

        // Si la livraison est marquée comme "Livré", ajouter la date actuelle comme date de livraison réelle
        if ("Livré".equalsIgnoreCase(statut)) {
            livraison.setDateLivraisonReelle(new Date());
        } else if ("Annulé".equalsIgnoreCase(statut)) {
            livraison.annulerLivraison();
        }

        // Forcer le chargement des collections qui posent problème (comme les waypoints)
        if (livraison.getItineraire() != null) {
            livraison.getItineraire().getWaypoints().size();  // Force Hibernate à initialiser les waypoints
        }

        return livraisonRepository.save(livraison);
    }

    // Autres méthodes existantes
    public List<Livraison> getAllLivraisons() {
        List<Livraison> livraisons = livraisonRepository.findAll();

        // Charger les dépendances pour éviter les erreurs de lazy loading lors de la sérialisation
        for (Livraison livraison : livraisons) {
            if (livraison.getItineraire() != null) {
                livraison.getItineraire().getWaypoints().size();  // Forcer le chargement des waypoints
            }
        }

        return livraisons;
    }

    public Livraison getLivraisonById(Integer livraisonId) {
        Livraison livraison = livraisonRepository.findById(livraisonId)
                .orElseThrow(() -> new RuntimeException("Livraison not found with id: " + livraisonId));

        // Charger les dépendances pour éviter les erreurs de lazy loading
        if (livraison.getItineraire() != null) {
            livraison.getItineraire().getWaypoints().size();  // Forcer le chargement des waypoints
        }

        return livraison;
    }
}