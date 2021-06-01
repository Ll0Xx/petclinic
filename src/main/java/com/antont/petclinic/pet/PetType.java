package com.antont.petclinic.pet;

import com.antont.petclinic.model.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "animal_type")
public class PetType extends NamedEntity {
}
