package com.smart.gestion_colis.repositories;

import com.smart.gestion_colis.entities.Motif;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotifRepository extends JpaRepository<Motif, Long> {
}
