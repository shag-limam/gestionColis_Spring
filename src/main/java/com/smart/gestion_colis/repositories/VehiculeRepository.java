package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Livreur;
import com.smart.gestion_colis.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
    List<Vehicule> findByLivreur(Livreur livreur);
    Optional<Vehicule> findById(Integer id);

    // Méthode pour trouver tous les véhicules approuvés
    Long countByApprouveTrue();
    // Méthode pour trouver tous les véhicules rejetés
    Long countByRejectedTrue();
}
