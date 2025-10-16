package com.wam.lab1_maintenance.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service responsable de la gestion des tokens JWT :
 * génération, validation et extraction des informations.
 */
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.token.secret}")
    private String jwtSecret;

    /**
     * Extrait le nom d'utilisateur (subject) contenu dans un token JWT.
     *
     * @param token le token JWT à analyser
     * @return le nom d'utilisateur contenu dans le token
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extrait une valeur spécifique des claims du token JWT.
     *
     * @param token le token JWT à analyser
     * @param claimsResolver fonction qui détermine quelle information extraire
     * @return la valeur extraite du token
     * @param <T> le type de la valeur retournée
     */
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrait tous les claims (informations) contenus dans un token JWT.
     *
     * @param token le token JWT à analyser
     * @return l’objet {@link Claims} contenant toutes les informations du token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Génère un token JWT pour un utilisateur avec une durée d’expiration longue (1 an).
     *
     * @param userDetails les informations de l’utilisateur
     * @return le token JWT généré
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, 1000L * 60 * 60 * 24 * 365);
    }

    /**
     * Génère un token JWT avec des claims personnalisés et une expiration définie.
     *
     * @param extraClaims claims additionnels à inclure dans le token
     * @param userDetails les informations de l’utilisateur
     * @param expirationMs durée de validité du token en millisecondes
     * @return le token JWT généré
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, long expirationMs) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Vérifie si un token JWT est valide pour un utilisateur donné.
     *
     * @param token le token JWT à vérifier
     * @param userDetails les informations de l’utilisateur
     * @return {@code true} si le token est valide, sinon {@code false}
     */
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Vérifie si un token JWT est expiré.
     *
     * @param token le token JWT à vérifier
     * @return {@code true} si le token est expiré, sinon {@code false}
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrait la date d’expiration d’un token JWT.
     *
     * @param token le token JWT à analyser
     * @return la date d’expiration du token
     */
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    /**
     * Retourne la clé de signature utilisée pour signer et valider les tokens JWT.
     *
     * @return la clé secrète de signature
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
