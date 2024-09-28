package com.smart.gestion_colis.services;

        import com.smart.gestion_colis.dtos.VehiculeDto;
        import com.smart.gestion_colis.entities.Livreur;
        import com.smart.gestion_colis.entities.Vehicule;
        import com.smart.gestion_colis.repositories.LivreurRepository;
        import com.smart.gestion_colis.repositories.VehiculeRepository;
        import org.springframework.stereotype.Service;

        import java.util.List;

@Service
public class VehiculeService {

    private final LivreurRepository livreurRepository;
    private final VehiculeRepository vehiculeRepository;

    public VehiculeService(LivreurRepository livreurRepository, VehiculeRepository vehiculeRepository) {
        this.livreurRepository = livreurRepository;
        this.vehiculeRepository = vehiculeRepository;
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
        vehicule.setLivreur(livreur);

        // Associer le véhicule au livreur
        livreur.setVehicule(vehicule);

        // Sauvegarder le véhicule et mettre à jour le livreur
        vehiculeRepository.save(vehicule);
        livreurRepository.save(livreur);

        return vehicule;
    }

    // Approuver un véhicule
    public Vehicule approveVehicule(Long vehiculeId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));

        vehicule.setApprouve(true);
        vehicule.setRejected(false);  // Le véhicule n'est pas rejeté s'il est approuvé
        return vehiculeRepository.save(vehicule);
    }

    // Rejeter un véhicule
    public Vehicule rejectVehicule(Long vehiculeId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + vehiculeId));

        vehicule.setApprouve(false);  // Si rejeté, le véhicule n'est pas approuvé
        vehicule.setRejected(true);
        return vehiculeRepository.save(vehicule);
    }

    // Récupérer tous les véhicules pour l'admin
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }
}
