package com.antont.petclinic.pet;

import com.antont.petclinic.model.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pet_type")
public class PetType extends NamedEntity {
}
