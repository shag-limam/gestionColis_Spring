package com.smart.gestion_colis.services;

import com.smart.gestion_colis.dtos.LivraisonDto;
import com.smart.gestion_colis.entities.Livraison;
import com.smart.gestion_colis.repositories.LivraisonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivraisonService {

    private final LivraisonRepository livraisonRepository;

    public LivraisonService(LivraisonRepository livraisonRepository) {
        this.livraisonRepository = livraisonRepository;
    }

    public List<Livraison> getAllLivraisons() {
        return livraisonRepository.findAll();
    }

    public Livraison getLivraisonById(Long id) {
        return livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison not found with id: " + id));
    }

    public Livraison createLivraison(LivraisonDto livraisonDto) {
        Livraison livraison = new Livraison();
        livraison.setDateLivraisonReelle(livraisonDto.getDateLivraisonReelle());
        livraison.setStatut(livraisonDto.getStatut());
        livraison.setLivreur(livraisonDto.getLivreur());
        return livraisonRepository.save(livraison);
    }

    public Livraison updateLivraison(Long id, LivraisonDto livraisonDto) {
        Livraison livraison = livraisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison not found with id: " + id));
        livraison.setDateLivraisonReelle(livraisonDto.getDateLivraisonReelle());
        livraison.setStatut(livraisonDto.getStatut());
        livraison.setLivreur(livraisonDto.getLivreur());
        return livraisonRepository.save(livraison);
    }

    public void deleteLivraison(Integer id) {
        livraisonRepository.deleteById(id);
    }
}
