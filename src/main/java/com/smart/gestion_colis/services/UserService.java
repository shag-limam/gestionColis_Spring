package com.smart.gestion_colis.services;

import com.smart.gestion_colis.dtos.*;
import com.smart.gestion_colis.entities.*;
import com.smart.gestion_colis.repositories.ClientRepository;
import com.smart.gestion_colis.repositories.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ClientRepository clientRepository;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,ClientRepository clientRepository ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.clientRepository=clientRepository;

    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public List<Client> allClients() {
        List<Client> client = new ArrayList<>();
        clientRepository.findAll().forEach(client::add);
        return client;
    }



    public Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    public Client createClient(RegisterClientDto input) {
        var client = new Client();

        client.setFullName(input.getFullName());
        client.setEmail(input.getEmail());
        client.setAddress(input.getAddress());
        client.setPhoneNumber(input.getPhoneNumber());

        client.setPassword(passwordEncoder.encode(input.getPassword()));

        return userRepository.save(client);
    }

    public Client signupClient(RegisterClientDto input, ImageData imageData) {
        var client = new Client();

        client.setFullName(input.getFullName());
        client.setEmail(input.getEmail());
        client.setAddress(input.getAddress());
        client.setPhoneNumber(input.getPhoneNumber());
        client.setPassword(passwordEncoder.encode(input.getPassword()));
        client.setImageData(imageData);
        client.setActive(true);

        return userRepository.save(client);
    }


    public Client updateClient(Long clientId, UpdateClientDto input, ImageData imageData) {
        // Retrieve existing Client entity from the database
        Client client = (Client) userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));

        // Update the Livreur entity with new data
        if (input.getFullName() != null) {
            client.setFullName(input.getFullName());
        }if (input.getActive() != null) {
            client.setActive(input.getActive());
        }
        if (input.getEmail() != null) {
            client.setEmail(input.getEmail());
        }
        if (input.getAddress() != null) {
            client.setAddress(input.getAddress());
        }
        if (input.getPhoneNumber() != null) {
            client.setPhoneNumber(input.getPhoneNumber());
        }
        if (input.getPassword() != null) {
            client.setPassword(passwordEncoder.encode(input.getPassword()));
        }
        if (imageData != null) {
            client.setImageData(imageData);
        }

        // Save the updated Livreur entity back to the repository
        return userRepository.save(client);
    }


    public Client updateClientStatus(Long clientId,UpdateClientDto j) {
        Client client = (Client) userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));

        // Met à jour le statut actif du client
        client.setActive(j.getActive());


        // Sauvegarde les modifications
        return userRepository.save(client);
    }


    public void deleteClient(Long clientId) {
        // Vérifier si le livreur existe avant de le supprimer
        Client client = (Client) userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));

        // Supprimer le livreur de la base de données
        userRepository.delete(client);
    }
    public Admin signupAdmin(RegisterAdminDto input, ImageData imageData) {
        var admin = new Admin();

        admin.setFullName(input.getFullName());
        admin.setEmail(input.getEmail());
        admin.setphoneNumber(input.getphoneNumber());
        admin.setPassword(passwordEncoder.encode(input.getPassword()));
        admin.setImageData(imageData);
        admin.setActive(true);

        return userRepository.save(admin);
    }


    public Admin updateAdmin(Long adminId, UpdateAdminDto input, ImageData imageData) {
        // Retrieve existing Client entity from the database
        Admin admin = (Admin) userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));

        // Update the Admin entity with new data
        if (input.getFullName() != null) {
            admin.setFullName(input.getFullName());
        }if (input.getActive() != null) {
            admin.setActive(input.getActive());
        }
        if (input.getEmail() != null) {
            admin.setEmail(input.getEmail());
        }
        if (input.getPassword() != null) {
            admin.setPassword(passwordEncoder.encode(input.getPassword()));
        }
        if (imageData != null) {
            admin.setImageData(imageData);
        }

        // Save the updated Admin entity back to the repository
        return userRepository.save(admin);
    }

    public void deleteAdmin(Long adminId) {
        // Vérifier si le livreur existe avant de le supprimer
        Admin admin = (Admin) userRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + adminId));
        // Supprimer le livreur de la base de données
        userRepository.delete(admin);
    }
}
