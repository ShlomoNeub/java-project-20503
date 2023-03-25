package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Profile implements Serializable, IEntity<Profile,Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;


    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile profile)) return false;
        return getFirstName().equals(profile.getFirstName()) && getLastName().equals(profile.getLastName()) && getEmail().equals(profile.getEmail()) && getPhoneNumber().equals(profile.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getEmail(), getPhoneNumber());
    }
    private boolean isValid() {
        boolean retVal = this.firstName != null;
        retVal &= this.lastName != null;
        retVal &= this.phoneNumber != null;
        return retVal;
    }
    public boolean isValid(Profile toValidate) {
        return toValidate.isValid();
    }

    @Override
    public int compareTo(@NotNull Profile o) {
        try {
            return this.equals(o)?0:this.id.compareTo(o.id);
        }catch (Exception e){
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }



}
