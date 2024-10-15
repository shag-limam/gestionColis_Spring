package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Livreur;
import com.smart.gestion_colis.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByLivreur(Livreur livreur);
}
