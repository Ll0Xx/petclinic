package com.antont.petclinic.security.registration;

import com.antont.petclinic.MessagesService;
import com.antont.petclinic.security.registration.excepsion.UserAlreadyExistException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final MessagesService messagesService;

    public RegistrationController(UserService userService, MessagesService messagesService) {
        this.userService = userService;
        this.messagesService = messagesService;
    }

    @GetMapping("/signup")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signup";
    }

    @PostMapping
    public String userRegistration(@ModelAttribute("user") @Valid UserDto userDto, final BindingResult bindingResult, final Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registrationForm", userDto);
            return "signup";
        }
        try {
            userService.register(userDto);
        } catch (UserAlreadyExistException e) {
            String message = messagesService.getMessage("exceptions.userAlreadyExist");
            bindingResult.rejectValue("username", "userData.username", message);
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            return "signup";
        }
        return "login";
    }
}
