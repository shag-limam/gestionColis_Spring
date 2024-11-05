package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Admin;
import com.smart.gestion_colis.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByLivreurId(Integer livreurId);

    List<Notification> findByClientId(Integer clientId);

    List<Notification> findByLivraisonId(Integer livraisonId);

    List<Notification> findByAdminId(Integer adminId);

    List<Notification> findByVehicule_Id(Integer vehiculeId);
}
