package com.smart.gestion_colis.dtos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateClientDto {
    private String email;
    private String password;
    private String fullName;
    private String address;
    private String phoneNumber;
	private Boolean active;
    public Boolean getActive() {
        return active;
    }
    public UpdateClientDto setActive(Boolean active) {
        this.active = active;
        return this;
    }
    // Getter and Setter for email
    public String getEmail() {
        return email;
    }
    public UpdateClientDto setEmail(String email) {
        this.email = email;
        return this;
    }
    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public UpdateClientDto setPassword(String password) {
        this.password = password;
        return this;
    }

    // Getter and Setter for fullName
    public String getFullName() {
        return fullName;
    }

    public UpdateClientDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }



    public String getAddress() {
        return address;
    }


    public UpdateClientDto setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public UpdateClientDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    // Getter and Setter for userType
    @Override
    public String toString() {
        return "UpdateClientDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
