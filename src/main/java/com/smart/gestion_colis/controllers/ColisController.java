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

@RestController
@RequestMapping("/api/colis")
public class ColisController {

    @Autowired
    private ColisService colisService;

//    @PostMapping("/createColis")
//    public ResponseEntity<Colis> createColis(
//            @RequestPart("colis") String colisDtoJson,
//            @RequestPart(value = "image", required = false) MultipartFile image) {
//        try {
//            // Convertir le JSON string en ColisDto
//            ObjectMapper objectMapper = new ObjectMapper();
//            ColisDto colisDto = objectMapper.readValue(colisDtoJson, ColisDto.class);
//
//            // Si une image est fournie, la traiter
//            ImageData imageData = null;
//            if (image != null) {
//                imageData = new ImageData();
//                imageData.setName(image.getOriginalFilename());
//                imageData.setType(image.getContentType());
//                imageData.setImageData(image.getBytes());
//            }
//
//            // Appel au service pour créer le colis
//            Colis createdColis = colisService.createColis(colisDto, imageData);
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdColis);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to create colis", e);
//        }
//    }
@PostMapping("/createColis")
public ResponseEntity<Colis> createColis(
    @RequestPart("colis") String colisDtoJson,
    @RequestPart("image") MultipartFile image) {
    try {
        // Convertir JSON string en ColisDto
        ObjectMapper objectMapper = new ObjectMapper();
        ColisDto colisDto = objectMapper.readValue(colisDtoJson, ColisDto.class);

        // Traiter le fichier associé (facultatif)
        ImageData imageData = null;
        if (image != null) {
            imageData = new ImageData();
            imageData.setName(image.getOriginalFilename());
            imageData.setType(image.getContentType());
            imageData.setImageData(image.getBytes());
        }

        // Appel au service pour créer le colis
        Colis createdColis = colisService.createColis(colisDto, imageData);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdColis);
    } catch (IOException e) {
        // Gestion d'erreur
        throw new RuntimeException("Failed to create colis", e);
    }
}




}
