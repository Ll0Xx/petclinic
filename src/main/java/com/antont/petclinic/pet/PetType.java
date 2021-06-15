package com.antont.petclinic.pet;

import com.antont.petclinic.model.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

//Not clear why do you use two words for same naming like "animal" and "pet"?
@Entity
@Table(name = "animal_type")
public class PetType extends NamedEntity {
}
