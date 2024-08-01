package com.smart.gestion_colis.services;

import com.smart.gestion_colis.dtos.*;
import com.smart.gestion_colis.entities.*;
import com.smart.gestion_colis.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Livreur signupLivreur(RegisterLivreurDto input, ImageData imageData) {
        var livreur = new Livreur();

        livreur.setFullName(input.getFullName());
        livreur.setEmail(input.getEmail());
        livreur.setLicence(input.getLicence());
        livreur.setVehicle(input.getVehicle());
        livreur.setAddress(input.getAddress());
        livreur.setPhoneNumber(input.getPhoneNumber());
        livreur.setPassword(passwordEncoder.encode(input.getPassword()));
        livreur.setImageData(imageData);
        livreur.setActive(true);

        return userRepository.save(livreur);
    }

    public void deleteLivreur(Long livreurId) {
        // Vérifier si le livreur existe avant de le supprimer
        Livreur livreur = (Livreur) userRepository.findById(livreurId)
                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livreurId));

        // Supprimer le livreur de la base de données
        userRepository.delete(livreur);
    }

    public Livreur updateLivreur(Long livreurId, UpdateLivreurDto input, ImageData imageData) {
        // Retrieve existing Livreur entity from the database
        Livreur livreur = (Livreur) userRepository.findById(livreurId)
                .orElseThrow(() -> new RuntimeException("Livreur not found with id: " + livreurId));

        // Update the Livreur entity with new data
        if (input.getFullName() != null) {
            livreur.setFullName(input.getFullName());
        }if (input.getActive() != null) {
            livreur.setActive(input.getActive());
        }
        if (input.getEmail() != null) {
            livreur.setEmail(input.getEmail());
        }
        if (input.getLicence() != null) {
            livreur.setLicence(input.getLicence());
        }
        if (input.getVehicle() != null) {
            livreur.setVehicle(input.getVehicle());
        }
        if (input.getAddress() != null) {
            livreur.setAddress(input.getAddress());
        }
        if (input.getPhoneNumber() != null) {
            livreur.setPhoneNumber(input.getPhoneNumber());
        }
        if (input.getPassword() != null) {
            livreur.setPassword(passwordEncoder.encode(input.getPassword()));
        }
        if (imageData != null) {
            livreur.setImageData(imageData);
        }

        // Save the updated Livreur entity back to the repository
        return userRepository.save(livreur);
    }

    public Client signupClient(RegisterClientDto input) {
        var client = new Client();

        client.setFullName(input.getFullName());
        client.setEmail(input.getEmail());
        client.setAddress(input.getAddress());
        client.setPhoneNumber(input.getPhoneNumber());

        client.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(client);
    }
    public Admin signupAdmin(RegisterAdminDto input) {
        var admin = new Admin();

        admin.setFullName(input.getFullName());
        admin.setEmail(input.getEmail());
        admin.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(admin);
    }
    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
