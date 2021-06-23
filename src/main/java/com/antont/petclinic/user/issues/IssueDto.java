package com.antont.petclinic.user.issues;

import com.antont.petclinic.user.issues.validation.DateConstraint;
import com.antont.petclinic.user.issues.validation.TextConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IssueDto {

    @NotNull
    private Integer pet;

    @NotNull
    @TextConstraint
    @Size(min = 1, max = 100)
    private String description;

    private String doctor;

    @DateConstraint
    private String date;

    public Integer getPet() {
        return pet;
    }

    public void setPet(Integer pet) {
        this.pet = pet;
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

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
