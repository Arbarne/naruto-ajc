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
import naruto_backend.api.request.SubscriptionRequest;
import naruto_backend.api.request.UpdateUtilisateurRequest;
import naruto_backend.api.response.UtilisateurDetailsResponse;
import naruto_backend.api.response.UtilisateurListViewResponse;
import naruto_backend.model.Leader;
import naruto_backend.model.Ninja;
import naruto_backend.model.Utilisateur;
import naruto_backend.service.EquipeService;
import naruto_backend.service.UtilisateurService;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurAPIController {

	private final UtilisateurService utilisateurService;
	private final EquipeService equipeService;
    
    
    public UtilisateurAPIController(UtilisateurService utilisateurService, EquipeService equipeService) {
        this.utilisateurService = utilisateurService;
        this.equipeService = equipeService;
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
        
        Utilisateur utilisateur = this.utilisateurService.save(new Utilisateur(), request);

        return new EntityCreatedResponse(utilisateur.getId());
    }

    @PutMapping("/{id}")
    public EntityUpdatedResponse update(@PathVariable String id, @Valid @RequestBody UpdateUtilisateurRequest request) {
        this.utilisateurService.update(Integer.valueOf(id), request);

        return new EntityUpdatedResponse(id, true);
    }
    
    @PutMapping("/{id}/equipe/{equipeId}")
    public EntityUpdatedResponse update(@PathVariable String id, @PathVariable String equipeId, @Valid @RequestBody UpdateUtilisateurRequest request) {
    	Utilisateur user = this.utilisateurService.getById(Integer.valueOf(id));

        this.utilisateurService.update(Integer.valueOf(id), request);

        if (user instanceof Ninja ninja) {
            ninja.setEquipe(equipeService.getById(Integer.valueOf(equipeId)));
        }
        else if (user instanceof Leader leader) {
            leader.setEquipe(equipeService.getById(Integer.valueOf(equipeId)));
        }
        else {
            return new EntityUpdatedResponse(id, false);
        }

        //il faut aussi sauvegarder l'utilisateur
        this.utilisateurService.save(user);
        return new EntityUpdatedResponse(id, true);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String id) {
        this.utilisateurService.delete(Integer.valueOf(id));
    }


    @DeleteMapping("/{id}/equipe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserFromEquipe(@PathVariable String id) {
    	
    	Utilisateur user = this.utilisateurService.getById(Integer.valueOf(id));

        if (user instanceof Ninja ninja) {
            ninja.setEquipe(null);
        }
        else if (user instanceof Leader leader) {
            leader.setEquipe(null);
        }
        
        //Askip ça, ça supprime complètement l'utilisateur, à remplacer par le save qui suit
        //this.utilisateurService.delete(Integer.valueOf(id));
        this.utilisateurService.save(user);
    }

}
