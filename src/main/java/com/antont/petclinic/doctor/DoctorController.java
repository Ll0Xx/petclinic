package com.antont.petclinic.doctor;

import com.antont.petclinic.user.issues.Issue;
import com.antont.petclinic.user.issues.IssueDto;
import com.antont.petclinic.user.issues.IssuesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/doctor/issues")
public class DoctorController {

    final private IssuesService issuesService;

    public DoctorController(IssuesService issuesService) {
        this.issuesService = issuesService;
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR','ROLE_ADMIN')")
    public String addNewIssue(@Valid @ModelAttribute IssueDto issueDto) {
        issuesService.addNewIssue(issueDto);

        return "redirect:/issues";
    }
}
