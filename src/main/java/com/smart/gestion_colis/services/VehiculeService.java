package com.smart.gestion_colis.services;

import com.smart.gestion_colis.dtos.VehiculeDto;
import com.smart.gestion_colis.entities.*;
import com.smart.gestion_colis.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class VehiculeService {

    private final LivreurRepository livreurRepository;
    private final VehiculeRepository vehiculeRepository;
    private final MotifRepository motifRepository;
    private final NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    private final AdminRepository adminRepository;

    public VehiculeService(NotificationService notificationService, AdminRepository adminRepository, LivreurRepository livreurRepository, VehiculeRepository vehiculeRepository, MotifRepository motifRepository) {
        this.livreurRepository = livreurRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.motifRepository = motifRepository;
        this.adminRepository = adminRepository;
        this.notificationService = notificationService;
    }

    /**
     * Méthode pour qu'un livreur soumette un véhicule, envoie une notification à l'admin
     */
    @Transactional
    public Vehicule addVehiculeToLivreur(Integer livreurId, VehiculeDto vehiculeDto, ImageData assuranceImage, ImageData carteGriseImage, List<ImageData> photos) {
        Livreur livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livreurId));

        Vehicule vehicule = new Vehicule();
        vehicule.setMarque(vehiculeDto.getMarque());
        vehicule.setModele(vehiculeDto.getModele());
        vehicule.setImmatriculation(vehiculeDto.getImmatriculation());
        vehicule.setApprouve(false);
        vehicule.setRejected(false);
        vehicule.setLivreur(livreur);

        // Associer l'assurance et la carte grise
        vehicule.setAssurance(assuranceImage);
        vehicule.setCarteGrise(carteGriseImage);

        // Ajouter les photos de l'intérieur et de l'extérieur
        vehicule.setPhotos(photos != null ? photos : new ArrayList<>());

        // Associer le véhicule au livreur
        livreur.setVehicule(vehicule);

        // Sauvegarder le véhicule et mettre à jour le livreur
        Vehicule savedVehicule = vehiculeRepository.save(vehicule);
        livreurRepository.save(livreur);

        // Récupérer l'admin (on suppose qu'il y en a un seul)
        Admin admin = StreamSupport.stream(adminRepository.findAll().spliterator(), false)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Créer et envoyer une notification à l'admin
        String message = "Un nouveau véhicule a été soumis par le livreur " + livreur.getFullName();
        notificationService.createNotificationForAdmin(admin, savedVehicule, message);
        return savedVehicule;
    }

    // Approuver un véhicule et supprimer tout motif existant
    public Vehicule approveVehicule(Integer vehiculeId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));

        // Supprimer le motif de rejet s'il existe
        if (vehicule.getMotif() != null) {
            Motif motif = vehicule.getMotif();
            vehicule.setMotif(null);
            vehiculeRepository.save(vehicule);
            motifRepository.delete(motif);
        }

        // Mettre à jour l'état du véhicule comme approuvé
        vehicule.setApprouve(true);
        vehicule.setRejected(false);
        Vehicule approvedVehicule = vehiculeRepository.save(vehicule);

        // Récupérer le livreur associé au véhicule
        Livreur livreur = vehicule.getLivreur();
        if (livreur == null) {
            throw new RuntimeException("Livreur not found for the approved vehicle with id: " + vehiculeId);
        }

        // Créer et envoyer une notification au livreur
        String message = "Votre véhicule a été approuvé par l'administrateur.";
        notificationService.createNotificationForLivreur(livreur, approvedVehicule, message);

        return approvedVehicule;
    }


    // Rejeter un véhicule avec un motif
    public Vehicule rejectVehicule(Integer vehiculeId, String motifDescription) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));

        // Gérer le motif de rejet
        Motif motif;
        if (vehicule.getMotif() != null) {
            motif = vehicule.getMotif();
            motif.setDescription(motifDescription);
        } else {
            motif = new Motif();
            motif.setDescription(motifDescription);
            motif.setVehicule(vehicule);
            vehicule.setMotif(motif);
        }

        // Mettre à jour l'état du véhicule
        vehicule.setApprouve(false);
        vehicule.setRejected(true);

        // Sauvegarder le motif et le véhicule
        motifRepository.save(motif);
        Vehicule updatedVehicule = vehiculeRepository.save(vehicule);

        // Récupérer le livreur associé au véhicule
        Livreur livreur = vehicule.getLivreur();
        if (livreur == null) {
            throw new RuntimeException("Livreur not found for the rejected vehicle with id: " + vehiculeId);
        }

        // Créer et envoyer une notification au livreur
        String message = "Votre véhicule a été rejeté par l'administrateur.";
        notificationService.createNotificationForLivreur(livreur, updatedVehicule, message);

        return updatedVehicule;
    }



    // Récupérer tous les véhicules pour l'admin
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    public long getTotalVehiculesApprouves() {
        return vehiculeRepository.countByApprouveTrue();
    }

    public long countRejectedVehicules() {
        return vehiculeRepository.countByRejectedTrue();
    }


    // Mise à jour d'un véhicule
    @Transactional
    public Vehicule updateVehicule(Integer vehiculeId, VehiculeDto vehiculeDto, ImageData assuranceImage, ImageData carteGriseImage, List<ImageData> photos) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));

        // Mise à jour des informations du véhicule
        vehicule.setMarque(vehiculeDto.getMarque());
        vehicule.setModele(vehiculeDto.getModele());
        vehicule.setImmatriculation(vehiculeDto.getImmatriculation());

        // Mise à jour des images si elles sont fournies
        if (assuranceImage != null) {
            vehicule.setAssurance(assuranceImage);
        }
        if (carteGriseImage != null) {
            vehicule.setCarteGrise(carteGriseImage);
        }

        // Mise à jour des photos sans remplacer la collection entière
        if (photos != null && !photos.isEmpty()) {
            vehicule.getPhotos().clear(); // Vider la liste existante
            vehicule.getPhotos().addAll(photos); // Ajouter les nouvelles photos
        }

        // Désapprouver le véhicule jusqu'à la prochaine vérification
        vehicule.setApprouve(false);

        // Sauvegarde du véhicule
        Vehicule updatedVehicule = vehiculeRepository.save(vehicule);

        // Récupération de l'administrateur
        Admin admin = StreamSupport.stream(adminRepository.findAll().spliterator(), false)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Création et envoi d'une notification à l'admin
        String message = "Le livreur " + vehicule.getLivreur().getFullName() + " a mis à jour les informations d'un véhicule.";
        notificationService.createNotificationForAdmin(admin, updatedVehicule, message);

        return updatedVehicule;
    }


    // Récupérer un véhicule par ID
    public Vehicule getVehiculeById(Integer vehiculeId) {
        return vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));
    }

    // Récupérer tous les véhicules pour un livreur
    public List<Vehicule> getVehiculesByLivreur(Integer livreurId) {
        Livreur livreur = livreurRepository.findById(livreurId)
                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livreurId));

        return vehiculeRepository.findByLivreur(livreur);
    }

    @Transactional
    public void deleteVehiculeById(Integer vehiculeId) {
        Livreur livreur = livreurRepository.findByVehiculeId(vehiculeId);
        if (livreur != null) {
            livreur.setVehicule(null);
            livreurRepository.save(livreur);
        }

        List<Notification> notifications = notificationRepository.findByVehicule_Id(vehiculeId);
        for (Notification notification : notifications) {
            notification.setVehicule(null);
            notificationRepository.save(notification);
        }

        vehiculeRepository.deleteById(vehiculeId);
    }
}
