package com.antont.petclinic.rest;

import com.antont.petclinic.pet.Pet;
import com.antont.petclinic.pet.PetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetsRestController {

    final private PetRepository petRepository;

    public PetsRestController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @GetMapping("/search")
    public List<Pet> searchPets(@RequestParam String petName) {

        List<Pet> pets = petRepository.findByName(petName).orElseThrow();
        return List.of();
    }

}
