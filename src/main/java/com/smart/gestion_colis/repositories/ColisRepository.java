package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Colis;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ColisRepository extends CrudRepository<Colis, Integer> {

    // Trouver un colis par ID
    Optional<Colis> findById(Integer id);

    // Récupérer tous les colis
    List<Colis> findAll();

    // Trouver les colis sans livraison (pas encore affectés à une livraison)
    List<Colis> findByLivraisonIsNull();
    List<Colis> findByIsAvailableTrue();
    Optional<Colis> findByReferenceSuivi(String referenceSuivi);
}
