package com.antont.petclinic.pet;

import com.antont.petclinic.issues.Issue;
import com.antont.petclinic.model.NamedEntity;
import com.antont.petclinic.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pets")
public class Pet extends NamedEntity {

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "type")
    private PetType type;

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public PetType getType() {
        return this.type;
    }

    public void setType(PetType type) {
        this.type = type;
    }
}
