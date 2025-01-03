package com.smart.gestion_colis.controllers;

import com.smart.gestion_colis.services.VehiculeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/statistiques")
public class StatistiqueController {

    private final VehiculeService vehiculeService;

    public StatistiqueController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    // Endpoint pour récupérer le nombre total de véhicules
//    @GetMapping("/total")
//    public ResponseEntity<Long> getTotalVehicules() {
//        long total = vehiculeService.countTotalVehicules();
//        return ResponseEntity.ok(total);
//    }

    // Endpoint pour récupérer le nombre de véhicules approuvés
    @GetMapping("/approuves")
    public ResponseEntity<Long> getTotalVehiculesApprouves() {
        long totalApproved = vehiculeService.getTotalVehiculesApprouves();
        return ResponseEntity.ok(totalApproved);
    }

    // Endpoint pour récupérer le nombre de véhicules rejetés
    @GetMapping("/rejetes")
    public ResponseEntity<Long> getTotalVehiculesRejetes() {
        long totalRejected = vehiculeService.countRejectedVehicules();
        return ResponseEntity.ok(totalRejected);
    }

}
