package com.example.demo.db.entities;

import com.example.demo.db.entities.interfaces.IEntity;
import com.example.demo.db.entities.validator.EmailValidator;
import com.example.demo.db.entities.validator.PhoneValidator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.MissingFormatArgumentException;
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

    @Column(nullable = false,unique = true)
    @EmailValidator.Validate
    private String email;

    @Column(nullable = false,unique = true)
    @PhoneValidator.Validate
    private String phoneNumber;

    public Profile() {
    }

    public Profile(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

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


    @Nullable
    public String getLastName() {
        return lastName;
    }


    @Nullable
    public String getEmail() {
        return email;
    }


    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
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
    public int compareTo(@NonNull Profile o) {
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

    public static Profile fromJson(JsonObject object) throws InvalidPropertyException{
        JsonElement _firstName = object.get("firstName");
        JsonElement _lastName = object.get("lastName");
        JsonElement _email = object.get("email");
        JsonElement _phoneNumber = object.get("phoneNumber");

        if(_firstName == null || _lastName == null ||_email == null || _phoneNumber == null)
            throw  new MissingFormatArgumentException("Missing argument");

        String firstName = _firstName.getAsString();
        String lastName = _lastName.getAsString();
        String email = _email.getAsString();
        String phoneNumber = _phoneNumber.getAsString();
        return new Profile(firstName, lastName, email, phoneNumber
        );
    }
}
