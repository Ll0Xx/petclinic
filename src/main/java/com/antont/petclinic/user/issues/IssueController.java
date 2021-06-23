package com.antont.petclinic.user.issues;

import com.antont.petclinic.pet.PetResponseModel;
import com.antont.petclinic.pet.PetService;
import com.antont.petclinic.user.UserRoleName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/issues")
public class IssueController {

    final private IssuesService issuesService;
    final private PetService petService;

    public IssueController(IssuesService issuesService, PetService petService) {
        this.issuesService = issuesService;
        this.petService = petService;
    }

    @GetMapping
    public String findPaginated(HttpServletRequest request,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam("sortField") Optional<String> sortField,
                                @RequestParam("sortDir") Optional<String> sortDir,
                                Model model) {
        boolean isDoctor = request.isUserInRole(UserRoleName.DOCTOR.name());
        Page<Issue> issuesPage = issuesService.findPaginatedForDoctor(page, size, sortField, sortDir, isDoctor);
        model.addAttribute("issuesPage", issuesPage);

        String currentSortField = sortField.orElse("id");
        String currentSortDir = sortDir.orElse(Sort.Direction.ASC.name());

        List<PetResponseModel> petsList = petService.findAllAsResponseModel();
        model.addAttribute("pets", petsList);

        model.addAttribute("currentPage", issuesPage.getPageable().getPageNumber());
        model.addAttribute("size", issuesPage.getPageable().getPageSize());
        model.addAttribute("sortField", currentSortField);
        model.addAttribute("sortDir", currentSortDir);
        model.addAttribute("reverseSortDir", currentSortDir.equals(Sort.Direction.ASC.name()) ?
                Sort.Direction.DESC.name() : Sort.Direction.ASC.name());


        List<Integer> pagesNumbers = issuesService.getPagesNumbersList(issuesPage.getTotalPages());
        model.addAttribute("pageNumbers", pagesNumbers);

        if (!model.containsAttribute("issue")) {
            model.addAttribute("issue", new IssueDto());
        }

        return "/issues";
    }

    @PutMapping
    public String updatePet() {
        return "";
    }
}
