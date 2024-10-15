package com.smart.gestion_colis.services;

import com.smart.gestion_colis.dtos.LivraisonDto;
import com.smart.gestion_colis.entities.Colis;
import com.smart.gestion_colis.entities.Itineraire;
import com.smart.gestion_colis.entities.Livraison;
import com.smart.gestion_colis.entities.Livreur;
import com.smart.gestion_colis.repositories.ColisRepository;
import com.smart.gestion_colis.repositories.ItineraireRepository;
import com.smart.gestion_colis.repositories.LivraisonRepository;
import com.smart.gestion_colis.repositories.LivreurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
@Slf4j
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
    public Livraison createLivraison(LivraisonDto livraisonDto) {
        // Rechercher le colis
        Colis colis = colisRepository.findById(livraisonDto.getColisId())
                .orElseThrow(() -> new RuntimeException("Colis not found with id: " + livraisonDto.getColisId()));

        if (colis.getLivraison() != null || !colis.isAvailable()) {
            throw new RuntimeException("Ce colis est déjà affecté à une livraison ou n'est pas disponible.");
        }

        // Rechercher le livreur
        Livreur livreur = livreurRepository.findById(livraisonDto.getLivreurId())
                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livraisonDto.getLivreurId()));

        if (!livreur.isAvailable()) {
            throw new RuntimeException("Ce livreur n'est pas disponible.");
        }

        // Créer l'itinéraire
        Itineraire itineraire = new Itineraire();
        itineraire.setDeparturePoint(livraisonDto.getDeparturePoint());
        itineraire.setArrivalPoint(livraisonDto.getArrivalPoint());
        itineraire.setWaypoints(livraisonDto.getWaypoints());
        itineraire = itineraireRepository.save(itineraire);

        // Créer la livraison
        Livraison livraison = new Livraison();
        livraison.setColis(colis);
        livraison.setLivreur(livreur);
        livraison.setItineraire(itineraire);
        livraison.setStatut(livraisonDto.getStatut());
        livraison.setDateLivraisonReelle(null);

        // Utiliser les setters du Colis pour mettre à jour les dates
        colis.setDateLivraisonPrevue(livraisonDto.getDateLivraisonPrevue());
        colis.setDateExpedition(livraisonDto.getDateExpedition());

        // Mettre à jour la disponibilité du livreur et du colis
        livreur.setAvailable(false);
        colis.setAvailable(false);
        livreurRepository.save(livreur);
        colisRepository.save(colis);

        return livraisonRepository.save(livraison);
    }

    /**
     * Mettre à jour une Livraison existante, avec possibilité de changer le livreur et/ou le colis
     */
