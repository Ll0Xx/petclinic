package com.antont.petclinic.user;

import com.antont.petclinic.pet.*;
import com.antont.petclinic.security.CurrentUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String listBooks(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        String user = currentUserService.getCurrentUserName();
        model.addAttribute("user", user);

        List<PetType> petTypes = petTypeRepository.findAll();
        model.addAttribute("petTypes", petTypes);

        PetDto petDto = new PetDto();
        model.addAttribute("pet", petDto);

        //ToDo: fix magic numbers
        //ToDo: Move logic to proper service/component layers
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Pet> petsPage = petService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("petsPage", petsPage);

        int totalPages = petsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "/user";
    }
}
