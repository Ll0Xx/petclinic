package com.antont.petclinic.user;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@NamedEntityGraph(
        name = "users.role",
        attributeNodes = @NamedAttributeNode("role")
)
@Entity
@Table(name = "users")
public class User {

    private boolean active;

    @Id
    public String getUsername() {
        return username;
    }
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @OneToMany(mappedBy = "role_id")
//    @JoinColumn(name = "role_id")
    private Set<UserRole> role;

    public Set<UserRole> getRoles() {
        return role;
    }

    public void setRoles(Set<UserRole> roles) {
        this.role = roles;
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

    public boolean hasRole(UserRole roleName) {
        return role.stream()
                .anyMatch(commonRole -> commonRole.getName().equals(roleName));
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
