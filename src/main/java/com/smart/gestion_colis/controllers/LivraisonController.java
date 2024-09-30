package com.smart.gestion_colis.controllers;

import com.smart.gestion_colis.dtos.ItineraireDto;
import com.smart.gestion_colis.entities.Livraison;
import com.smart.gestion_colis.services.LivraisonService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livraisons")
public class LivraisonController {

    private final LivraisonService livraisonService;

    public LivraisonController(LivraisonService livraisonService) {
        this.livraisonService = livraisonService;
    }

    // Créer une nouvelle Livraison avec un nouvel Itinéraire
    @PostMapping("/create")
    public ResponseEntity<Livraison> createLivraison(
            @RequestParam Integer colisId,
            @RequestParam Integer livreurId,
            @RequestBody ItineraireDto itineraireDto) {
        Livraison livraison = livraisonService.createLivraison(colisId, livreurId, itineraireDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(livraison);
    }

    // Mettre à jour le statut de la Livraison
    @PutMapping("/updateStatus/{livraisonId}")
    public ResponseEntity<Livraison> updateLivraisonStatus(@PathVariable Integer livraisonId, @RequestParam String statut) {
        Livraison updatedLivraison = livraisonService.updateLivraisonStatut(livraisonId, statut);
        return ResponseEntity.ok(updatedLivraison);
    }

    // Récupérer toutes les Livraisons
    @GetMapping("/list")
    @Transactional
    public ResponseEntity<List<Livraison>> getAllLivraisons() {
        return ResponseEntity.ok(livraisonService.getAllLivraisons());
    }

    // Récupérer une Livraison par son ID
    @GetMapping("/{livraisonId}")
    @Transactional
    public ResponseEntity<Livraison> getLivraisonById(@PathVariable Integer livraisonId) {
        Livraison livraison = livraisonService.getLivraisonById(livraisonId);
        return ResponseEntity.ok(livraison);
    }
}