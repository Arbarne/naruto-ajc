package naruto_backend.api;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import naruto_backend.api.request.SubscriptionRequest;
import naruto_backend.api.request.UpdateUtilisateurRequest;
import naruto_backend.api.request.UpdateUtilisateurTypeRequest;
import naruto_backend.api.response.LeaderOptionResponse;
import naruto_backend.api.response.NinjaOptionResponse;
import naruto_backend.api.response.UtilisateurDetailsResponse;
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<UtilisateurDetailsResponse> findAll() {
        return this.utilisateurService.getAll().stream().map(UtilisateurDetailsResponse::convert).toList();
    }

    @GetMapping("/ninja")
    @PreAuthorize("hasRole('LEADER') or hasRole('ADMIN')")
    public List<NinjaOptionResponse> findAllNinja() {
        return this.utilisateurService.getAllNinja().stream()
            .map(ninja -> new NinjaOptionResponse(
                ninja.getId(),
                ninja.getLogin(),
                ninja.getNom(),
                ninja.getPrenom(),
                ninja.getRang() != null ? ninja.getRang().toString() : null,
                ninja.getSpecialite() != null ? ninja.getSpecialite().toString() : null,
                ninja.getEquipe() != null ? ninja.getEquipe().getId() : null))
            .toList();
    }

    @GetMapping("/leader")
    @PreAuthorize("hasRole('ADMIN')")
    public List<LeaderOptionResponse> findAllLeader(@RequestParam(required = false) Integer equipeId) {
        // Seuls les leaders sans equipe sont proposes, sauf le leader actuel de l'equipe en cours de modification.
        return this.utilisateurService.getAllLeader().stream()
            .filter(leader -> leader.getEquipe() == null
                || (equipeId != null && equipeId.equals(leader.getEquipe().getId())))
            .map(LeaderOptionResponse::convert)
            .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public UtilisateurDetailsResponse findById(@PathVariable String id) {
        return UtilisateurDetailsResponse.convert(this.utilisateurService.getById(Integer.valueOf(id)));
    }

    @GetMapping("/mine")
    public UtilisateurDetailsResponse findMine(Authentication authentication) {
        Utilisateur utilisateur = this.utilisateurService.getByLogin(authentication.getName())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return UtilisateurDetailsResponse.convert(utilisateur);
    }

    @GetMapping("/leader/available")
    @PreAuthorize("hasRole('ADMIN')")
    public List<LeaderOptionResponse> findAvailableLeaders() {
        return utilisateurService
            .getAvailableLeaders()
            .stream()
            .map(LeaderOptionResponse::convert)
            .toList()
        ;
    }

    @PostMapping("/inscription")
    public EntityCreatedResponse subscribe(@Valid @RequestBody SubscriptionRequest request) {
        // Une inscription cree toujours un Ninja (jamais un Leader/Hokage, ni un Utilisateur
        // generique qui n'apparaitrait dans aucune liste et ne pourrait jamais rejoindre une equipe).
        Utilisateur utilisateur = this.utilisateurService.create(new Ninja(), request);

        return new EntityCreatedResponse(utilisateur.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public EntityCreatedResponse create(@Valid @RequestBody SubscriptionRequest request) {
        // Le Hokage cree directement un profil Ninja (les valeurs de base sont ensuite
        // ajustables via PUT /utilisateur/{id}).
        Utilisateur utilisateur = this.utilisateurService.create(new Ninja(), request);

        return new EntityCreatedResponse(utilisateur.getId());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityUpdatedResponse update(@PathVariable Integer id, @Valid @RequestBody UpdateUtilisateurRequest request) {
        this.utilisateurService.update(Integer.valueOf(id), request);

        return new EntityUpdatedResponse(id, true);
    }
    
    @PutMapping("/{id}/type")
    @PreAuthorize("hasRole('ADMIN')")
    public EntityUpdatedResponse updateType(@PathVariable Integer id, @Valid @RequestBody UpdateUtilisateurTypeRequest request) {
        this.utilisateurService.updateType(id, request.getType());

        return new EntityUpdatedResponse(id, true);
    }

    @PutMapping("/{id}/equipe/{equipeId}")
    @PreAuthorize("hasRole('LEADER') or hasRole('ADMIN')")
    public EntityUpdatedResponse update(@PathVariable Integer id, @PathVariable Integer equipeId, Authentication authentication) {

        if (!equipeService.peutGererEquipe(equipeId, authentication)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

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
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@PathVariable String id) {
        this.utilisateurService.delete(Integer.valueOf(id));
    }


    @DeleteMapping("/{id}/equipe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('LEADER') or hasRole('ADMIN')")
    public EntityUpdatedResponse deleteUserFromEquipe(@PathVariable Integer id, Authentication authentication) {

        if (!equipeService.peutGererMembre(id, authentication)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Utilisateur user;
        try {
            user = this.utilisateurService.deleteUserFromEquipe(id);
        } catch (NotFoundException e) {
            return new EntityUpdatedResponse(-1, false);
        }
        return new EntityUpdatedResponse(user.getId(), true);
    }

}
