package com.antont.petclinic.pet;

import com.antont.petclinic.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    List<Pet> findAllByOwner(Optional<User> owner, Pageable pageable);

    long countByOwner(Optional<User> owner);

    Optional<Pet> findByIdAndOwner(Integer id, User owner);

    Optional<List<Pet>> findByName(String name);
}