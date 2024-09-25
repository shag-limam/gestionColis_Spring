package com.smart.gestion_colis.services;

import com.smart.gestion_colis.dtos.ItineraireDto;
import com.smart.gestion_colis.entities.Itineraire;
import com.smart.gestion_colis.repositories.ItineraireRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItineraireService {

    private final ItineraireRepository itineraireRepository;

    public ItineraireService(ItineraireRepository itineraireRepository) {
        this.itineraireRepository = itineraireRepository;
    }

    public List<Itineraire> getAllItineraires() {
        return itineraireRepository.findAll();
    }

    public Itineraire getItineraireById(Long id) {
        return itineraireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itineraire not found with id: " + id));
    }

    public Itineraire createItineraire(ItineraireDto itineraireDto) {
        Itineraire itineraire = new Itineraire();
        itineraire.setDeparturePoint(itineraireDto.getDeparturePoint());
        itineraire.setArrivalPoint(itineraireDto.getArrivalPoint());
        itineraire.setWaypoints(itineraireDto.getWaypoints());
        return itineraireRepository.save(itineraire);
    }

    public Itineraire updateItineraire(Long id, ItineraireDto itineraireDto) {
        Itineraire itineraire = itineraireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Itineraire not found with id: " + id));
        itineraire.setDeparturePoint(itineraireDto.getDeparturePoint());
        itineraire.setArrivalPoint(itineraireDto.getArrivalPoint());
        itineraire.setWaypoints(itineraireDto.getWaypoints());
        return itineraireRepository.save(itineraire);
    }

    public void deleteItineraire(Integer id) {
        itineraireRepository.deleteById(id);
    }
}
