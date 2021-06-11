package com.antont.petclinic.pet;

import com.antont.petclinic.security.CurrentUserService;
import com.antont.petclinic.user.User;
import com.antont.petclinic.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final CurrentUserService currentUserService;
    private final PetTypeRepository petTypeRepository;
    private final PetRepository petRepository;


    public PetService(PetRepository petRepository, CurrentUserService currentUserService, PetTypeRepository roleRepository) {
        this.petRepository = petRepository;
        this.currentUserService = currentUserService;
        this.petTypeRepository = roleRepository;
    }

    public List<Pet> findAllPetsByOwner(Pageable pageable) {
        User user = currentUserService.getCurrentUser();

        return new ArrayList<>(petRepository.findAllByOwner(user));
    }

    public void addPet(PetDto petDto) {
        User user = currentUserService.getCurrentUser();

        Pet pet = new Pet();
        pet.setName(petDto.getName());
        Integer petTypeId = Integer.valueOf(petDto.getType());
        PetType type = petTypeRepository.findById(petTypeId).orElseThrow();
        pet.setType(type);
        pet.setOwner(user);

        petRepository.save(pet);
    }

    public Page<Pet> findPaginated(Pageable pageable) {
        User user = currentUserService.getCurrentUser();
        List<Pet> pets = petRepository.findAllByOwner(user);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Pet> list;

        if (pets.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, pets.size());
            list = pets.subList(startItem, toIndex);
        }

        Page<Pet> PetsPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), pets.size());

        return PetsPage;
    }
}
