package naruto_backend.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import naruto_backend.api.request.AuthRequest;
import naruto_backend.api.response.AuthResponse;
import naruto_backend.model.Equipe;
import naruto_backend.model.Hokage;
import naruto_backend.model.Leader;
import naruto_backend.model.Utilisateur;
import naruto_backend.security.JwtUtils;
import naruto_backend.service.EquipeService;
import naruto_backend.service.UtilisateurService;

@RestController
@RequestMapping("/auth")
public class AuthApiController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UtilisateurService utilisateurSrv;
    private EquipeService equipeSrv;

    @PostMapping
    public AuthResponse auth(@RequestBody AuthRequest request) {
        Authentication auth = new UsernamePasswordAuthenticationToken(request.login(), request.password());

        // On demande à Spring Security de vérifier l'authentification (username & password OK ?)
        this.authenticationManager.authenticate(auth);

        // Si l'autentification est OK, on va chercher l'utilisateur en base pour renvoyer les infos en plus (roles/equipe) au front
        Utilisateur utilisateur = utilisateurSrv.getByLogin(request.login()).get();
        String role = "USER";
        Integer equipeId = null;

        if (utilisateur instanceof Hokage) {
            role = "ADMIN";
        }
        else if (utilisateur instanceof Leader) {
            role = "LEADER";
            Optional<Equipe> optEquipe = equipeSrv.findByLeaderId(utilisateur.getId());
            if (optEquipe.isPresent()) {
                equipeId = optEquipe.get().getId();
            }
        }

        // Si on arrive ici, c'est que l'authentification est OK
        return new AuthResponse(JwtUtils.generate(request.login()), role, equipeId);
    }
}