//    @Transactional
//    public Livraison updateLivraison(Integer livraisonId, LivraisonDto livraisonDto) {
//        // Rechercher la livraison par son ID
//        Livraison livraison = livraisonRepository.findById(livraisonId)
//                .orElseThrow(() -> new RuntimeException("Livraison not found with id: " + livraisonId));
//
//        // Mise à jour du colis si un nouvel ID de colis est fourni, sinon garder le colis actuel
//        if (livraisonDto.getColisId() != null) {
//            Colis newColis = colisRepository.findById(livraisonDto.getColisId())
//                    .orElseThrow(() -> new RuntimeException("Colis not found with id: " + livraisonDto.getColisId()));
//
//            if (newColis.getLivraison() != null && !newColis.getLivraison().getId().equals(livraisonId)) {
//                throw new RuntimeException("Ce colis est déjà affecté à une autre livraison.");
//            }
//
//            // Rendre l'ancien colis disponible avant d'assigner le nouveau
//            if (livraison.getColis() != null && !livraison.getColis().getId().equals(newColis.getId())) {
//                livraison.getColis().setAvailable(true);
//                livraison.getColis().setLivraison(null);
//                colisRepository.save(livraison.getColis());
//            }
//
//            livraison.setColis(newColis);
//            newColis.setLivraison(livraison);
//            newColis.setAvailable(false);
//            colisRepository.save(newColis);
//        }
//
//        // Mise à jour du livreur si un nouvel ID de livreur est fourni, sinon garder le livreur actuel
//        if (livraisonDto.getLivreurId() != null) {
//            Livreur newLivreur = livreurRepository.findById(livraisonDto.getLivreurId())
//                    .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livraisonDto.getLivreurId()));
//
//            if (!newLivreur.isAvailable()) {
//                throw new RuntimeException("Ce livreur n'est pas disponible.");
//            }
//
//            // Rendre l'ancien livreur disponible avant d'assigner le nouveau
//            if (livraison.getLivreur() != null && !livraison.getLivreur().getId().equals(newLivreur.getId())) {
//                livraison.getLivreur().setAvailable(true);
//                livreurRepository.save(livraison.getLivreur());
//            }
//
//            livraison.setLivreur(newLivreur);
//            newLivreur.setAvailable(false);
//            livreurRepository.save(newLivreur);
//        }
//
//        // Mise à jour de l'itinéraire si des données sont fournies, sinon garder l'itinéraire actuel
//        if (livraisonDto.getDeparturePoint() != null && livraisonDto.getArrivalPoint() != null) {
//            Itineraire itineraire = livraison.getItineraire();
//            if (itineraire == null) {
//                itineraire = new Itineraire();
//            }
//            itineraire.setDeparturePoint(livraisonDto.getDeparturePoint());
//            itineraire.setArrivalPoint(livraisonDto.getArrivalPoint());
//            itineraire.setWaypoints(livraisonDto.getWaypoints());
//            itineraireRepository.save(itineraire);
//            livraison.setItineraire(itineraire);
//        }
//
//        // Mise à jour des dates dans le colis si fournies, sinon garder les dates actuelles
//        if (livraison.getColis() != null) {
//            if (livraisonDto.getDateExpedition() != null) {
//                livraison.getColis().setDateExpedition(livraisonDto.getDateExpedition());
//            }
//            if (livraisonDto.getDateLivraisonPrevue() != null) {
//                livraison.getColis().setDateLivraisonPrevue(livraisonDto.getDateLivraisonPrevue());
//            }
//            colisRepository.save(livraison.getColis());
//        }
//
//        // Mise à jour du statut si fourni, sinon garder le statut actuel
//        if (livraisonDto.getStatut() != null) {
//            livraison.setStatut(livraisonDto.getStatut());
//        }
//
//        return livraisonRepository.save(livraison);
//    }

