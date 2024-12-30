package com.smart.gestion_colis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.gestion_colis.dtos.VehiculeDto;
import com.smart.gestion_colis.entities.ImageData;
import com.smart.gestion_colis.entities.Vehicule;
import com.smart.gestion_colis.services.VehiculeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    // Endpoint pour que le livreur soumette les détails de son véhicule avec assurance, carte grise et photos
    // Endpoint pour que le livreur soumette les détails de son véhicule avec assurance, carte grise et photos
    @PostMapping("/livreur/{livreurId}")
    public ResponseEntity<?> addVehiculeToLivreur(
            @PathVariable Integer livreurId,
            @RequestPart("vehicule") String vehiculeDtoJson,
            @RequestPart(value = "assurance", required = false) MultipartFile assuranceFile,
            @RequestPart(value = "carteGrise", required = false) MultipartFile carteGriseFile,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photosFiles) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            VehiculeDto vehiculeDto = objectMapper.readValue(vehiculeDtoJson, VehiculeDto.class);

            // Process assurance file if it exists
            ImageData assuranceImage = null;
            if (assuranceFile != null) {
                assuranceImage = new ImageData(assuranceFile.getOriginalFilename(),
                        assuranceFile.getContentType(),
                        assuranceFile.getBytes());
            }

            // Process carte grise file if it exists
            ImageData carteGriseImage = null;
            if (carteGriseFile != null) {
                carteGriseImage = new ImageData(carteGriseFile.getOriginalFilename(),
                        carteGriseFile.getContentType(),
                        carteGriseFile.getBytes());
            }

            // Process photos if they exist
            List<ImageData> photos = new ArrayList<>();
            if (photosFiles != null && !photosFiles.isEmpty()) {
                for (MultipartFile photoFile : photosFiles) {
                    ImageData photo = new ImageData(photoFile.getOriginalFilename(),
                            photoFile.getContentType(),
                            photoFile.getBytes());
                    photos.add(photo);
                }
            }

            // Appel du service pour enregistrer le véhicule
            Vehicule savedVehicule = vehiculeService.addVehiculeToLivreur(livreurId, vehiculeDto, assuranceImage, carteGriseImage, photos);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicule);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du véhicule : " + e.getMessage());
        }
    }

//    @PostMapping("/livreur/{livreurId}")
//    public ResponseEntity<Vehicule> addVehiculeToLivreur(
//            @PathVariable Integer livreurId,
//            @RequestPart("vehicule") String vehiculeDtoJson,
//            @RequestPart("assurance") MultipartFile assuranceFile,
//            @RequestPart("carteGrise") MultipartFile carteGriseFile,
//            @RequestPart("photos") List<MultipartFile> photosFiles) {
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            // Convertir le JSON en objet VehiculeDto
//            VehiculeDto vehiculeDto = objectMapper.readValue(vehiculeDtoJson, VehiculeDto.class);
//
//            ImageData assuranceImage = new ImageData(assuranceFile.getOriginalFilename(),
//                    assuranceFile.getContentType(),
//                    assuranceFile.getBytes());
//
//            ImageData carteGriseImage = new ImageData(carteGriseFile.getOriginalFilename(),
//                    carteGriseFile.getContentType(),
//                    carteGriseFile.getBytes());
//
//            List<ImageData> photos = new ArrayList<>();
//            for (MultipartFile photoFile : photosFiles) {
//                ImageData photo = new ImageData(photoFile.getOriginalFilename(),
//                        photoFile.getContentType(),
//                        photoFile.getBytes());
//                photos.add(photo);
//            }
//
//            Vehicule savedVehicule = vehiculeService.addVehiculeToLivreur(livreurId, vehiculeDto, assuranceImage, carteGriseImage, photos);
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicule);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    // Endpoint pour que l'admin approuve un véhicule
    @PutMapping("/admin/approve/{vehiculeId}")
    public ResponseEntity<Vehicule> approveVehicule(@PathVariable Integer vehiculeId) {
        Vehicule approvedVehicule = vehiculeService.approveVehicule(vehiculeId);
        return ResponseEntity.ok(approvedVehicule);
    }

    // Rejeter un véhicule avec un motif
    @PutMapping("/admin/reject/{vehiculeId}")
    public ResponseEntity<Vehicule> rejectVehicule(
            @PathVariable Integer vehiculeId,
            @RequestBody Map<String, String> motif) {

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

    // Endpoint pour mettre à jour un véhicule
    @PutMapping("/{vehiculeId}")
    public ResponseEntity<Vehicule> updateVehicule(
            @PathVariable Integer vehiculeId,
            @RequestPart("vehicule") String vehiculeDtoJson,
            @RequestPart(value = "assurance", required = false) MultipartFile assuranceFile,
            @RequestPart(value = "carteGrise", required = false) MultipartFile carteGriseFile,
            @RequestPart(value = "photos", required = false) List<MultipartFile> photosFiles) {

        try {
            // Convertir le JSON en objet VehiculeDto
            ObjectMapper objectMapper = new ObjectMapper();
            VehiculeDto vehiculeDto = objectMapper.readValue(vehiculeDtoJson, VehiculeDto.class);

            ImageData assuranceImage = null;
            ImageData carteGriseImage = null;
            List<ImageData> photos = new ArrayList<>();

            if (assuranceFile != null) {
                assuranceImage = new ImageData(assuranceFile.getOriginalFilename(),
                        assuranceFile.getContentType(),
                        assuranceFile.getBytes());
            }

            if (carteGriseFile != null) {
                carteGriseImage = new ImageData(carteGriseFile.getOriginalFilename(),
                        carteGriseFile.getContentType(),
                        carteGriseFile.getBytes());
            }

            if (photosFiles != null) {
                for (MultipartFile photoFile : photosFiles) {
                    ImageData photo = new ImageData(photoFile.getOriginalFilename(),
                            photoFile.getContentType(),
                            photoFile.getBytes());
                    photos.add(photo);
                }
            }

            Vehicule updatedVehicule = vehiculeService.updateVehicule(vehiculeId, vehiculeDto, assuranceImage, carteGriseImage, photos);
            return ResponseEntity.ok(updatedVehicule);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint pour récupérer un véhicule par ID
    @GetMapping("/{vehiculeId}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable Integer vehiculeId) {
        Vehicule vehicule = vehiculeService.getVehiculeById(vehiculeId);
        return ResponseEntity.ok(vehicule);
    }

    // Récupérer tous les véhicules pour un livreur
    @GetMapping("/livreur/{livreurId}")
    public ResponseEntity<List<Vehicule>> getVehiculesByLivreur(@PathVariable Integer livreurId) {
        List<Vehicule> vehicules = vehiculeService.getVehiculesByLivreur(livreurId);
        return ResponseEntity.ok(vehicules);
    }

    // Supprimer un véhicule par son ID
    @DeleteMapping("/deleteVehicule/{vehiculeId}")
    public ResponseEntity<String> deleteVehicule(@PathVariable Integer vehiculeId) {
        try {
            vehiculeService.deleteVehiculeById(vehiculeId);
            return ResponseEntity.ok("Vehicule with ID " + vehiculeId + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete vehicule: " + e.getMessage());
        }
    }
}
