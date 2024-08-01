package com.smart.gestion_colis.dtos;

public class UpdateLivreurDto {
    private String email;
    private String password;
    private String fullName;
    private String vehicle;
    private String licence;
    private String address;
    private String phoneNumber;
	private Boolean active;


    public String getVehicle() {
        return vehicle;
    }
    public UpdateLivreurDto setVehicle(String vehicle) {
        this.vehicle = vehicle;
        return this;
    }
    public Boolean getActive() {
        return active;
    }
    public UpdateLivreurDto setActive(Boolean active) {
        this.active = active;
        return this;
    }
    public String getLicence() {
        return licence;
    }
    public UpdateLivreurDto setLicence(String licence) {
        this.licence = licence;
        return this;
    }
    // Getter and Setter for email
    public String getEmail() {
        return email;
    }
    public UpdateLivreurDto setEmail(String email) {
        this.email = email;
        return this;
    }
    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public UpdateLivreurDto setPassword(String password) {
        this.password = password;
        return this;
    }

    // Getter and Setter for fullName
    public String getFullName() {
        return fullName;
    }

    public UpdateLivreurDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }



    public String getAddress() {
        return address;
    }


    public UpdateLivreurDto setAddress(String address) {
        this.address = address;
        return this;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public UpdateLivreurDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    // Getter and Setter for userType


    @Override
    public String toString() {
        return "UpdateLivreurDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", licence='" + licence + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
