package com.smart.gestion_colis.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
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

    @Column(nullable = false)
    private String vehicle;

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
    public String getVehicle() {
        return vehicle;
    }

    public Livreur setVehicle(String vehicle) {
        this.vehicle = vehicle;
        return this;
    }

}