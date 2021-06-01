package com.antont.petclinic.user;

import com.antont.petclinic.model.NamedEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole extends NamedEntity {
}
