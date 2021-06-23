package com.antont.petclinic.user.issues;

import com.antont.petclinic.user.issues.validation.TextConstraint;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

public class IssueDto {

    @NotNull
    private Integer pet;

    @NotNull
    @TextConstraint
    @Size(min = 1, max = 100)
    private String description;

    private String doctor;

    @DateTimeFormat
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
