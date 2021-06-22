package com.antont.petclinic.pet;

import com.antont.petclinic.security.CurrentUserService;
import com.antont.petclinic.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.*;

@Service
public class PetService {

    private static final int PAGE_START_NUMBER = 1;
    private static final int PAGE_SIZE = 5;
    private final CurrentUserService currentUserService;
    private final PetTypeRepository petTypeRepository;
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository, CurrentUserService currentUserService, PetTypeRepository roleRepository) {
        this.petRepository = petRepository;
        this.currentUserService = currentUserService;
        this.petTypeRepository = roleRepository;
    }

    public List<Pet> findAllByOwner(Pageable pageable) {
        Optional<User> user = currentUserService.getCurrentUser();

        return petRepository.findAllByOwner(user);
    }

    public Optional<Pet> findPetById(Integer id) {
        return petRepository.findById(id);
    }

    public Optional<PetResponseModel> findPetResponseModelById(Integer id) {
        return petRepository.findById(id).map(Pet::toResponseModel);
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public void addPet(PetDto petDto) {
        Optional<User> user = currentUserService.getCurrentUser();

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

    public void update(int petId, PetDto petDto) {
        if (petRepository.existsById(petId)) {
            Optional<Pet> petData = petRepository.findById(petId);

            petData.ifPresent(pet -> {
                if (isOwnerValid(petId)) {
                    pet.setName(petDto.getName());
                    Integer petTypeId = petDto.getType();
                    PetType type = petTypeRepository.findById(petTypeId).orElseThrow();
                    pet.setType(type);
                    petRepository.save(pet);
                }
            });
        }
    }

    private void savePet(PetDto petDto, User user) {
        Pet pet = new Pet();
        pet.setName(petDto.getName());
        Integer petTypeId = petDto.getType();
        PetType type = petTypeRepository.findById(petTypeId).orElseThrow();
        pet.setType(type);
        pet.setOwner(user);

        petRepository.save(pet);
    }

    public void deletePet(int petId) {
        if (petRepository.existsById(petId)) {
            if (isPetOwnerValid(petId)) {
                petRepository.deleteById(petId);
            }
        }
    }

    private boolean isOwnerValid(int petId) {
        Optional<User> user = currentUserService.getCurrentUser();
        Optional<Pet> petData = petRepository.findById(petId);

        if (user.isPresent() && petData.isPresent()) {
            if (Objects.equals(user.get().getUsername(), petData.get().getOwner().getUsername())) {
                return true;
            } else {
                throw new AccessDeniedException("Update rejected due to attempt to update pet entity with id: " + petId +
                        " that does not belong to the current user, username: " + user.get().getUsername());
            }
        }
        return false;
    }

    /**
     * method return Page<Pet> object with paginated List<Pet> object with page size and number of the requested page
     *
     * @param page number of requested page
     * @param size size of pages
     * @return a Page<Pet> object with a list of pet elements
     */
    public Page<Pet> findPaginated(Optional<Integer> page, Optional<Integer> size) {
        int currentPage = page.orElse(PAGE_START_NUMBER) - 1;
        int pageSize = size.orElse(PAGE_SIZE);

        Optional<User> user = currentUserService.getCurrentUser();
        List<Pet> pets = petRepository.findAllByOwner(user);

        int startItem = currentPage * pageSize;
        List<Pet> list;

        if (pets.size() < startItem) {
            list = pets;
        } else {
            int toIndex = Math.min(startItem + pageSize, pets.size());
            list = pets.subList(startItem, toIndex);
        }

        return new PageImpl<>(list, PageRequest.of(currentPage, pageSize), pets.size());
    }

    /**
     * generate list of numbers for 1 to totalPages
     *
     * @param totalPages number of total pages of Page<> element
     * @return list of integer for 1 to totalPages
     */
    public List<Integer> getPagesNumbersList(Integer totalPages) {
        return IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
    }
}
