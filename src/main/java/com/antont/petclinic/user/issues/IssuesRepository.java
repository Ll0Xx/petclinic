package com.antont.petclinic.user.issues;

import com.antont.petclinic.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuesRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByPetOwner(User user, Pageable pageable);
    List<Issue> findByDoctor(User user, Pageable pageable);
}
