package com.antont.petclinic.user.issues;

import com.antont.petclinic.model.BaseEntity;
import com.antont.petclinic.pet.Pet;
import com.antont.petclinic.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Entity
@Table(name = "issue")
public class Issue extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @Column(name = "description")
    @NotEmpty
    private String description;

    @Column(name = "date")
    private String date;

    public Issue() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public IssueResponseModel toResponseModel() {
        return new IssueResponseModel(getId(), pet.getId(), pet.getName(), doctor.getLastName(),
                doctor.getFirstName() + " " + doctor.getLastName(), description, date);
    }
}
