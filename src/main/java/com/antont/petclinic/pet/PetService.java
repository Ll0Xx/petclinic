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

    public Optional<PetResponseModel> findResponseModelById(Integer id) {
        return petRepository.findById(id).map(Pet::toResponseModel);
    }

    public List<PetResponseModel> findAllAsResponseModel() {
        List<Pet> pets = petRepository.findAll();

        return pets.stream()
                .map(Pet::toResponseModel)
                .collect(Collectors.toList());

    }

    public void save(PetDto petDto) {
        Optional<User> user = currentUserService.getCurrentUser();

        user.ifPresent(it -> {
            Pet pet = new Pet();
            pet.setName(petDto.getName());
            Integer petTypeId = petDto.getType();
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

    public void delete(int petId) {
        if (petRepository.existsById(petId)) {
            if (isOwnerValid(petId)) {
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
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        List<Pet> pets = petRepository.findAllByOwner(user, pageable);
        long totalCount = petRepository.countByOwner(user);

        return new PageImpl<>(pets, pageable, totalCount);
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
