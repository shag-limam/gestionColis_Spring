package com.smart.gestion_colis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.gestion_colis.dtos.*;
import com.smart.gestion_colis.entities.*;
import com.smart.gestion_colis.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }


//    @PostMapping("/signupClient")
//    public ResponseEntity<Client> createClient(@RequestBody RegisterClientDto registerClientDto) {
//        Client registeredClient = userService.createClient(registerClientDto);
//
//        return ResponseEntity.ok(registeredClient);
//    }
  @PostMapping("/signupClient")
  public ResponseEntity<Client> registerClient(
            @RequestPart("client") String registerClientDtoJson,
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Convert JSON string to RegisterClientDto object
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterClientDto registerClientDto = objectMapper.readValue(registerClientDtoJson, RegisterClientDto.class);

            // Process the photo
            ImageData imageData = new ImageData();
            imageData.setName(photo.getOriginalFilename());
            imageData.setType(photo.getContentType());
            imageData.setImageData(photo.getBytes());

            // Register Client
            Client registeredClient = userService.signupClient(registerClientDto, imageData);

            return ResponseEntity.ok(registeredClient);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e); // Use RuntimeException for simplicity
        }
    }
    @PutMapping("/updateClient/{clientId}")
    public ResponseEntity<Client> updateClient(
            @PathVariable Long clientId,
            @RequestPart("client") String updateClientDtoJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
             try {
                // Convert JSON string to UpdateAdminDto object
                 ObjectMapper objectMapper = new ObjectMapper();
                  UpdateClientDto updateClientDto  = objectMapper.readValue(updateClientDtoJson, UpdateClientDto.class);

                // Optional: Process the photo if provided
                ImageData imageData = null;
                if (photo != null) {
                    imageData = new ImageData();
                    imageData.setName(photo.getOriginalFilename());
                    imageData.setType(photo.getContentType());
                    imageData.setImageData(photo.getBytes());
                }

                // Update Livreur using AuthenticationService
                Client updatedClient = userService.updateClient(clientId, updateClientDto, imageData);

                return ResponseEntity.ok(updatedClient);
            } catch (IOException e) {
                throw new RuntimeException("Failed to process request", e); // Handle exception appropriately
            }
        }
    @DeleteMapping("/deleteClient/{clientId}")
    public ResponseEntity<String> deleteClient(@PathVariable Long clientId) {
        try {
            // Appel du service pour supprimer le livreur
            userService.deleteClient(clientId);
            return ResponseEntity.ok("Client with ID " + clientId + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete client: " + e.getMessage());
        }
    }
    @PostMapping("/signupAdmin")
    public ResponseEntity<Admin> registerAdmin(
            @RequestPart("admin") String registerAdminDtoJson,
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Convert JSON string to RegisterClientDto object
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterAdminDto registerAdminDto = objectMapper.readValue(registerAdminDtoJson, RegisterAdminDto.class);

            // Process the photo
            ImageData imageData = new ImageData();
            imageData.setName(photo.getOriginalFilename());
            imageData.setType(photo.getContentType());
            imageData.setImageData(photo.getBytes());

            // Register Client
            Admin registeredAdmin = userService.signupAdmin(registerAdminDto, imageData);

            return ResponseEntity.ok(registeredAdmin);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e); // Use RuntimeException for simplicity
        }
    }
    @PutMapping("/updateAdmin/{adminId}")
    public ResponseEntity<Admin> updateAdmin(
            @PathVariable Long adminId,
            @RequestPart("admin") String updateAdminDtoJson,
            @RequestPart(value = "photo", required = false) MultipartFile photo) {
        try {
            // Convert JSON string to UpdateAdminDto object
            ObjectMapper objectMapper = new ObjectMapper();
            UpdateAdminDto updateAdminDto  = objectMapper.readValue(updateAdminDtoJson, UpdateAdminDto.class);

            // Optional: Process the photo if provided
            ImageData imageData = null;

            if (photo != null) {
                imageData = new ImageData();
                imageData.setName(photo.getOriginalFilename());
                imageData.setType(photo.getContentType());
                imageData.setImageData(photo.getBytes());
            }

            // Update Livreur using AuthenticationService
            Admin updatedAdmin = userService.updateAdmin(adminId, updateAdminDto, imageData);

            return ResponseEntity.ok(updatedAdmin);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process request", e); // Handle exception appropriately
        }
    }
    @DeleteMapping("/deleteAdmin/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long adminId) {
        try {
            // Appel du service pour supprimer le admin
            userService.deleteAdmin(adminId);
            return ResponseEntity.ok("Admin with ID " + adminId + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete client: " + e.getMessage());
        }
    }
}