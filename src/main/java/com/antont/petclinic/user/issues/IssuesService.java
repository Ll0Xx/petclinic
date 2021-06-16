package com.antont.petclinic.user.issues;

import com.antont.petclinic.pet.PetRepository;
import com.antont.petclinic.security.CurrentUserService;
import com.antont.petclinic.user.User;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class IssuesService {

    private static final int PAGE_START_NUMBER = 1;
    private static final int PAGE_SIZE = 2;
    private static final String DEFAULT_SORT_FIELD = "id";
    final private CurrentUserService currentUserService;
    final private IssuesRepository issuesRepository;
    final private PetRepository petRepository;

    public IssuesService(CurrentUserService currentUserService, IssuesRepository issuesRepository, PetRepository petRepository) {
        this.currentUserService = currentUserService;
        this.issuesRepository = issuesRepository;
        this.petRepository = petRepository;
    }

    public Page<Issue> findPaginatedForDoctor(Optional<Integer> page, Optional<Integer> size, Optional<String> sortField,
                                              Optional<String> sortDir, boolean isDoctor) {
        User user = currentUserService.getCurrentUser();

        int currentPage = page.orElse(PAGE_START_NUMBER);
        int pageSize = size.orElse(PAGE_SIZE);
        String currentSortField = sortField.orElse(DEFAULT_SORT_FIELD);
        String currentSortDir = sortDir.orElse(Sort.Direction.ASC.name());

        Sort sort = currentSortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(currentSortField).ascending() :
                Sort.by(currentSortField).descending();

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, sort);

        List<Issue> issues;

        if (isDoctor) {
            issues = issuesRepository.findByDoctor(user, pageable);

        } else {
            issues = issuesRepository.findByPetOwner(user, pageable);
        }

        return new PageImpl<>(issues, PageRequest.of(currentPage - 1, pageSize, sort), issues.size());
    }

    public void addNewIssue(IssueDto issueDto) {
        Issue issue = new Issue();
        issue.setDoctor(currentUserService.getCurrentUser());
        issue.setPet(petRepository.getById(issueDto.getPet()));
        issue.setDescription(issueDto.getDescription());
        issue.setDate(issueDto.getDate());

        issuesRepository.save(issue);
    }

    /**
     * generate list of numbers for 1 to totalPages
     *
     * @param totalPages number of total pages of Page<> element
     * @return list of integer for 1 to totalPages
     */
    public List<Integer> getPagesNumbersList(Integer totalPages) {
        return IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
    }
}
