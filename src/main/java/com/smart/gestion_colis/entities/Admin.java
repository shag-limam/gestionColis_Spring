package com.smart.gestion_colis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {
    // Admin specific fields and methods
    @Column(nullable = false)
    private String phoneNumber;


    public String getphoneNumber() {
        return phoneNumber;
    }

    public Admin setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
