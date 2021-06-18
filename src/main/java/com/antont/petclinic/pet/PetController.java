package com.antont.petclinic.pet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String addPet(@Valid @ModelAttribute PetDto petCreationModel) {
        petService.addPet(petCreationModel);
        return "redirect:/user";
    }

    @PostMapping("/update")
    public String editPet(@RequestParam("id") Optional<Integer> id, @Valid @ModelAttribute PetDto petCreationModel) {
        int petId = id.orElseThrow();

        petService.update(petId, petCreationModel);
        return "redirect:/user";
    }

    @PostMapping("/delete")
    public String deletePet(@RequestParam("id") Optional<Integer> id) {
        int petId = id.orElseThrow();

        petService.delete(petId);
        return "redirect:/user";
    }
}
