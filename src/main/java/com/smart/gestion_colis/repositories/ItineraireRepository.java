package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Client;
import com.smart.gestion_colis.entities.Itineraire;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItineraireRepository extends CrudRepository<Itineraire, Integer> {
    Optional<Itineraire> findById(Long id);

    public List<Itineraire> findAll();

}