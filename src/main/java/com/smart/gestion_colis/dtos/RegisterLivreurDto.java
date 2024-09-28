package com.smart.gestion_colis.dtos;

public class RegisterLivreurDto {
    private String email;
    private String password;
    private String fullName;
    private String licence;
    private String address;
    private String phoneNumber;


    public String getLicence() {
        return licence;
    }

    public RegisterLivreurDto setLicence(String licence) {
        this.licence = licence;
        return this;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public RegisterLivreurDto setEmail(String email) {
        this.email = email;
        return this;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public RegisterLivreurDto setPassword(String password) {
        this.password = password;
        return this;
    }

    // Getter and Setter for fullName
    public String getFullName() {
        return fullName;
    }

    public RegisterLivreurDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }



    public String getAddress() {
        return address;
    }


    public RegisterLivreurDto setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public RegisterLivreurDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    // Getter and Setter for userType


    @Override
    public String toString() {
        return "RegisterLivreurDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", licence='" + licence + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}