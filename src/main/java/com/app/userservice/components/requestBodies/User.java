package com.app.userservice.components.requestBodies;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Formatter;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message="Email is required.")
    @Email(message="Please provide a valid email address.")
    private String email;

    @NotBlank(message="Password is required.")
    private String password;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected User() {}

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Set email of user
     * @param email User's email
     * @return The instance of User
     */
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Set password to be encoded before saving in repository
     * @param password Raw password value
     * @return The instance of User
     */
    public User setPassword(String password) {
        this.password = password;
        return this;
    }


    public String getFirstName() {
        return firstName;
    }

     /**
     * Set first name of user
     * @param firstName First name of User
     * @return The instance of User
     */
    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

     /**
     * Set last name of user
     * @param lastName Last name of user
     * @return The instance of User
     */
    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        return formatter.format("User(id=%d, email='%s', firstName='%s', lastName='%s')",
                this.id, this.email, this.firstName, this.lastName).toString();
    }

}
