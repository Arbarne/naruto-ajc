package naruto_backend.api;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
import naruto_backend.api.request.SubscriptionRequest;
import naruto_backend.api.request.UpdateUtilisateurRequest;
import naruto_backend.api.response.UtilisateurDetailsResponse;
import naruto_backend.api.response.UtilisateurListViewResponse;
import naruto_backend.model.Utilisateur;
import naruto_backend.service.UtilisateurService;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurAPIController {

	private final UtilisateurService utilisateurService;
    
    
    public UtilisateurAPIController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }
    
    @GetMapping
    public List<UtilisateurListViewResponse> findAll() {
        return this.utilisateurService.getAll().stream().map(UtilisateurListViewResponse::convert).toList();
    }

    @GetMapping("/{id}")
    public UtilisateurDetailsResponse findById(@PathVariable String id) {
        return UtilisateurDetailsResponse.convert(this.utilisateurService.getById(Integer.valueOf(id)));
    }

    @PostMapping("/inscription")
    public EntityCreatedResponse subscribe(@Valid @RequestBody SubscriptionRequest request) {
        Utilisateur utilisateur = this.utilisateurService.create(new Utilisateur(), request);

        return new EntityCreatedResponse(utilisateur.getId());
    }

    @PutMapping("/{id}")
    public EntityUpdatedResponse update(@PathVariable Integer id, @Valid @RequestBody UpdateUtilisateurRequest request) {
        this.utilisateurService.update(Integer.valueOf(id), request);

        return new EntityUpdatedResponse(id, true);
    }
    
    @PutMapping("/{id}/equipe/{equipeId}")
    public EntityUpdatedResponse update(@PathVariable Integer id, @PathVariable Integer equipeId) {
    	
        Utilisateur user;
        try {
            user = this.utilisateurService.updateEquipe(id, equipeId);
        } catch (NotFoundException e) {
            return new EntityUpdatedResponse(-1, false);
        }

        return new EntityUpdatedResponse(user.getId(), true);
        
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        this.utilisateurService.delete(Integer.valueOf(id));
    }


    @DeleteMapping("/{id}/equipe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public EntityUpdatedResponse deleteUserFromEquipe(@PathVariable Integer id) {
    	
        Utilisateur user;
        try {
            user = this.utilisateurService.deleteUserFromEquipe(id);
        } catch (NotFoundException e) {
            return new EntityUpdatedResponse(-1, false);
        }
        return new EntityUpdatedResponse(user.getId(), true);
    }

}
