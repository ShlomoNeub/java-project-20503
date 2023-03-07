package com.example.demo.db.entities;

import com.example.demo.db.Validatable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import com.example.demo.db.entities.Users;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Collection;

@Entity
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    @OneToMany(mappedBy = "profile")
    @JsonBackReference
    @Nullable
    Collection<Users> users;

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Nullable
    public Collection<Users> getUsers() {
        return users;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull  String firstName) {
        this.firstName = firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@NotNull  String email) {
        this.email = email;
    }

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isValid() {
        return true;
    }
}
