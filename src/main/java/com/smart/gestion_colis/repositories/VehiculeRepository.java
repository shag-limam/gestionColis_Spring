package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Livreur;
import com.smart.gestion_colis.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule, Integer> {
    List<Vehicule> findByLivreur(Livreur livreur);
    Optional<Vehicule> findById(Integer id);
}
