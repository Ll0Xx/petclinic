package com.antont.petclinic.pet;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public String addPet(@Valid @ModelAttribute PetDto petDto, final BindingResult bindingResult, RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.pet", bindingResult);
            attr.addFlashAttribute("pet", petDto);
            return "redirect:/user";
        }
        petService.addPet(petDto);
        return "redirect:/user";
    }

    @PostMapping("/update")
    public String editPet(@RequestParam("id") Optional<Integer> id, @Valid @ModelAttribute PetDto petDto,
                          final BindingResult bindingResult, RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            return "redirect:/user";
        }
        int petId = id.orElseThrow();
        petService.updatePet(petId, petDto);
        return "redirect:/user";
    }

    @PostMapping("/delete")
    public String deletePet(@RequestParam("id") Optional<Integer> id) {
        int petId = id.orElseThrow();

        petService.deletePet(petId);
        return "redirect:/user";
    }
}
