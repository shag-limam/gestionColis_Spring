package com.smart.gestion_colis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.gestion_colis.common.Constants;
import com.smart.gestion_colis.dtos.*;
import com.smart.gestion_colis.entities.*;
import com.smart.gestion_colis.responses.LoginResponse;
import com.smart.gestion_colis.services.AuthenticationService;
import com.smart.gestion_colis.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }
    @ModelAttribute(name = "userActive")
    public List<Long> userActive() {
        return Constants.USERS_ACTIV;
    }
    @PostMapping("/signupLivreur")
    public ResponseEntity<Livreur> registerLivreur(
            @RequestPart("livreur") String registerLivreurDtoJson,
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Convert JSON string to RegisterLivreurDto object
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterLivreurDto registerLivreurDto = objectMapper.readValue(registerLivreurDtoJson, RegisterLivreurDto.class);

            // Process the photo
            ImageData imageData = new ImageData();
            imageData.setName(photo.getOriginalFilename());
            imageData.setType(photo.getContentType());
            imageData.setImageData(photo.getBytes());

            // Register Livreur
            Livreur registeredLivreur = authenticationService.signupLivreur(registerLivreurDto, imageData);

            return ResponseEntity.ok(registeredLivreur);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e); // Use RuntimeException for simplicity
        }
    }

    @DeleteMapping("/deleteLivreur/{livreurId}")
    public ResponseEntity<String> deleteLivreur(@PathVariable Long livreurId) {
        try {
            // Appel du service pour supprimer le livreur
            authenticationService.deleteLivreur(livreurId);
            return ResponseEntity.ok("Livreur with ID " + livreurId + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete livreur: " + e.getMessage());
        }
    }

    @PutMapping("/updateLivreur/{livreurId}")
    public ResponseEntity<Livreur> updateLivreur(
            @PathVariable Long livreurId,
            @RequestPart("livreur") String updateLivreurDtoJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        try {
            // Convert JSON string to UpdateLivreurDto object
            ObjectMapper objectMapper = new ObjectMapper();
            UpdateLivreurDto pdateLivreurDto  = objectMapper.readValue(updateLivreurDtoJson, UpdateLivreurDto.class);

            // Optional: Process the photo if provided
            ImageData imageData = null;
            if (photo != null) {
                imageData = new ImageData();
                imageData.setName(photo.getOriginalFilename());
                imageData.setType(photo.getContentType());
                imageData.setImageData(photo.getBytes());
            }

            // Update Livreur using AuthenticationService
            Livreur updatedLivreur = authenticationService.updateLivreur(livreurId, pdateLivreurDto, imageData);

            return ResponseEntity.ok(updatedLivreur);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process request", e); // Handle exception appropriately
        }
    }

    @PostMapping("/signupClient")
    public ResponseEntity<Client> registerClient(@RequestBody RegisterClientDto registerClientDto) {
        Client registeredClient = authenticationService.signupClient(registerClientDto);

        return ResponseEntity.ok(registeredClient);
    }
    @PostMapping("/signupAdmin")
    public ResponseEntity<Admin> registerAdmin(@RequestBody RegisterAdminDto registerAdminDto) {
        Admin registeredAdmin = authenticationService.signupAdmin(registerAdminDto);

        return ResponseEntity.ok(registeredAdmin);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setUserType(authenticatedUser.getClass().getSimpleName());

        return ResponseEntity.ok(loginResponse);
    }
}