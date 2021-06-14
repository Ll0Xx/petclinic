package com.antont.petclinic.user.issues;

import com.antont.petclinic.security.CurrentUserService;
import com.antont.petclinic.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.*;
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

    public Page<Issue> findPaginatedForDoctor(Pageable pageable) {

        User user = currentUserService.getCurrentUser();
        List<Issue> issues = issuesRepository.findByDoctor(user, pageable);

        return getPaginatedIssues(pageable, issues);
    }

    public Page<Issue> findPaginated(Pageable pageable) {

        User user = currentUserService.getCurrentUser();
        List<Issue> issues = issuesRepository.findByPetOwner(user, pageable);

        return getPaginatedIssues(pageable, issues);
    }

    @NotNull
    private Page<Issue> getPaginatedIssues(Pageable pageable, List<Issue> issues) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        Sort sort = pageable.getSort();
        List<Issue> list;

        if (issues.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, issues.size());
            list = issues.subList(startItem, toIndex);
        }

        Page<Issue> PetsPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize, sort), issues.size());

        return PetsPage;
    }
}
