package com.antont.petclinic.pet;

import com.antont.petclinic.model.NamedEntity;
import com.antont.petclinic.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne
    @JoinColumn(name = "type")
    private PetType type;

    public PetType getType() {
        return this.type;
    }

    public void setType(PetType type) {
        this.type = type;
    }
}
