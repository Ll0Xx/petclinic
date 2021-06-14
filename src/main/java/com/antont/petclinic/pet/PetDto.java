package com.antont.petclinic.pet;

import com.antont.petclinic.security.registration.validation.NameConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PetDto {

    @NotNull
    @NameConstraint
    @Size(min = 1, max = 20)
    private String name;

    @NotNull
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
