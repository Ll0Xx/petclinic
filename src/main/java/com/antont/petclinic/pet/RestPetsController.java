package com.antont.petclinic.pet;

import com.antont.petclinic.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/pets")
public class RestPetsController {

    private final PetService petService;

    public RestPetsController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<PetResponseModel> editPet(@RequestParam("id") Optional<Integer> id) {
        return ResponseEntity.of(id.flatMap(petService::findPetResponseModelById));
    }

    @PutMapping
    public JsonResponse editPet(@RequestParam("id") Optional<Integer> id, @Valid @RequestBody() PetDto petDto,
                                final BindingResult bindingResult) {
        JsonResponse jsonResponse = new JsonResponse();
        if (bindingResult.hasErrors()) {
            jsonResponse.setStatus("fail");
            jsonResponse.setResult(bindingResult.getAllErrors());
            return jsonResponse;
        }
        id.ifPresent(x -> petService.update(x, petDto));
        jsonResponse.setStatus("ok");
        return jsonResponse;
    }
}
