package com.antont.petclinic.user;

import com.antont.petclinic.pet.*;
import com.antont.petclinic.security.CurrentUserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final PetService petService;
    private final CurrentUserService currentUserService;
    private final PetTypeRepository petTypeRepository;

    public UserController(PetService petService, CurrentUserService currentUserService, PetTypeRepository petTypeRepository) {
        this.petService = petService;
        this.currentUserService = currentUserService;
        this.petTypeRepository = petTypeRepository;
    }

    @GetMapping()
    public String petsList(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        String user = currentUserService.getCurrentUserName();
        model.addAttribute("user", user);

        if (!model.containsAttribute("updateMessage")) {
            model.addAttribute("updateMessage", "");
        }

        List<PetType> petTypes = petTypeRepository.findAll();
        model.addAttribute("petTypes", petTypes);

        if (!model.containsAttribute("pet")) {
            model.addAttribute("pet", new PetDto());
        }

        Page<Pet> petsPage = petService.findPaginated(page, size);
        model.addAttribute("petsPage", petsPage);

        List<Integer> pagesNumbers = petService.getPagesNumbersList(petsPage.getTotalPages());
        model.addAttribute("pageNumbers", pagesNumbers);

        return "/user";
    }
}
