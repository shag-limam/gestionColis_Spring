package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Colis;
import com.smart.gestion_colis.entities.Livraison;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LivraisonRepository extends CrudRepository<Livraison, Integer> {

    Optional<Livraison> findById(Long id);
    public List<Livraison> findAll();
}