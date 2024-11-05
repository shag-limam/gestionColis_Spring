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
@RequestMapping("api/users")
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

    @GetMapping("/listClient")
    public ResponseEntity<List<Client>> allClients() {
        List <Client> clients = userService.allClients();

        return ResponseEntity.ok(clients);
    }
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
            System.out.println("Reçu JSON client : " + updateClientDtoJson);

            ObjectMapper objectMapper = new ObjectMapper();
            UpdateClientDto updateClientDto;
            try {
                updateClientDto = objectMapper.readValue(updateClientDtoJson, UpdateClientDto.class);
                System.out.println("Données du client converties avec succès.");
            } catch (Exception e) {
                System.err.println("Erreur de conversion du JSON : " + e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  // Retourne 400 en cas d'erreur
            }

            // Process the photo if provided
            ImageData imageData = null;
            if (photo != null) {
                imageData = new ImageData();
                imageData.setName(photo.getOriginalFilename());
                imageData.setType(photo.getContentType());
                imageData.setImageData(photo.getBytes());
                System.out.println("Image traitée avec succès.");
            }

            Client updatedClient = userService.updateClient(clientId, updateClientDto, imageData);
            return ResponseEntity.ok(updatedClient);

        } catch (Exception e) {
            System.err.println("Erreur inattendue lors de la mise à jour : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Retourne 500 en cas d'erreur serveur
        }
    }


    @PutMapping("/updateClientStatus/{clientId}")
    public ResponseEntity<Client> updateClientStatus(@PathVariable Long clientId, @RequestBody UpdateClientDto updateClientDto) {
        try {
            Client updatedClient = userService.updateClientStatus(clientId, updateClientDto);
            return ResponseEntity.ok(updatedClient);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Retourne 404 si le client n'est pas trouvé
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Retourne 500 en cas d'erreur serveur
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

    @GetMapping("/listC/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable Long clientId) {
        try {
            Client client = userService.findClientById(clientId);
            if (client != null) {
                return ResponseEntity.ok(client);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
    @PostMapping("/signupLivreur")
    public ResponseEntity<Livreur> registerLivreur(
            @RequestPart("livreur") String registerLivreurDtoJson,
            @RequestPart("photo") MultipartFile photo) {
        try {
            // Convertir le JSON en objet DTO
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterLivreurDto registerLivreurDto = objectMapper.readValue(registerLivreurDtoJson, RegisterLivreurDto.class);

            // Traiter la photo
            ImageData imageData = new ImageData();
            imageData.setName(photo.getOriginalFilename());
            imageData.setType(photo.getContentType());
            imageData.setImageData(photo.getBytes());

            // Enregistrer le livreur
            Livreur registeredLivreur = userService.signupLivreur(registerLivreurDto, imageData);

            return ResponseEntity.ok(registeredLivreur);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // Si le JSON est mal formé ou le fichier corrompu
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Erreur générique du serveur
        }
    }


    @DeleteMapping("/deleteLivreur/{livreurId}")
    public ResponseEntity<String> deleteLivreur(@PathVariable Long livreurId) {
        try {
            // Appel du service pour supprimer le livreur
            userService.deleteLivreur(livreurId);
            return ResponseEntity.ok("Livreur with ID " + livreurId + " has been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete livreur: " + e.getMessage());
        }
    }

    @GetMapping("/listLivreur")
    public ResponseEntity<List<Livreur>> allLivreurs() {
        List <Livreur> livreurs = userService.allLivreurs();
        return ResponseEntity.ok(livreurs);
    }



    @GetMapping("/listL/{livreurId}")
    public ResponseEntity<Livreur> getLivreurById(@PathVariable Integer livreurId) {
        try {
            Livreur livreur = userService.findLivreurById(livreurId);
            if (livreur != null) {
                return ResponseEntity.ok(livreur);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
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
            Livreur updatedLivreur = userService.updateLivreur(livreurId, pdateLivreurDto, imageData);

            return ResponseEntity.ok(updatedLivreur);
        } catch (IOException e) {
            throw new RuntimeException("Failed to process request", e); // Handle exception appropriately
        }
    }

    @PutMapping("/updateLivreurStatus/{livreurId}")
    public ResponseEntity<Livreur> updateLivreurStatus(@PathVariable Long livreurId, @RequestBody UpdateLivreurDto updateLivreurDto) {
        try {
            Livreur updatedLivreur = userService.updateLivreurStatus(livreurId, updateLivreurDto);
            return ResponseEntity.ok(updatedLivreur);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // Retourne 404 si le client n'est pas trouvé
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);  // Retourne 500 en cas d'erreur serveur
        }
    }
}