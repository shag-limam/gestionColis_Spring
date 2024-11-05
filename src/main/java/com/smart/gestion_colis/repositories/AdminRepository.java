package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Admin;
import com.smart.gestion_colis.entities.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends CrudRepository<Admin, Integer> {


    List<Admin> findFirstByOrderById();

}
