package com.antont.petclinic.visit;

import com.antont.petclinic.security.CurrentUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IssueController {

    @GetMapping("/user/issues")
    public String listBooks() {

        return "/issues";
    }
}
