
package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Client;
import com.smart.gestion_colis.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);
    Optional<Client> findById(Long email);

//    List<Client> findAll();
}