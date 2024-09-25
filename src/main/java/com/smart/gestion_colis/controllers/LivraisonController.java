package com.smart.gestion_colis.controllers;

import com.smart.gestion_colis.dtos.LivraisonDto;
import com.smart.gestion_colis.entities.Livraison;
import com.smart.gestion_colis.services.LivraisonService;
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

    @GetMapping
    public List<Livraison> getAllLivraisons() {
        return livraisonService.getAllLivraisons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livraison> getLivraisonById(@PathVariable Long id) {
        Livraison livraison = livraisonService.getLivraisonById(id);
        if (livraison != null) {
            return ResponseEntity.ok(livraison);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Livraison> createLivraison(@RequestBody LivraisonDto livraisonDto) {
        Livraison livraison = livraisonService.createLivraison(livraisonDto);
        return ResponseEntity.ok(livraison);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livraison> updateLivraison(@PathVariable Long id, @RequestBody LivraisonDto livraisonDto) {
        Livraison livraison = livraisonService.updateLivraison(id, livraisonDto);
        return ResponseEntity.ok(livraison);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivraison(@PathVariable Integer id) {
        livraisonService.deleteLivraison(id);
        return ResponseEntity.noContent().build();
    }
}
