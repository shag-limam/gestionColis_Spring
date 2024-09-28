package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Client;
import com.smart.gestion_colis.entities.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LivreurRepository extends CrudRepository<Livreur, Integer> {

    Optional<Livreur> findByEmail(String email);
}
//public interface LivreurRepository extends JpaRepository<Livreur, Long> {