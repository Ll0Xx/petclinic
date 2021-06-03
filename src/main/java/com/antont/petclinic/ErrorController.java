package com.antont.petclinic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class ErrorController {

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
}