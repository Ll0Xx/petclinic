package com.antont.petclinic.visit;

import com.antont.petclinic.model.BaseEntity;
import com.antont.petclinic.pet.Pet;
import com.antont.petclinic.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public Pet getPet() {
        return this.pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    public User getDoctor() {
        return this.doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    @Column(name = "issue")
    @NotEmpty
    private String issue;

    public String getIssue() {
        return this.issue;
    }

    public void setIssue(String password) {
        this.issue = issue;
    }
}
