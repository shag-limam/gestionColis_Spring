package com.smart.gestion_colis.controllers;

import com.smart.gestion_colis.dtos.VehiculeDto;
import com.smart.gestion_colis.entities.Client;
import com.smart.gestion_colis.entities.Vehicule;
import com.smart.gestion_colis.services.VehiculeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    // Endpoint pour que le livreur soumette les détails de son véhicule
    @PostMapping("/livreur/{livreurId}")
    public ResponseEntity<Vehicule> addVehiculeToLivreur(
            @PathVariable Integer livreurId,
            @RequestBody VehiculeDto vehiculeDto) {

        Vehicule savedVehicule = vehiculeService.addVehiculeToLivreur(livreurId, vehiculeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicule);
    }

    // Endpoint pour que l'admin approuve un véhicule
    @PutMapping("/admin/approve/{vehiculeId}")
    public ResponseEntity<Vehicule> approveVehicule(@PathVariable Long vehiculeId) {
        Vehicule approvedVehicule = vehiculeService.approveVehicule(vehiculeId);
        return ResponseEntity.ok(approvedVehicule);
    }

    // Rejeter un véhicule
    @PutMapping("/admin/reject/{vehiculeId}")
    public ResponseEntity<Vehicule> rejectVehicule(@PathVariable Long vehiculeId) {
        Vehicule rejectedVehicule = vehiculeService.rejectVehicule(vehiculeId);
        return ResponseEntity.ok(rejectedVehicule);
    }

    // Récupérer tous les véhicules
    @GetMapping("/admin/list")
    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        List<Vehicule> vehicules = vehiculeService.getAllVehicules();
        return ResponseEntity.ok(vehicules);
    }

}
