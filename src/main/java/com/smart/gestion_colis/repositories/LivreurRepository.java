package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Livreur;
import com.smart.gestion_colis.entities.Vehicule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivreurRepository extends CrudRepository<Livreur, Integer> {

    Optional<Livreur> findByEmail(String email);

//    Optional<Vehicule> findByVehiculeId(Integer id);
    Livreur findByVehiculeId(Integer vehiculeId);
    public List<Livreur> findAll();

    Optional<Livreur> findByVehicule(Vehicule vehicule);

    List<Livreur> findByIsAvailableTrue();

}
