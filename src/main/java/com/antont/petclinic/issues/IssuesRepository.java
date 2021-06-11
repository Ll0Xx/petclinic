package com.antont.petclinic.issues;

import com.antont.petclinic.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuesRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByPetOwner(User user);
}
