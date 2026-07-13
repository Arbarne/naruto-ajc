package naruto_backend.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import naruto_backend.api.request.CreateEquipeRequest;
import naruto_backend.api.request.UpdateEquipeRequest;
import naruto_backend.api.response.EntityCreatedResponse;
import naruto_backend.api.response.EntityUpdatedResponse;
import naruto_backend.api.response.EquipeDetailsResponse;
import naruto_backend.api.response.EquipeListResponse;
import naruto_backend.model.Equipe;
import naruto_backend.service.EquipeService;

@RestController
@RequestMapping("/equipe")
public class EquipeApiController {

    private final EquipeService service;

    public EquipeApiController(EquipeService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<EquipeListResponse> findAll() {
        return service.getAll()
                .stream()
                .map(EquipeListResponse::convert)
                .toList();
    }

    @GetMapping("/mine")
    public EquipeDetailsResponse findMine(Authentication authentication) {
        Equipe equipe = service.getEquipeDeUtilisateur(authentication.getName());

        if (equipe == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return EquipeDetailsResponse.convert(equipe);
    }

    @GetMapping("/{id}")
    public EquipeDetailsResponse findById(@PathVariable Integer id, Authentication authentication) {
        if (!service.peutConsulter(id, authentication.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return EquipeDetailsResponse.convert(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('LEADER') or hasRole('ADMIN')")
    public EntityCreatedResponse create(@Valid @RequestBody CreateEquipeRequest request, Authentication authentication) {
        return new EntityCreatedResponse(service.insert(request, authentication).getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityUpdatedResponse update(@PathVariable Integer id, @Valid @RequestBody UpdateEquipeRequest request) {
        service.update(id, request);

        return new EntityUpdatedResponse(id, true);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable Integer id) {
        service.delete(id);
    }
}
