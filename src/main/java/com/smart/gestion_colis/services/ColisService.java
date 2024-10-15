package com.smart.gestion_colis.services;

import com.smart.gestion_colis.dtos.ColisDto;
import com.smart.gestion_colis.entities.Client;
import com.smart.gestion_colis.entities.Colis;
import com.smart.gestion_colis.entities.ImageData;
import com.smart.gestion_colis.repositories.ClientRepository;
import com.smart.gestion_colis.repositories.ColisRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@AllArgsConstructor
public class ColisService {

    private final ColisRepository colisRepository;
    private final ClientRepository clientRepository;

    public ColisService(ColisRepository colisRepository,
                        ClientRepository clientRepository) {
        this.colisRepository = colisRepository;
        this.clientRepository = clientRepository;
    }

    //Créer un nouveau Colis
    @Transactional
    public Colis createColis(ColisDto colisDto, ImageData imageData) {
        // Créer un nouvel objet Colis à partir du DTO
        Colis colis = new Colis();
        colis.setDescription(colisDto.getDescription());
        colis.setPoids(colisDto.getPoids());
        colis.setAdresseExpediteur(colisDto.getAdresseExpediteur());
        colis.setAdresseDestinataire(colisDto.getAdresseDestinataire());
        colis.setDateExpedition(colisDto.getDateExpedition());
        colis.setDateLivraisonPrevue(colisDto.getDateLivraisonPrevue());

        // Ajouter l'image si elle est présente
        if (imageData != null) {
            colis.setImageData(imageData);
        }

        // Récupérer et associer le client via son ID
        Optional<Client> clientOptional = clientRepository.findById(colisDto.getClientId());
        if (clientOptional.isPresent()) {
            colis.setClient(clientOptional.get());
        } else {
            throw new RuntimeException("Client not found with ID: " + colisDto.getClientId());
        }

        // Enregistrer le colis dans la base de données
        return colisRepository.save(colis);
    }

    //Mettre à jour un Colis existant
    @Transactional
    public Colis updateColis(Integer colisId, ColisDto colisDto, ImageData imageData) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Colis not found with id: " + colisId));

        // Mise à jour des champs du colis
        colis.setDescription(colisDto.getDescription());
        colis.setPoids(colisDto.getPoids());
        colis.setAdresseExpediteur(colisDto.getAdresseExpediteur());
        colis.setAdresseDestinataire(colisDto.getAdresseDestinataire());
        colis.setDateExpedition(colisDto.getDateExpedition());
        colis.setDateLivraisonPrevue(colisDto.getDateLivraisonPrevue());

        if (imageData != null) {
            colis.setImageData(imageData);
        }

        return colisRepository.save(colis);
    }

    //Supprimer un Colis par son ID
    public void deleteColis(Integer colisId) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Colis not found with id: " + colisId));
        colisRepository.delete(colis);
    }

    //Récupérer tous les Colis
    public List<Colis> getAllColis() {
        return colisRepository.findAll();
    }

    //Récupérer un Colis par son ID
    public Colis getColisById(Integer colisId) {
        return colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Colis not found with id: " + colisId));
    }

    // Ajouter une méthode pour récupérer les colis disponibles (sans livraison)
    public List<Colis> getColisDisponibles() {
        return colisRepository.findByLivraisonIsNull();
    }
}
