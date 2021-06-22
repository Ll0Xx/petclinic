package com.antont.petclinic.user.issues;

import java.sql.Date;

public class IssueResponseModel {

    private int id;

    private int petId;
    
    private String petName;
    
    private String doctorId;

    private String doctorFullName;

    private String description;

    private Date date;

    public IssueResponseModel(int id, int petId, String petName, String doctorId, String doctorFullName, String description, Date date) {
        this.id = id;
        this.petId = petId;
        this.petName = petName;
        this.doctorId = doctorId;
        this.doctorFullName = doctorFullName;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
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
}
