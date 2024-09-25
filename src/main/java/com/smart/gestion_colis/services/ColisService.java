package com.smart.gestion_colis.services;

import com.smart.gestion_colis.dtos.ColisDto;
import com.smart.gestion_colis.entities.*;
import com.smart.gestion_colis.repositories.ClientRepository;
import com.smart.gestion_colis.repositories.ColisRepository;
import com.smart.gestion_colis.repositories.ItineraireRepository;
import com.smart.gestion_colis.repositories.LivraisonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ColisService {

    private final ColisRepository colisRepository;
    private final ItineraireRepository itineraireRepository;
    private final LivraisonRepository livraisonRepository;
    private final ClientRepository clientRepository;  // Assurez-vous d'injecter le repository pour le client

    public ColisService(ColisRepository colisRepository,
                        ItineraireRepository itineraireRepository,
                        LivraisonRepository livraisonRepository,
                        ClientRepository clientRepository) {
        this.colisRepository = colisRepository;
        this.itineraireRepository = itineraireRepository;
        this.livraisonRepository = livraisonRepository;
        this.clientRepository = clientRepository;
    }

    /**
     * Créer un nouveau Colis
     */
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

        // Récupérer et associer l'itinéraire si l'ID est présent
        if (colisDto.getItineraireId() != null) {
            Optional<Itineraire> itineraireOptional = itineraireRepository.findById(colisDto.getItineraireId());
            if (itineraireOptional.isPresent()) {
                colis.setItineraire(itineraireOptional.get());
            } else {
                throw new RuntimeException("Itinéraire not found with ID: " + colisDto.getItineraireId());
            }
        }

        // Récupérer et associer la livraison si l'ID est présent
        if (colisDto.getLivraisonId() != null) {
            Optional<Livraison> livraisonOptional = livraisonRepository.findById(colisDto.getLivraisonId());
            if (livraisonOptional.isPresent()) {
                colis.setLivraison(livraisonOptional.get());
            } else {
                throw new RuntimeException("Livraison not found with ID: " + colisDto.getLivraisonId());
            }
        }

        // Enregistrer le colis dans la base de données
        return colisRepository.save(colis);
    }

    /**
     * Mettre à jour un Colis existant
     */


    /**
     * Récupérer tous les Colis
     */
    public List<Colis> getAllColis() {
        return colisRepository.findAll();
    }

    /**
     * Récupérer un Colis par son ID
     */
    public Colis getColisById(Long colisId) {
        return colisRepository.findById(colisId)
                .orElseThrow(() -> new RuntimeException("Colis not found with id: " + colisId));
    }

    /**
     * Supprimer un Colis par son ID
     */
//    public void deleteColis(Long colisId) {
//        Colis colis = colisRepository.findById(colisId)
//                .orElseThrow(() -> new RuntimeException("Colis not found with id: " + colisId));
//        colisRepository.delete(colis);
//    }
}
