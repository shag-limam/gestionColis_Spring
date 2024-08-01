package com.smart.gestion_colis.dtos;

public class RegisterUserDto {
    private String email;
    private String password;
    private String fullName;
    // "Admin", "Livreur", or "Client"


    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public RegisterUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    // Getter and Setter for fullName
    public String getFullName() {
        return fullName;
    }

    public RegisterUserDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    // Getter and Setter for userType


    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
//                ", vehicle='" + vehicle + '\'' +
//                ", licence='" + licence + '\'' +
                '}';
    }
}








