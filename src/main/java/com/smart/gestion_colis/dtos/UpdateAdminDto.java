package com.smart.gestion_colis.dtos;

public class UpdateAdminDto {
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
	private Boolean active;
    public Boolean getActive() {
        return active;
    }
    public UpdateAdminDto setActive(Boolean active) {
        this.active = active;
        return this;
    }
    // Getter and Setter for email
    public String getEmail() {
        return email;
    }
    public UpdateAdminDto setEmail(String email) {
        this.email = email;
        return this;
    }
    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public UpdateAdminDto setPassword(String password) {
        this.password = password;
        return this;
    }

    // Getter and Setter for fullName
    public String getFullName() {
        return fullName;
    }

    public UpdateAdminDto setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public UpdateAdminDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
    // Getter and Setter for userType
    @Override
    public String toString() {
        return "UpdateAdminDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
