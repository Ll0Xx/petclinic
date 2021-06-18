package com.antont.petclinic.pet;

import com.antont.petclinic.security.CurrentUserService;
import com.antont.petclinic.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;

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
        Optional<User> user = currentUserService.getCurrentUser();

        return new ArrayList<>(petRepository.findAllByOwner(user));
    }

    public void addPet(PetDto petDto) {
        Optional<User> user = currentUserService.getCurrentUser();

        savePet(petDto, user);
    }

    public void updatePet(int petId, PetDto petDto) {
        Pet petData = petRepository.findById(petId).orElseThrow();

        if (isPetOwnerValid(petId)) {
            petData.setName(petDto.getName());
            Integer petTypeId = Integer.valueOf(petDto.getType());
            PetType type = petTypeRepository.findById(petTypeId).orElseThrow();
            petData.setType(type);
            petRepository.save(petData);
        }
    }

    private void savePet(PetDto petDto, Optional<User> user) {
        user.ifPresent(it -> {
            Pet pet = new Pet();
            pet.setName(petDto.getName());
            Integer petTypeId = Integer.valueOf(petDto.getType());
            PetType type = petTypeRepository.findById(petTypeId).orElseThrow();
            pet.setType(type);
            pet.setOwner(it);

            petRepository.save(pet);
        });

    }

    public void deletePet(int petId) {
        if (isPetOwnerValid(petId)) {
            petRepository.deleteById(petId);
        }
    }

    private boolean isPetOwnerValid(int petId) {
        Optional<User> user = currentUserService.getCurrentUser();
        Optional<Pet> petData = petRepository.findById(petId);

        if (user.isPresent() && petData.isPresent()) {
            if (Objects.equals(user.get().getUsername(), petData.get().getOwner().getUsername())) {
                return true;
            } else {
                throw new AccessDeniedException("Update rejected due to attempt to update pet entity with id: " + petId +
                        " that does not belong to the current user, username: " + user.get().getUsername());
            }
        } return false;
    }

    public Page<Pet> findPaginated(Pageable pageable) {
        Optional<User> user = currentUserService.getCurrentUser();
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
