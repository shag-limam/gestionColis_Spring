package com.smart.gestion_colis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.gestion_colis.dtos.ColisDto;
import com.smart.gestion_colis.entities.Colis;
import com.smart.gestion_colis.entities.ImageData;
import com.smart.gestion_colis.services.ColisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/colis")
public class ColisController {

    @Autowired
    private ColisService colisService;

    // Créer un nouveau Colis
    @PostMapping("/createColis")
    public ResponseEntity<Colis> createColis(
            @RequestPart("colis") String colisDtoJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ColisDto colisDto = objectMapper.readValue(colisDtoJson, ColisDto.class);

            ImageData imageData = null;
            if (image != null) {
                imageData = new ImageData();
                imageData.setName(image.getOriginalFilename());
                imageData.setType(image.getContentType());
                imageData.setImageData(image.getBytes());
            }

            Colis createdColis = colisService.createColis(colisDto, imageData);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdColis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create colis", e);
        }
    }

    // Mettre à jour un Colis existant
    @PutMapping("/updateColis/{colisId}")
    public ResponseEntity<Colis> updateColis(
            @PathVariable Integer colisId,
            @RequestPart("colis") String colisDtoJson,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ColisDto colisDto = objectMapper.readValue(colisDtoJson, ColisDto.class);

            ImageData imageData = null;
            if (image != null) {
                imageData = new ImageData();
                imageData.setName(image.getOriginalFilename());
                imageData.setType(image.getContentType());
                imageData.setImageData(image.getBytes());
            }

            Colis updatedColis = colisService.updateColis(colisId, colisDto, imageData);
            return ResponseEntity.ok(updatedColis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update colis", e);
        }
    }

    // Supprimer un Colis par son ID
    @DeleteMapping("/deleteColis/{colisId}")
    public ResponseEntity<String> deleteColis(@PathVariable Integer colisId) {
        try {
            colisService.deleteColis(colisId);
            return ResponseEntity.ok("Colis with ID " + colisId + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete colis: " + e.getMessage());
        }
    }

    // Lister tous les Colis
    @GetMapping("/list")
    public ResponseEntity<List<Colis>> getAllColis() {
        List<Colis> colisList = colisService.getAllColis();
        return ResponseEntity.ok(colisList);
    }

    // Récupérer un Colis par son ID
    @GetMapping("/{colisId}")
    public ResponseEntity<Colis> getColisById(@PathVariable Integer colisId) {
        Colis colis = colisService.getColisById(colisId);
        return ResponseEntity.ok(colis);
    }

    // Ajouter un endpoint pour récupérer les colis disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Colis>> getColisDisponibles() {
        List<Colis> colisDisponibles = colisService.getColisDisponibles();
        return ResponseEntity.ok(colisDisponibles);
    }
}
