package com.antont.petclinic.pet;

import javax.validation.constraints.NotNull;

public class PetDto {

    @NotNull
    private String name;

    @NotNull
    private PetTypeNames type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetTypeNames getType() {
        return type;
    }

    public void setType(PetTypeNames type) {
        this.type = type;
    }
}
