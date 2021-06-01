package com.antont.petclinic.user;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {

    @Id
    public String getUsername() {
        return username;
    }
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;

    public UserRole getType() {
        return this.role;
    }

    public void setType(UserRole type) {
        this.role = type;
    }

    @Column(name = "first_name")
    @NotEmpty
    private String firstName;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "password")
    @NotEmpty
    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
