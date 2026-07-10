package naruto_backend.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import naruto_backend.model.Utilisateur;
import naruto_backend.service.UtilisateurService;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    @Autowired
    private UtilisateurService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = this.service.getByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return User.builder()
            .username(username)
            .password(utilisateur.getPassword())
            .build()
        ;
    }
}
