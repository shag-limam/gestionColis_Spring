package com.smart.gestion_colis.controllers;

import com.smart.gestion_colis.dtos.LivraisonDto;
import com.smart.gestion_colis.entities.Colis;
import com.smart.gestion_colis.entities.Livraison;
import com.smart.gestion_colis.entities.Livreur;
import com.smart.gestion_colis.services.LivraisonService;
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

    @PostMapping("/create")
    public ResponseEntity<Livraison> createLivraison(@RequestBody LivraisonDto livraisonDto) {
        try {
            Livraison livraison = livraisonService.createLivraison(livraisonDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(livraison);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PutMapping("/update/{livraisonId}")
    public ResponseEntity<Livraison> updateLivraison(
            @PathVariable Integer livraisonId,
            @RequestBody LivraisonDto livraisonDto) {
        try {
            Livraison updatedLivraison = livraisonService.updateLivraison(livraisonId, livraisonDto);
            return ResponseEntity.ok(updatedLivraison);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    // Mettre à jour le statut de la Livraison
    @PutMapping("/updateStatus/{livraisonId}")
    public ResponseEntity<Livraison> updateLivraisonStatus(
            @PathVariable Integer livraisonId, @RequestParam String statut) {
        try {
            Livraison updatedLivraison = livraisonService.updateLivraisonStatut(livraisonId, statut);
            return ResponseEntity.ok(updatedLivraison);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    // Récupérer toutes les Livraisons
    @GetMapping("/list")
    public ResponseEntity<List<Livraison>> getAllLivraisons() {
        return ResponseEntity.ok(livraisonService.getAllLivraisons());
    }

    // Récupérer une Livraison par son ID
    @GetMapping("/{livraisonId}")
    public ResponseEntity<Livraison> getLivraisonById(@PathVariable Integer livraisonId) {
        Livraison livraison = livraisonService.getLivraisonById(livraisonId);
        return ResponseEntity.ok(livraison);
    }
    // Endpoint pour récupérer les livraisons par référence de suivi
    @GetMapping("/colis/{referenceSuivi}")
    public List<Livraison> getLivraisonsByReferenceSuivi(@PathVariable String referenceSuivi) {
        return livraisonService.getLivraisonsByReferenceSuivi(referenceSuivi);
    }
    // Récupérer tous les livreurs disponibles
    @GetMapping("/availableLivreurs")
    public ResponseEntity<List<Livreur>> getAvailableLivreurs() {
        List<Livreur> livreurs = livraisonService.getAvailableLivreurs();
        return ResponseEntity.ok(livreurs);
    }
    // Récupérer tous les colis disponibles (non affectés à une livraison)
    @GetMapping("/availableColis")
    public ResponseEntity<List<Colis>> getAvailableColis() {
        List<Colis> colis = livraisonService.getAvailableColis();
        return ResponseEntity.ok(colis);
    }
    // Supprimer une Livraison par son ID
    @DeleteMapping("/delete/{livraisonId}")
    public ResponseEntity<String> deleteLivraison(@PathVariable Integer livraisonId) {
        try {
            livraisonService.deleteLivraison(livraisonId);
            return ResponseEntity.ok("Livraison with ID " + livraisonId + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete livraison: " + e.getMessage());
        }
    }
    // Récupérer les livraisons d'un livreur spécifique
    @GetMapping("/livreur/{livreurId}")
    public ResponseEntity<List<Livraison>> getLivraisonsByIdLivreur(@PathVariable Integer livreurId) {
        try {
            List<Livraison> livraisons = livraisonService.getLivraisonsByIdLivreur(livreurId);
            return ResponseEntity.ok(livraisons);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Nouveau endpoint pour démarrer une livraison
    @PutMapping("/demarrer/{livraisonId}")
    public ResponseEntity<Livraison> demarrerLivraison(@PathVariable Integer livraisonId) {
        try {
            Livraison livraison = livraisonService.demarrerLivraison(livraisonId);
            return ResponseEntity.ok(livraison);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
