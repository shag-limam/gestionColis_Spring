package com.smart.gestion_colis.dtos;

public class RegisterClientDto {
    private String email;
    private String password;
    private String fullName;
    private String address;
    private String phoneNumber;


    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public RegisterClientDto setEmail(String email) {
        this.email = email;
        return this;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public RegisterClientDto setPassword(String password) {
        this.password = password;
        return this;
    }

    // Getter and Setter for fullName
    public String getFullName() {
        return fullName;
    }

    public RegisterClientDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getAddress() {
        return address;
    }


    public RegisterClientDto setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public RegisterClientDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String toString() {
        return "RegisterLivreurDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}



