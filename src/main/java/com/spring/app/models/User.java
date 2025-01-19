package com.spring.app.models;

import jakarta.persistence.*;
import com.spring.app.enums.user_enums.Gender;

@Entity
@Table(name = "users")
public class User {
    public User(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    @Column(unique=true)
    private String email;

    @Enumerated(EnumType.STRING)  // Add this annotation
    @Column
    private Gender gender;
}