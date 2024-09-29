package com.smart.gestion_colis.controllers;

import com.smart.gestion_colis.dtos.VehiculeDto;
import com.smart.gestion_colis.entities.Vehicule;
import com.smart.gestion_colis.services.VehiculeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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

    // Rejeter un véhicule avec un motif
    @PutMapping("/admin/reject/{vehiculeId}")
    public ResponseEntity<Vehicule> rejectVehicule(
            @PathVariable Long vehiculeId,
            @RequestBody Map<String, String> motif) {

        // Récupérer la description du motif du corps de la requête
        String motifDescription = motif.get("motifDescription");

        if (motifDescription == null || motifDescription.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Vehicule rejectedVehicule = vehiculeService.rejectVehicule(vehiculeId, motifDescription);
        return ResponseEntity.ok(rejectedVehicule);
    }

    // Récupérer tous les véhicules pour l'admin
    @GetMapping("/admin/list")
    public ResponseEntity<List<Vehicule>> getAllVehicules() {
        List<Vehicule> vehicules = vehiculeService.getAllVehicules();
        return ResponseEntity.ok(vehicules);
    }
}
