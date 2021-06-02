package com.antont.petclinic;

import org.springframework.web.bind.annotation.RequestMapping;

public class LoginController {

    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }
}
