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
    private final UserRepository userRepository;
    private final PetTypeRepository petTypeRepository;
    private final PetRepository petRepository;


    public PetService(PetRepository petRepository, CurrentUserService currentUserService, UserRepository userRepository, PetTypeRepository roleRepository) {
        this.petRepository = petRepository;
        this.currentUserService = currentUserService;
        this.userRepository = userRepository;
        this.petTypeRepository = roleRepository;
    }

    public List<Pet> findAllPetsByOwner(Pageable pageable) {
        String user = currentUserService.getCurrentUserName();
        Optional<User> owner = userRepository.findUserByUsername(user);

        return new ArrayList<>(petRepository.findAllByOwner(owner));
    }

    public void addPet(PetDto petDto) {
        String user = currentUserService.getCurrentUserName();
        User owner = userRepository.findUserByUsername(user).orElseThrow();

        Pet pet = new Pet();
        pet.setName(petDto.getName());
        Integer petTypeId = Integer.valueOf(petDto.getType());
        PetType type = petTypeRepository.findById(petTypeId).orElseThrow();
        pet.setType(type);
        pet.setOwner(owner);

        petRepository.save(pet);
    }

    public Page<Pet> findPaginated(Pageable pageable) {
        String user = currentUserService.getCurrentUserName();
        Optional<User> petOwner = userRepository.findUserByUsername(user);
        List<Pet> pets = petRepository.findAllByOwner(petOwner);

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
