package com.smart.gestion_colis.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Accessors(chain = true)
@Table(name = "Livreur")
public class Livreur extends User {

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String licence;



    // The owning side of the relationship with Vehicule
    @JsonManagedReference  // Propriétaire de la relation
    @OneToOne
    @JoinColumn(name = "vehicule_id", referencedColumnName = "id")
    private Vehicule vehicule;  // This should match the 'mappedBy' in Vehicule

    public String getAddress() {
        return address;
    }

    public Livreur setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Livreur setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    public String getLicence() {
        return licence;
    }
    public Livreur setLicence(String licence) {
        this.licence = licence;
        return this;
    }


}
