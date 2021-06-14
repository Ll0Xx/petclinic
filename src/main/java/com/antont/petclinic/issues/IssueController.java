package com.antont.petclinic.issues;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class IssueController {

    final private IssuesService issuesService;

    public IssueController(IssuesService issuesService) {
        this.issuesService = issuesService;
    }

    @GetMapping("/user/issues")
    public String findPaginated(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                @RequestParam("sortField") Optional<String> sortField,
                                @RequestParam("sortDir") Optional<String> sortDir,
                                Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        String currentSortField = sortField.orElse("id");
        String currentSortDir = sortDir.orElse("asc");

        Sort sort = currentSortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(currentSortField).ascending() :
                Sort.by(currentSortField).descending();

        Page<Issue> issuesPage = issuesService.findPaginated(PageRequest.of(currentPage - 1, pageSize, sort));

        model.addAttribute("issuesPage", issuesPage);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("size", pageSize);
        model.addAttribute("sortField", currentSortField);
        model.addAttribute("sortDir", currentSortDir);
        model.addAttribute("reverseSortDir", currentSortDir.equals("asc") ? "desc" : "asc");

        int totalPages = issuesPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "/issues";
    }
}
