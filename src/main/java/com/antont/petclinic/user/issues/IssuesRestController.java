package com.antont.petclinic.user.issues;

import com.antont.petclinic.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("api/issues")
public class IssuesRestController {

    private final IssuesService issuesService;

    public IssuesRestController(IssuesService issuesService) {
        this.issuesService = issuesService;
    }

    @GetMapping
    public ResponseEntity<IssueResponseModel> editPet(@RequestParam("id") Optional<Integer> id) {
        return ResponseEntity.of(id.flatMap(issuesService::findPetResponseModelById));
    }

    @DeleteMapping
    public void deleteIssue(@RequestParam("id") Optional<Integer> id) {
        id.ifPresent(issuesService::delete);
    }

    @PutMapping
    public JsonResponse updateIssue(@RequestParam("id") Optional<Integer> id,
                                    @Valid @RequestBody() IssueDto issueDto,
                                    final BindingResult bindingResult) {

        JsonResponse jsonResponse = new JsonResponse();
        if (bindingResult.hasErrors()) {
            jsonResponse.setStatus("fail");
            jsonResponse.setResult(bindingResult.getAllErrors());
            return jsonResponse;
        }
        id.ifPresent(
                it -> {
                    issuesService.update(it, issueDto);
                    jsonResponse.setStatus("ok");
                    jsonResponse.setResult(issuesService.findById(it).map(Issue::toResponseModel));
                });

        return jsonResponse;
    }
}
