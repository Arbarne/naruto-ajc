package naruto_backend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import naruto_backend.dao.IDAOUtilisateur;
import naruto_backend.model.Hokage;
import naruto_backend.model.Leader;
import naruto_backend.model.Utilisateur;
import naruto_backend.service.UtilisateurService;

@Component
public class JwtHeaderFilter extends OncePerRequestFilter {
    @Autowired
    private UtilisateurService utilisateurSrv;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            String token = authHeader.substring(7);
            String username = JwtUtils.validate(token);
            Optional<Utilisateur> optUtilisateur = this.utilisateurSrv.getByLogin(username);

            if (optUtilisateur.isPresent()) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                Utilisateur utilisateur = optUtilisateur.get();

                if (utilisateur instanceof Hokage) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }

                else if (utilisateur instanceof Leader) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_LEADER"));
                }
                
                else {
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // On pense à passer à la suite
        filterChain.doFilter(request, response);
    }
}
