package com.antont.petclinic.issues;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public String listBooks(Model model, @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Issue> issuesPage = issuesService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("issuesPage", issuesPage);

        int totalPages = issuesPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("issuesNumbers", pageNumbers);
        }

        return "/issues";
    }
}
