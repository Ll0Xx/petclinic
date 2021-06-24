package com.antont.petclinic.user.issues;

import com.antont.petclinic.pet.Pet;
import com.antont.petclinic.pet.PetRepository;
import com.antont.petclinic.security.CurrentUserService;
import com.antont.petclinic.user.User;
import com.antont.petclinic.user.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class IssuesService {

    private static final int PAGE_START_NUMBER = 0;
    private static final int PAGE_SIZE = 2;
    private static final String DEFAULT_SORT_FIELD = "id";
    final private UserRepository userRepository;
    final private CurrentUserService currentUserService;
    final private IssuesRepository issuesRepository;
    final private PetRepository petRepository;

    public IssuesService(UserRepository userRepository, CurrentUserService currentUserService,
                         IssuesRepository issuesRepository, PetRepository petRepository) {
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
        this.issuesRepository = issuesRepository;
        this.petRepository = petRepository;
    }

    public Optional<Issue> findById(int id) {
        return issuesRepository.findById(id);
    }

    public Page<Issue> findPaginated(Optional<Integer> page, Optional<Integer> size, Optional<String> sortField,
                                     Optional<String> sortDir, boolean isDoctor) {
        Optional<User> user = currentUserService.getCurrentUser();

        int currentPage = page.orElse(PAGE_START_NUMBER);
        int pageSize = size.orElse(PAGE_SIZE);
        String currentSortField = sortField.orElse(DEFAULT_SORT_FIELD);
        String currentSortDir = sortDir.orElse(Sort.Direction.ASC.name());

        Sort sort = currentSortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(currentSortField).ascending() :
                Sort.by(currentSortField).descending();

        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);

        long totalCount = issuesRepository.count();

        List<Issue> issues;

        if (isDoctor) {
            issues = issuesRepository.findByDoctor(user, pageable);

        } else {
            issues = issuesRepository.findByPetOwner(user, pageable);
        }

        return new PageImpl<>(issues, PageRequest.of(currentPage, pageSize, sort), totalCount);
    }

    public void add(IssueDto issueDto) {
        Issue issue = new Issue();

        Optional<User> user = currentUserService.getCurrentUser();
        Optional<Pet> pet = petRepository.findById(issueDto.getPet());
        if (user.isPresent() && pet.isPresent()) {
            issue.setDoctor(user.get());
            issue.setPet(pet.get());
            issue.setDescription(issueDto.getDescription());
            issue.setDate(issueDto.getDate());
            issuesRepository.save(issue);
        }
    }

    public Optional<IssueResponseModel> findPetResponseModelById(Integer id) {
        return issuesRepository.findById(id).map(Issue::toResponseModel);
    }

    public void update(Integer issueId, IssueDto issueDto) {
        if (issuesRepository.existsById(issueId)) {
            Optional<Issue> issueData = issuesRepository.findById(issueId);

            issueData.ifPresent(issue -> {
                Optional<Pet> petData = petRepository.findById(issueDto.getPet());
                issue.setPet(petData.orElseThrow());

                Optional<User> userData = userRepository.findById(issueDto.getDoctor());
                issue.setDoctor(userData.orElseThrow());

                issue.setDescription(issueDto.getDescription());
                issue.setDate(issueDto.getDate());
                issuesRepository.save(issue);
            });
        }
    }

    public void delete(int id) {
        if (issuesRepository.existsById(id)) {
            issuesRepository.deleteById(id);
        }
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
