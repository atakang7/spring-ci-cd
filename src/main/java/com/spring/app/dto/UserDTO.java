package com.spring.app.dto;
import com.spring.app.enums.user_enums.Gender;

public class UserDTO {
    private String username;
    private String email;

    @java.lang.Override
    public java.lang.String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                '}';
    }

    private Gender gender;

    // Default constructor
    public UserDTO() {}

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}