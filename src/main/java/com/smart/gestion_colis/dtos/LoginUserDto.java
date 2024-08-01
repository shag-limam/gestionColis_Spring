package com.smart.gestion_colis.dtos;

public class LoginUserDto {
    private String email;
    private String password;

    private String userType;

    public String getUserType() {
        return userType;
    }

    public LoginUserDto setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public LoginUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }


}