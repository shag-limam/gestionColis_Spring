//package com.smart.gestion_colis.services;
//
//import com.smart.gestion_colis.dtos.VehiculeDto;
//import com.smart.gestion_colis.entities.*;
//import com.smart.gestion_colis.repositories.*;
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.StreamSupport;
//
//@Service
//public class VehiculeService {
//
//    private final LivreurRepository livreurRepository;
//    private final VehiculeRepository vehiculeRepository;
//    private final MotifRepository motifRepository;
//    private final NotificationService notificationService; // Injection du NotificationService
//
//    @Autowired
//    private NotificationRepository notificationRepository;
//
//    private final AdminRepository adminRepository;
//
//    public VehiculeService(NotificationService notificationService,AdminRepository adminRepository,LivreurRepository livreurRepository, VehiculeRepository vehiculeRepository, MotifRepository motifRepository) {
//        this.livreurRepository = livreurRepository;
//        this.vehiculeRepository = vehiculeRepository;
//        this.motifRepository = motifRepository;
//        this.adminRepository = adminRepository;
//        this.notificationService = notificationService;
//    }
//
//    // Méthode pour que le livreur soumette un véhicule
////    public Vehicule addVehiculeToLivreur(Integer livreurId, VehiculeDto vehiculeDto) {
////        Livreur livreur = livreurRepository.findById(livreurId)
////                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livreurId));
////
////        Vehicule vehicule = new Vehicule();
////        vehicule.setMarque(vehiculeDto.getMarque());
////        vehicule.setModele(vehiculeDto.getModele());
////        vehicule.setImmatriculation(vehiculeDto.getImmatriculation());
////        vehicule.setApprouve(false);  // Par défaut, le véhicule n'est pas approuvé
////        vehicule.setRejected(false);  // Par défaut, le véhicule n'est pas rejeté
////        vehicule.setLivreur(livreur);
////
////        // Associer le véhicule au livreur
////        livreur.setVehicule(vehicule);
////
////        // Sauvegarder le véhicule et mettre à jour le livreur
////        vehiculeRepository.save(vehicule);
////        livreurRepository.save(livreur);
////
////        return vehicule;
////    }
//    /**
//     * Méthode pour qu'un livreur soumette un véhicule, envoie une notification à l'admin
//     */
//    @Transactional
//    // pour que le livreur soumette un véhicule envoyer une notification vers admin
////    public Vehicule addVehiculeToLivreur(Integer livreurId, VehiculeDto vehiculeDto) {
////        Livreur livreur = livreurRepository.findById(livreurId)
////                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livreurId));
////
////        Vehicule vehicule = new Vehicule();
////        vehicule.setMarque(vehiculeDto.getMarque());
////        vehicule.setModele(vehiculeDto.getModele());
////        vehicule.setImmatriculation(vehiculeDto.getImmatriculation());
////        vehicule.setApprouve(false);  // Par défaut, le véhicule n'est pas approuvé
////        vehicule.setRejected(false);  // Par défaut, le véhicule n'est pas rejeté
////        vehicule.setLivreur(livreur);
////
////        // Associer le véhicule au livreur
////        livreur.setVehicule(vehicule);
////
////        // Sauvegarder le véhicule et mettre à jour le livreur
////        Vehicule savedVehicule = vehiculeRepository.save(vehicule);
////        livreurRepository.save(livreur);
////
////        // Récupérer l'admin (on suppose qu'il y en a un seul)
////        Admin admin = StreamSupport.stream(adminRepository.findAll().spliterator(), false)
////                .findFirst()
////                .orElseThrow(() -> new RuntimeException("Admin not found"));
////
////        // Créer et envoyer une notification à l'admin
////        String message = "Un nouveau véhicule a été soumis par le livreur " + livreur.getFullName();
////        Notification notification = new Notification(message, admin, savedVehicule);
////        notificationRepository.save(notification);
////
////        return savedVehicule;
////    }
//
//    public Vehicule addVehiculeToLivreur(Integer livreurId, VehiculeDto vehiculeDto) {
//        Livreur livreur = livreurRepository.findById(livreurId)
//                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livreurId));
//
//        Vehicule vehicule = new Vehicule();
//        vehicule.setMarque(vehiculeDto.getMarque());
//        vehicule.setModele(vehiculeDto.getModele());
//        vehicule.setImmatriculation(vehiculeDto.getImmatriculation());
//        vehicule.setApprouve(false);  // Par défaut, le véhicule n'est pas approuvé
//        vehicule.setRejected(false);  // Par défaut, le véhicule n'est pas rejeté
//        vehicule.setLivreur(livreur);
//
//        // Associer le véhicule au livreur
//        livreur.setVehicule(vehicule);
//
//        // Sauvegarder le véhicule et mettre à jour le livreur
//        Vehicule savedVehicule = vehiculeRepository.save(vehicule);
//        livreurRepository.save(livreur);
//
//        // Récupérer l'admin (on suppose qu'il y en a un seul)
//        Admin admin = StreamSupport.stream(adminRepository.findAll().spliterator(), false)
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("Admin not found"));
//        // Créer et envoyer une notification à l'admin
//        String message = "Un nouveau véhicule a été soumis par le livreur " + livreur.getFullName();
//        notificationService.createNotificationForAdmin(admin, savedVehicule, message);
//        return savedVehicule;
//    }
//
//
//    // Approuver un véhicule et supprimer tout motif existant
//    public Vehicule approveVehicule(Integer vehiculeId) {
//        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
//                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));
//
//        // Vérifier si un motif est associé, et le supprimer s'il est présent
//        if (vehicule.getMotif() != null) {
//            Motif motif = vehicule.getMotif();
//
//            // Dissocier le motif du véhicule avant suppression
//            vehicule.setMotif(null);
//            vehiculeRepository.save(vehicule);  // Sauvegarder le véhicule sans le motif
//
//            // Supprimer ensuite le motif de la base de données
//            motifRepository.delete(motif);
//        }
//
//        // Approuver le véhicule
//        vehicule.setApprouve(true);
//        vehicule.setRejected(false);  // Le véhicule n'est plus rejeté
//
//        // Sauvegarder le véhicule approuvé
//        return vehiculeRepository.save(vehicule);
//    }
//
//    // Rejeter un véhicule avec un motif
//    public Vehicule rejectVehicule(Integer vehiculeId, String motifDescription) {
//        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
//                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));
//
//        // Vérifier si un motif existe déjà pour ce véhicule
//        Motif motif;
//        if (vehicule.getMotif() != null) {
//            // Si un motif existe déjà, le mettre à jour avec la nouvelle description
//            motif = vehicule.getMotif();
//            motif.setDescription(motifDescription);
//        } else {
//            // Si aucun motif n'existe, en créer un nouveau
//            motif = new Motif();
//            motif.setDescription(motifDescription);
//            motif.setVehicule(vehicule);
//            vehicule.setMotif(motif);
//        }
//
//        // Marquer le véhicule comme rejeté
//        vehicule.setApprouve(false);
//        vehicule.setRejected(true);
//
//        // Sauvegarder les modifications
//        motifRepository.save(motif);
//        return vehiculeRepository.save(vehicule);
//    }
//
//
//    // Récupérer tous les véhicules pour l'admin
//    public List<Vehicule> getAllVehicules() {
//        return vehiculeRepository.findAll();
//    }
//
//    // Mise à jour d'un véhicule
//    public Vehicule updateVehicule(Integer vehiculeId, VehiculeDto vehiculeDto) {
//        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
//                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));
//
//        vehicule.setMarque(vehiculeDto.getMarque());
//        vehicule.setModele(vehiculeDto.getModele());
//        vehicule.setImmatriculation(vehiculeDto.getImmatriculation());
//        vehicule.setApprouve(false);  // Par défaut, le véhicule n'est pas approuvé
////        vehicule.setRejected(false);
//
//        return vehiculeRepository.save(vehicule);
//    }
//
//    // Récupérer un véhicule par ID
//    public Vehicule getVehiculeById(Integer vehiculeId) {
//        return vehiculeRepository.findById(vehiculeId)
//                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));
//    }
//
//    // Récupérer tous les véhicules pour un livreur
//    public List<Vehicule> getVehiculesByLivreur(Integer livreurId) {
//        Livreur livreur = livreurRepository.findById(livreurId)
//                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livreurId));
//
//        return vehiculeRepository.findByLivreur(livreur);
//    }
//
////    public void deleteVehicule(Integer vehiculeId) {
////        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
////                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));
////        vehiculeRepository.delete(vehicule);
////    }
//
//    @Transactional
//    public void deleteVehiculeById(Integer vehiculeId) {
//        // Recherchez le livreur associé au véhicule
//        Livreur livreur = livreurRepository.findByVehiculeId(vehiculeId);
//        if (livreur != null) {
//            // Dissocier le véhicule du livreur
//            livreur.setVehicule(null);
//            livreurRepository.save(livreur); // Enregistrer la mise à jour du livreur
//        }
//
//        // Rechercher toutes les notifications associées au véhicule et les dissocier
//        List<Notification> notifications = notificationRepository.findByVehicule_Id(vehiculeId);
//        for (Notification notification : notifications) {
//            notification.setVehicule(null);  // Supprimer la référence au véhicule
//            notificationRepository.save(notification); // Enregistrer la modification
//        }
//
//        // Supprimer le véhicule
//        vehiculeRepository.deleteById(vehiculeId);
//    }
//
//}
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

        if (vehicule.getMotif() != null) {
            Motif motif = vehicule.getMotif();
            vehicule.setMotif(null);
            vehiculeRepository.save(vehicule);
            motifRepository.delete(motif);
        }

        vehicule.setApprouve(true);
        vehicule.setRejected(false);

        return vehiculeRepository.save(vehicule);
    }

    // Rejeter un véhicule avec un motif
    public Vehicule rejectVehicule(Integer vehiculeId, String motifDescription) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));

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

        vehicule.setApprouve(false);
        vehicule.setRejected(true);

        motifRepository.save(motif);
        return vehiculeRepository.save(vehicule);
    }

    // Récupérer tous les véhicules pour l'admin
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    // Mise à jour d'un véhicule
    public Vehicule updateVehicule(Integer vehiculeId, VehiculeDto vehiculeDto, ImageData assuranceImage, ImageData carteGriseImage, List<ImageData> photos) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));

        vehicule.setMarque(vehiculeDto.getMarque());
        vehicule.setModele(vehiculeDto.getModele());
        vehicule.setImmatriculation(vehiculeDto.getImmatriculation());

        // Mise à jour des images si fournies
        if (assuranceImage != null) {
            vehicule.setAssurance(assuranceImage);
        }
        if (carteGriseImage != null) {
            vehicule.setCarteGrise(carteGriseImage);
        }
        if (photos != null && !photos.isEmpty()) {
            vehicule.setPhotos(photos);
        }

        vehicule.setApprouve(false);

        return vehiculeRepository.save(vehicule);
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
