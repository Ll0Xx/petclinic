package com.antont.petclinic.doctor;

import com.antont.petclinic.user.issues.IssueDto;
import com.antont.petclinic.user.issues.IssuesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/doctor/issues")
public class DoctorController {

    final private IssuesService issuesService;

    public DoctorController(IssuesService issuesService) {
        this.issuesService = issuesService;
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyRole('ROLE_DOCTOR','ROLE_ADMIN')")
    public String addNewIssue(@Valid @ModelAttribute IssueDto issueDto, BindingResult bindingResult, RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.issue", bindingResult);
            attr.addFlashAttribute("issue", issueDto);
        } else {
            issuesService.add(issueDto);
        }

        return "redirect:/issues";
    }
}
