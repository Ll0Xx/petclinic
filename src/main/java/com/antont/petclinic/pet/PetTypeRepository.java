package com.antont.petclinic.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {

    List<PetType> getAll();
    Optional<PetType> findByName(String name);
}