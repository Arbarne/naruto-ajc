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

import jakarta.validation.Valid;
import naruto_backend.api.request.CreateUtilisateurRequest;
import naruto_backend.api.request.UpdateUtilisateurRequest;
import naruto_backend.api.response.UtilisateurDetailsResponse;
import naruto_backend.api.response.UtilisateurListViewResponse;
import naruto_backend.service.UtilisateurService;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurAPIController {

	private final UtilisateurService service;

    public UtilisateurAPIController(UtilisateurService service) {
        this.service = service;
    }
    
    @GetMapping
    public List<UtilisateurListViewResponse> findAll() {
        return this.service.getAll().stream().map(UtilisateurListViewResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public UtilisateurDetailsResponse findById(@PathVariable String id) {
        return UtilisateurDetailsResponse.convert(this.service.getById(Integer.valueOf(id)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse create(@Valid @RequestBody CreateUtilisateurRequest request) {
        return new EntityCreatedResponse(this.service.insert(request).getId().toString());
    }

    @PutMapping("/{id}")
    public EntityUpdatedResponse update(@PathVariable String id, @Valid @RequestBody UpdateUtilisateurRequest request) {
        this.service.update(Integer.valueOf(id), request);

        return new EntityUpdatedResponse(id, true);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        this.service.delete(Integer.valueOf(id));
    }
}
