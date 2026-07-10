package naruto_backend.security;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {
    //////// TODO : config
    private static String JWT_KEYS = "6E5A7234753778214125442A472D4B6150645367556B58703273357638792F42";

    private JwtUtils() { }

    public static String generate(String username) {
        Date now = new Date();
        SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEYS.getBytes());

        return Jwts.builder()
            .subject(username) // Nom de l'utilisateur
            .issuedAt(now) // Date de création du jeton
            .expiration(new Date(now.getTime() + 3_600_000)) // Date d'expiration + 1 heure => valide 1 heure
            .signWith(secretKey) // Signer le jeton JWT
            .compact() // Récupérer le jeton au format String
        ;
    }

    public static String validate(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(JWT_KEYS.getBytes());

        try {
            return Jwts.parser()
                .verifyWith(secretKey) // On vérifie la signature du jeton, s'assurer qu'il n'a pas été altéré
                .build()
                .parseSignedClaims(token) // Donner le jeton à valider
                .getPayload() // A partir du jeton, on cherche le contenu (Payload)
                .getSubject() // A partir du contenu, on cherche le Subject (nom d'utilisateur)
            ;
        }

        catch (Exception e) {
            return null;
        }
    }
}
