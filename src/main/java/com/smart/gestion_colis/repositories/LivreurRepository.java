package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Client;
import com.smart.gestion_colis.entities.Colis;
import com.smart.gestion_colis.entities.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LivreurRepository extends CrudRepository<Livreur, Integer> {

    Optional<Livreur> findByEmail(String email);
    public List<Livreur> findAll();

//    List<Livreur> findByIsAvailableTrue();

//    @Query("SELECT l FROM Livreur l WHERE l.isAvailable = true")
//    @Query("SELECT l FROM public.livreur l WHERE l.is_available = true")
    List<Livreur> findByIsAvailableTrue();

}
//public interface LivreurRepository extends JpaRepository<Livreur, Long> {