package com.smart.gestion_colis.services;

import com.smart.gestion_colis.dtos.VehiculeDto;
import com.smart.gestion_colis.entities.Livreur;
import com.smart.gestion_colis.entities.Motif;
import com.smart.gestion_colis.entities.Vehicule;
import com.smart.gestion_colis.repositories.LivreurRepository;
import com.smart.gestion_colis.repositories.MotifRepository;
import com.smart.gestion_colis.repositories.VehiculeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculeService {

    private final LivreurRepository livreurRepository;
    private final VehiculeRepository vehiculeRepository;
    private final MotifRepository motifRepository;

    public VehiculeService(LivreurRepository livreurRepository, VehiculeRepository vehiculeRepository, MotifRepository motifRepository) {
        this.livreurRepository = livreurRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.motifRepository = motifRepository;
    }

    // Méthode pour que le livreur soumette un véhicule
    public Vehicule addVehiculeToLivreur(Integer livreurId, VehiculeDto vehiculeDto) {
        Livreur livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livreurId));

        Vehicule vehicule = new Vehicule();
        vehicule.setMarque(vehiculeDto.getMarque());
        vehicule.setModele(vehiculeDto.getModele());
        vehicule.setImmatriculation(vehiculeDto.getImmatriculation());
        vehicule.setApprouve(false);  // Par défaut, le véhicule n'est pas approuvé
        vehicule.setRejected(false);  // Par défaut, le véhicule n'est pas rejeté
        vehicule.setLivreur(livreur);

        // Associer le véhicule au livreur
        livreur.setVehicule(vehicule);

        // Sauvegarder le véhicule et mettre à jour le livreur
        vehiculeRepository.save(vehicule);
        livreurRepository.save(livreur);

        return vehicule;
    }

    // Approuver un véhicule et supprimer tout motif existant
    public Vehicule approveVehicule(Long vehiculeId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));

        // Vérifier si un motif est associé, et le supprimer s'il est présent
        if (vehicule.getMotif() != null) {
            Motif motif = vehicule.getMotif();

            // Dissocier le motif du véhicule avant suppression
            vehicule.setMotif(null);
            vehiculeRepository.save(vehicule);  // Sauvegarder le véhicule sans le motif

            // Supprimer ensuite le motif de la base de données
            motifRepository.delete(motif);
        }

        // Approuver le véhicule
        vehicule.setApprouve(true);
        vehicule.setRejected(false);  // Le véhicule n'est plus rejeté

        // Sauvegarder le véhicule approuvé
        return vehiculeRepository.save(vehicule);
    }

    // Rejeter un véhicule avec un motif
    public Vehicule rejectVehicule(Long vehiculeId, String motifDescription) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));

        // Vérifier si un motif existe déjà pour ce véhicule
        Motif motif;
        if (vehicule.getMotif() != null) {
            // Si un motif existe déjà, le mettre à jour avec la nouvelle description
            motif = vehicule.getMotif();
            motif.setDescription(motifDescription);
        } else {
            // Si aucun motif n'existe, en créer un nouveau
            motif = new Motif();
            motif.setDescription(motifDescription);
            motif.setVehicule(vehicule);
            vehicule.setMotif(motif);
        }

        // Marquer le véhicule comme rejeté
        vehicule.setApprouve(false);
        vehicule.setRejected(true);

        // Sauvegarder les modifications
        motifRepository.save(motif);
        return vehiculeRepository.save(vehicule);
    }


    // Récupérer tous les véhicules pour l'admin
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }
}
