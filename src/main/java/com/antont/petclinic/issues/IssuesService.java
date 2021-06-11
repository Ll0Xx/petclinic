package com.antont.petclinic.issues;

import com.antont.petclinic.pet.Pet;
import com.antont.petclinic.security.CurrentUserService;
import com.antont.petclinic.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class IssuesService {

    final private CurrentUserService currentUserService;
    final private IssuesRepository issuesRepository;

    public IssuesService(CurrentUserService currentUserService, IssuesRepository issuesRepository) {
        this.currentUserService = currentUserService;
        this.issuesRepository = issuesRepository;
    }

    public Page<Issue> findPaginated(Pageable pageable) {

        User user = currentUserService.getCurrentUser();
        List<Issue> issues = issuesRepository.findByPetOwner(user);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Issue> list;

        if (issues.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, issues.size());
            list = issues.subList(startItem, toIndex);
        }

        Page<Issue> PetsPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), issues.size());

        return PetsPage;
    }
}
