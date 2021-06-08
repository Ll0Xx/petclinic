package com.antont.petclinic.pet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

//    @GetMapping
//    public String pet(@PageableDefault Pageable pageable, Model model) {
//        model.addAttribute("petList", petService.findAllPetsByOwner(pageable));
//        return "user";
//    }

    @PostMapping
    public String addPet(@Valid @ModelAttribute PetDto petCreationModel) {
        petService.addPet(petCreationModel);
        return "redirect:/user";
    }

//    @PostMapping("/delete/{petId}")
//    public String deletePet(@PathVariable Long petId) {
//        petService.deletePet(petId);
//
//        return "redirect:/pets";
//    }
//
//    @PostMapping("/edit/{petId}")
//    public String editPet(@PathVariable Long petId,
//                          @Valid @ModelAttribute PetUpdateModel petUpdateModel) {
//        petService.updatePet(petId, petUpdateModel);
//
//        return "redirect:/pets";
//    }

}
