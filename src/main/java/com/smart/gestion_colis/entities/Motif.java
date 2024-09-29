package com.smart.gestion_colis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "motifs")
public class Motif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String description;  // La raison du refus


    @JsonIgnore  // Empêche la boucle lors de la sérialisation en JSON
    @OneToOne
    @JoinColumn(name = "vehicule_id", referencedColumnName = "id")
    private Vehicule vehicule;  // Référence au véhicule qui a été rejeté

    @Override
    public String toString() {
        return "Motif{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", vehiculeId=" + (vehicule != null ? vehicule.getId() : "null") +
                '}';
    }
}
