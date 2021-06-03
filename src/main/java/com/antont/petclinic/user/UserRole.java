package com.antont.petclinic.user;

import com.antont.petclinic.model.NamedEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole extends NamedEntity implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ROLE_" + getName();
    }
}