//    @Transactional
//    public Livraison updateLivraison(Integer livraisonId, LivraisonDto livraisonDto) {
//        // Rechercher la livraison par son ID
//        Livraison livraison = livraisonRepository.findById(livraisonId)
//                .orElseThrow(() -> new RuntimeException("Livraison not found with id: " + livraisonId));
//
//        // Vérifier et mettre à jour le Colis si un nouvel ID est fourni, sinon garder l'ancien Colis
//        if (livraisonDto.getColisId() != null) {
//            Colis newColis = colisRepository.findById(livraisonDto.getColisId())
//                    .orElseThrow(() -> new RuntimeException("Colis not found with id: " + livraisonDto.getColisId()));
//
//            if (newColis.getLivraison() != null && !newColis.getLivraison().getId().equals(livraisonId)) {
//                throw new RuntimeException("Ce colis est déjà affecté à une autre livraison.");
//            }
//
//            if (!newColis.equals(livraison.getColis())) {
//                // Libérer l'ancien colis
//                livraison.getColis().setAvailable(true);
//                livraison.getColis().setLivraison(null);
//                colisRepository.save(livraison.getColis());
//
//                // Assigner le nouveau colis
//                livraison.setColis(newColis);
//                newColis.setAvailable(false);
//                newColis.setLivraison(livraison);
//                colisRepository.save(newColis);
//            }
//        }
//
//        // Vérifier et mettre à jour le Livreur si un nouvel ID est fourni, sinon garder l'ancien Livreur
//        if (livraisonDto.getLivreurId() != null) {
//            Livreur newLivreur = livreurRepository.findById(livraisonDto.getLivreurId())
//                    .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livraisonDto.getLivreurId()));
//
//            if (!newLivreur.equals(livraison.getLivreur())) {
//                // Libérer l'ancien livreur
//                if (livraison.getLivreur() != null) {
//                    livraison.getLivreur().setAvailable(true);
//                    livreurRepository.save(livraison.getLivreur());
//                }
//
//                // Assigner le nouveau livreur
//                livraison.setLivreur(newLivreur);
//                newLivreur.setAvailable(false);
//                livreurRepository.save(newLivreur);
//            }
//        }
//
//        // Mettre à jour l'itinéraire si les informations sont fournies
//        if (livraisonDto.getDeparturePoint() != null && livraisonDto.getArrivalPoint() != null) {
//            Itineraire itineraire = livraison.getItineraire();
//            itineraire.setDeparturePoint(livraisonDto.getDeparturePoint());
//            itineraire.setArrivalPoint(livraisonDto.getArrivalPoint());
//            itineraire.setWaypoints(livraisonDto.getWaypoints());
//            itineraireRepository.save(itineraire);
//        }
//
//        // Mettre à jour les dates du colis si elles sont fournies
//        if (livraison.getColis() != null) {
//            if (livraisonDto.getDateExpedition() != null) {
//                livraison.getColis().setDateExpedition(livraisonDto.getDateExpedition());
//            }
//            if (livraisonDto.getDateLivraisonPrevue() != null) {
//                livraison.getColis().setDateLivraisonPrevue(livraisonDto.getDateLivraisonPrevue());
//            }
//            colisRepository.save(livraison.getColis());
//        }
//
//        // Mettre à jour le statut si fourni
//        if (livraisonDto.getStatut() != null) {
//            livraison.setStatut(livraisonDto.getStatut());
//        }
//
//        return livraisonRepository.save(livraison);
//    }

    // Méthode pour récupérer les livraisons par référence de suivi
    public List<Livraison> getLivraisonsByReferenceSuivi(String referenceSuivi) {
        Colis colis = colisRepository.findByReferenceSuivi(referenceSuivi)
                .orElseThrow(() -> new RuntimeException("Colis not found with reference: " + referenceSuivi));

        return livraisonRepository.findByColis(colis);
    }
    @Transactional
    public Livraison updateLivraison(Integer livraisonId, LivraisonDto livraisonDto) {
        // Rechercher la livraison par son ID
        Livraison livraison = livraisonRepository.findById(livraisonId)
                .orElseThrow(() -> new RuntimeException("Livraison not found with id: " + livraisonId));

        // Vérifier et mettre à jour le Colis si un nouvel ID est fourni, sinon garder l'ancien Colis
        if (livraisonDto.getColisId() != null) {
            Colis newColis = colisRepository.findById(livraisonDto.getColisId())
                    .orElseThrow(() -> new RuntimeException("Colis not found with id: " + livraisonDto.getColisId()));

            if (newColis.getLivraison() != null && !newColis.getLivraison().getId().equals(livraisonId)) {
                throw new RuntimeException("Ce colis est déjà affecté à une autre livraison.");
            }

            if (!newColis.equals(livraison.getColis())) {
                // Libérer l'ancien colis
                livraison.getColis().setAvailable(true);
                livraison.getColis().setLivraison(null);
                colisRepository.save(livraison.getColis());

                // Assigner le nouveau colis
                livraison.setColis(newColis);
                newColis.setAvailable(false);
                newColis.setLivraison(livraison);
                colisRepository.save(newColis);
            }
        }

        // Vérifier et mettre à jour le Livreur si un nouvel ID est fourni, sinon garder l'ancien Livreur
        if (livraisonDto.getLivreurId() != null) {
            Livreur newLivreur = livreurRepository.findById(livraisonDto.getLivreurId())
                    .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livraisonDto.getLivreurId()));

            if (!newLivreur.equals(livraison.getLivreur())) {
                // Libérer l'ancien livreur
                if (livraison.getLivreur() != null) {
                    livraison.getLivreur().setAvailable(true);
                    livreurRepository.save(livraison.getLivreur());
                }

                // Assigner le nouveau livreur
                livraison.setLivreur(newLivreur);
                newLivreur.setAvailable(false);
                livreurRepository.save(newLivreur);
            }
        }

        // Mettre à jour l'itinéraire si les informations sont fournies
        if (livraisonDto.getDeparturePoint() != null && livraisonDto.getArrivalPoint() != null) {
            Itineraire itineraire = livraison.getItineraire();
            itineraire.setDeparturePoint(livraisonDto.getDeparturePoint());
            itineraire.setArrivalPoint(livraisonDto.getArrivalPoint());
            itineraire.setWaypoints(livraisonDto.getWaypoints());
            itineraireRepository.save(itineraire);
        }

        // Mettre à jour les dates du colis si elles sont fournies
        if (livraison.getColis() != null) {
            if (livraisonDto.getDateExpedition() != null) {
                livraison.getColis().setDateExpedition(livraisonDto.getDateExpedition());
            }
            if (livraisonDto.getDateLivraisonPrevue() != null) {
                livraison.getColis().setDateLivraisonPrevue(livraisonDto.getDateLivraisonPrevue());
            }
            colisRepository.save(livraison.getColis());
        }

        // Mettre à jour le statut si fourni
        if (livraisonDto.getStatut() != null) {
            livraison.setStatut(livraisonDto.getStatut());

            // Appliquer les règles supplémentaires pour le statut
            if ("Livré".equalsIgnoreCase(livraisonDto.getStatut())) {
                livraison.setDateLivraisonReelle(new Date());
                // Rendre le livreur et le colis disponibles après la livraison
                livraison.getLivreur().setAvailable(true);
                livraison.getColis().setAvailable(false);
                livreurRepository.save(livraison.getLivreur());
                colisRepository.save(livraison.getColis());
            } else if ("Annulé".equalsIgnoreCase(livraisonDto.getStatut())) {
                livraison.annulerLivraison();
                livraison.getLivreur().setAvailable(true);
                livraison.getColis().setAvailable(true);
                livreurRepository.save(livraison.getLivreur());
                colisRepository.save(livraison.getColis());
            }
        }

        return livraisonRepository.save(livraison);
    }


    /**
     * Mettre à jour le statut d'une Livraison
     */
    @Transactional
    public Livraison updateLivraisonStatut(Integer livraisonId, String statut) {
        Livraison livraison = livraisonRepository.findById(livraisonId)
                .orElseThrow(() -> new RuntimeException("Livraison not found with id: " + livraisonId));

        livraison.setStatut(statut);

        if ("Livré".equalsIgnoreCase(statut)) {
            livraison.setDateLivraisonReelle(new Date());
            // Rendre le livreur et le colis disponibles après la livraison
            livraison.getLivreur().setAvailable(true);
//            livraison.getColis().setAvailable(true);
            livreurRepository.save(livraison.getLivreur());
            colisRepository.save(livraison.getColis());
        } else if ("Annulé".equalsIgnoreCase(statut)) {
            livraison.annulerLivraison();
            livraison.getLivreur().setAvailable(true);
            livraison.getColis().setAvailable(true);
            livreurRepository.save(livraison.getLivreur());
            colisRepository.save(livraison.getColis());
        }

        return livraisonRepository.save(livraison);
    }

    // Récupérer tous les livreurs disponibles
    public List<Livreur> getAvailableLivreurs() {
        return livreurRepository.findByIsAvailableTrue();
    }

    // Récupérer les colis non affectés à une livraison
    public List<Colis> getAvailableColis() {
        return colisRepository.findByIsAvailableTrue();
    }

    // Récupérer toutes les Livraisons
    public List<Livraison> getAllLivraisons() {
        List<Livraison> livraisons = livraisonRepository.findAll();
        for (Livraison livraison : livraisons) {
            if (livraison.getLivreur() != null) {
                livraison.getLivreur().getFullName(); // Charge explicitement le nom du livreur
            }
            if (livraison.getColis() != null) {
                livraison.getColis().getReferenceSuivi(); // Charge explicitement la référence du colis
            }
        }
        return livraisons;
    }



    // Récupérer une Livraison par son ID
    public Livraison getLivraisonById(Integer livraisonId) {
        Livraison livraison = livraisonRepository.findById(livraisonId)
                .orElseThrow(() -> new RuntimeException("Livraison not found with id: " + livraisonId));

        // Charger les dépendances
        if (livraison.getLivreur() != null) {
            livraison.getLivreur().getFullName(); // Charge explicitement le livreur
        }
        if (livraison.getColis() != null) {
            livraison.getColis().getReferenceSuivi(); // Charge explicitement la référence du colis
        }
        return livraison;
    }


    // Supprimer une Livraison par son ID
    @Transactional
    public void deleteLivraison(Integer livraisonId) {
        Livraison livraison = livraisonRepository.findById(livraisonId)
                .orElseThrow(() -> new RuntimeException("Livraison not found with id: " + livraisonId));

        // Rendre le livreur et le colis disponibles après suppression
        Livreur livreur = livraison.getLivreur();
        Colis colis = livraison.getColis();

        if (livreur != null) {
            livreur.setAvailable(true);
            livreur.getLivraisons().remove(livraison);  // Supprime la relation avec la livraison sans mettre null
            livreurRepository.save(livreur);
        }

        if (colis != null) {
            colis.setAvailable(true);
            colis.setLivraison(null);  // Dissocier le colis de la livraison
            colisRepository.save(colis);
        }

        // Supprimer l'itinéraire lié à la livraison si nécessaire
        if (livraison.getItineraire() != null) {
            itineraireRepository.delete(livraison.getItineraire());
        }

        // Finalement, supprimer la livraison
        livraisonRepository.delete(livraison);
    }

}
