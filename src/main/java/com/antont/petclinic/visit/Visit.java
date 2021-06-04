package com.antont.petclinic.visit;

import com.antont.petclinic.pet.Pet;
import com.antont.petclinic.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "visits")
public class Visit {

    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @Column(name = "issue")
    @NotEmpty
    private String issue;

    public Visit() {
    }

    public Pet getPet() {
        return this.pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public User getDoctor() {
        return this.doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public String getIssue() {
        return this.issue;
    }

    public void setIssue(String password) {
        this.issue = issue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
