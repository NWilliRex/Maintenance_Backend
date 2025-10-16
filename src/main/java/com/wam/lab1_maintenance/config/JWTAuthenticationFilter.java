package com.wam.lab1_maintenance.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtre d’authentification JWT.
 * <p>
 * Ce filtre intercepte chaque requête HTTP pour vérifier la présence
 * d’un jeton JWT dans l’en-tête {@code Authorization}.
 * Si le jeton est valide, il authentifie l’utilisateur
 * dans le contexte de sécurité de Spring.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    /** Service responsable de la gestion et de la validation des tokens JWT. */
    private final JwtService jwtService;

    /** Service permettant de charger les informations de l’utilisateur depuis la base. */
    private final UserDetailsService userDetailsService;

    /**
     * Méthode principale du filtre.
     * <p>
     * Vérifie si une requête contient un en-tête {@code Authorization} avec un jeton valide.
     * Si oui, elle met à jour le contexte de sécurité pour l’utilisateur correspondant.
     * </p>
     *
     * @param request  la requête HTTP entrante
     * @param response la réponse HTTP sortante
     * @param filterChain la chaîne de filtres à exécuter
     * @throws ServletException en cas d’erreur liée au traitement du filtre
     * @throws IOException en cas d’erreur d’entrée/sortie
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Si aucun jeton n’est présent ou qu’il ne commence pas par "Bearer", on passe au filtre suivant
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraction du jeton JWT et du nom d’utilisateur
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        // Vérifie que le contexte n’a pas déjà une authentification
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Valide le jeton et configure l’authentification
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Poursuit la chaîne de filtres
        filterChain.doFilter(request, response);
    }
}
