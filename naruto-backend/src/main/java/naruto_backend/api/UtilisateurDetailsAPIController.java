package naruto_backend.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import naruto_backend.api.response.UtilisateurDetailsResponse;
import naruto_backend.api.response.UtilisateurListViewResponse;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurDetailsAPIController {
    private final UtilisateurService service;

    public UtilisateurApiController(UtilisateurService service) {
        this.service = service;
    }
    
    @GetMapping
    public List<UtilisateurListViewResponse> findAll() {
        return this.service.findAll().stream().map(UserListView::convert).toList();
    }

    @GetMapping("/{id}")
    public UtilisateurDetailsResponse findById(@PathVariable String id) {
        return UtilisateurDetailsResponse.convert(this.service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse create(@Valid @RequestBody CreateUtilisateurRequest request) {
        return new EntityCreatedResponse(this.service.save(request).getId());
    }

    @PutMapping("/{id}")
    public EntityUpdatedResponse update(@PathVariable String id, @Valid @RequestBody UpdateUtilisateurRequest request) {
        this.service.save(id, request);

        return new EntityUpdatedResponse(id, true);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        this.service.deleteById(id);
    }
}
