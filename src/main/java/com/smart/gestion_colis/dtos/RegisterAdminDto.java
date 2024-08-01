package com.smart.gestion_colis.dtos;

public class RegisterAdminDto {
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;

    // Getter and Setter for email
    public String getphoneNumber() {
        return phoneNumber;
    }

    public RegisterAdminDto setphoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    public String getEmail() {
        return email;
    }

    public RegisterAdminDto setEmail(String email) {
        this.email = email;
        return this;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public RegisterAdminDto setPassword(String password) {
        this.password = password;
        return this;
    }

    // Getter and Setter for fullName
    public String getFullName() {
        return fullName;
    }

    public RegisterAdminDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    // Getter and Setter for userType


    @Override
    public String toString() {
        return "RegisterAdminDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}