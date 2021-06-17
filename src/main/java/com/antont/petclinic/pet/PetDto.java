package com.antont.petclinic.pet;

import com.antont.petclinic.pet.validation.TypeConstraint;
import com.antont.petclinic.security.registration.validation.NameConstraint;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PetDto {

    @NotNull(message = "{validation.null}")
    @NameConstraint
    @Size(min = 3, max = 20, message = "{validation.invalidLength}")
    private String name;

    @NotNull(message = "{validation.null}")
    @TypeConstraint
    private Integer type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
