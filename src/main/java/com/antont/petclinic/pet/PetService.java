package com.antont.petclinic.pet;

import com.antont.petclinic.security.AuthenticatedUser;
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

@Service
public class PetService {

    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;
    private final PetTypeRepository petTypeRepository;
    private PetRepository petRepository;


    public PetService(PetRepository petRepository, CurrentUserService currentUserService, UserRepository userRepository, PetTypeRepository roleRepository) {
        this.petRepository = petRepository;
        this.currentUserService = currentUserService;
        this.userRepository = userRepository;
        this.petTypeRepository = roleRepository;
    }

    public List<Pet> findAllPetsByOwner(Pageable pageable) {
        AuthenticatedUser user = currentUserService.getCurrentUser();
        User owner = userRepository.findUserByUsername(user.getUsername()).orElseThrow();

        return new ArrayList<>(petRepository.findAllByOwner(pageable, owner));
    }

    public void addPet(PetDto petDto) {
        AuthenticatedUser user = currentUserService.getCurrentUser();
        User owner = userRepository.findUserByUsername(user.getUsername()).orElseThrow();

        Pet pet = new Pet();
        pet.setName(petDto.getName());
        PetType type = petTypeRepository.findByName(petDto.getType().name()).orElseThrow();
        pet.setType(type);
        pet.setOwner(owner);

        petRepository.save(pet);
    }

    public Page<Pet> findPaginated(Pageable pageable) {
        AuthenticatedUser user = currentUserService.getCurrentUser();
        User petOwner = userRepository.findUserByUsername(user.getUsername()).orElseThrow();
        List<Pet> books = petRepository.findAllByOwner(pageable, petOwner);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Pet> list;

        if (books.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }

        Page<Pet> PetsPage
                = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), books.size());

        return PetsPage;
    }
}
