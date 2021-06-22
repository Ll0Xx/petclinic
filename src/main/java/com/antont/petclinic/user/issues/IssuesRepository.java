package com.antont.petclinic.user.issues;

import com.antont.petclinic.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssuesRepository extends JpaRepository<Issue, Integer> {

    List<Issue> findByPetOwner(Optional<User> user, Pageable pageable);

    List<Issue> findByDoctor(Optional<User> user, Pageable pageable);
}
