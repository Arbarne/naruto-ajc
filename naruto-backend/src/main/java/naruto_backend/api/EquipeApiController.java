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
import naruto_backend.api.request.CreateEquipeRequest;
import naruto_backend.api.request.UpdateEquipeRequest;
import naruto_backend.api.response.EntityCreatedResponse;
import naruto_backend.api.response.EntityUpdatedResponse;
import naruto_backend.api.response.EquipeDetails;
import naruto_backend.api.response.EquipeList;
import naruto_backend.service.EquipeService;

@RestController
@RequestMapping("/equipe")
public class EquipeApiController {
    
    private final EquipeService service;

    public EquipeApiController(EquipeService service) {
        this.service = service;
    }

    @GetMapping
    public List<EquipeList> findAll() {
        return service.getAll()
                .stream()
                .map(EquipeList::convert)
                .toList();
    }

    @GetMapping("/{id}")
    public EquipeDetails findById(@PathVariable Integer id) {
        return EquipeDetails.convert(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityCreatedResponse create(@Valid @RequestBody CreateEquipeRequest request) {
    return new EntityCreatedResponse(service.insert(request).getId());
}

    @PutMapping("/{id}")
    public EntityUpdatedResponse update(@PathVariable Integer id, @Valid @RequestBody UpdateEquipeRequest request) {
    service.update(id, request);

    return new EntityUpdatedResponse(id, true);
}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        service.delete(id);
    }
}
