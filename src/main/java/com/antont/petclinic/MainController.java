package com.antont.petclinic;

import com.antont.petclinic.pet.*;
import com.antont.petclinic.security.AuthenticatedUser;
import com.antont.petclinic.security.CurrentUserService;
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
public class MainController {

    private final PetService petService;
    private final CurrentUserService currentUserService;
    private final PetTypeRepository petTypeRepository;

    public MainController(PetService petService, CurrentUserService currentUserService, PetTypeRepository petTypeRepository) {
        this.petService = petService;
        this.currentUserService = currentUserService;
        this.petTypeRepository = petTypeRepository;
    }

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "/index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }

    @GetMapping("/user")
    public String listBooks(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        AuthenticatedUser user = currentUserService.getCurrentUser();
        model.addAttribute("user", user);

        List<PetType> petTypes = petTypeRepository.getAll();
        model.addAttribute("petTypes", petTypes);

        PetDto petDto = new PetDto();
        model.addAttribute("pet", petDto);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Pet> bookPage = petService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("bookPage", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "/user";
    }

//    @GetMapping("/user")
//    public String user() {
//        return "/user";
//    }
}
