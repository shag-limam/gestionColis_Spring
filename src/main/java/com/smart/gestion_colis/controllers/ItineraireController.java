package com.smart.gestion_colis.controllers;

import com.smart.gestion_colis.dtos.ItineraireDto;
import com.smart.gestion_colis.entities.Itineraire;
import com.smart.gestion_colis.services.ItineraireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itineraires")
public class ItineraireController {

    private final ItineraireService itineraireService;

    public ItineraireController(ItineraireService itineraireService) {
        this.itineraireService = itineraireService;
    }

    @GetMapping
    public List<Itineraire> getAllItineraires() {
        return itineraireService.getAllItineraires();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Itineraire> getItineraireById(@PathVariable Long id) {
        Itineraire itineraire = itineraireService.getItineraireById(id);
        if (itineraire != null) {
            return ResponseEntity.ok(itineraire);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Itineraire> createItineraire(@RequestBody ItineraireDto itineraireDto) {
        Itineraire itineraire = itineraireService.createItineraire(itineraireDto);
        return ResponseEntity.ok(itineraire);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Itineraire> updateItineraire(@PathVariable Long id, @RequestBody ItineraireDto itineraireDto) {
        Itineraire itineraire = itineraireService.updateItineraire(id, itineraireDto);
        return ResponseEntity.ok(itineraire);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItineraire(@PathVariable Integer id) {
        itineraireService.deleteItineraire(id);
        return ResponseEntity.noContent().build();
    }
}
