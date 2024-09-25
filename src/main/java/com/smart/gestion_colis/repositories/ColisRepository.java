package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Colis;
import com.smart.gestion_colis.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ColisRepository extends CrudRepository<Colis, Integer> {

    Optional<Colis> findById(Long id);
    public List<Colis> findAll();

}
